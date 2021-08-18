package app.basic.project.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.BuildConfig
import app.basic.project.R
import app.basic.project.util.NetworkUtils
import app.basic.project.util.SessionManager
import java.util.*

/**
 * @author Enlistech.
 * Email - enlistechs@gmail.com
 */
abstract class BaseFragment(layoutResId: Int) : Fragment(layoutResId) {

    private var rootView: View? = null
    private lateinit var dialog: Dialog

    var sessionManager: SessionManager? = null

    val isNetworkConnected: Boolean
        get() {
            return if (!NetworkUtils.isNetworkConnected(requireContext())) {
                showErrorToast(getString(R.string.error_internet))
                false
            } else {
                true
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rootView = view
        sessionManager = SessionManager(requireContext())
        super.onViewCreated(view, savedInstanceState)
    }

    fun showErrorToast(errorMsg: String) {
        Toast.makeText(requireActivity(), errorMsg, Toast.LENGTH_LONG).show()
    }

    fun showSuccessToast(successMsg: String) {
        Toast.makeText(requireActivity(), successMsg, Toast.LENGTH_SHORT).show()
    }

    fun printLog(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, "--------------------------$message")
        }
    }

    protected fun setLinearRecyclerView(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        orientation: Int,
        reverseLayout: Boolean,
        hasFixedSize: Boolean,
        nestedScrollingEnabled: Boolean
    ): androidx.recyclerview.widget.RecyclerView {
        val linearLayoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                requireActivity(),
                orientation,
                reverseLayout
            )
        recyclerView.setHasFixedSize(hasFixedSize)
        recyclerView.isNestedScrollingEnabled = nestedScrollingEnabled
        recyclerView.layoutManager = linearLayoutManager
        return recyclerView
    }

    protected fun setGridRecyclerView(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        orientation: Int,
        reverseLayout: Boolean,
        spanCount: Int,
        hasFixedSize: Boolean,
        nestedScrollingEnabled: Boolean
    ): androidx.recyclerview.widget.RecyclerView {
        val gridLayoutManager =
            androidx.recyclerview.widget.GridLayoutManager(
                requireActivity(),
                spanCount,
                orientation,
                reverseLayout
            )
        recyclerView.setHasFixedSize(hasFixedSize)
        recyclerView.isNestedScrollingEnabled = nestedScrollingEnabled
        recyclerView.layoutManager = gridLayoutManager
        return recyclerView
    }

    fun showLoading() {
        dialog = Dialog(requireContext())
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    fun hideLoading() {
        if (dialog.isShowing) {
            dialog.cancel()
        }
    }

//    fun pushFragmentWithBackStack(DestinationFragment: androidx.fragment.app.Fragment) {
//        try {
//            val SourceFragment = this
//            val viewResourceID = (SourceFragment.view!!.parent as ViewGroup).id
//            val fragmentManager = fragmentManager
//            val ft = fragmentManager!!.beginTransaction()
//            ft.add(viewResourceID, DestinationFragment)
//            ft.hide(SourceFragment)
//            ft.addToBackStack(SourceFragment.javaClass.name)
//            ft.commit()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    fun replaceFragment(DestinationFragment: androidx.fragment.app.Fragment) {
//        val SourceFragment = this
//        val fragmentManager = fragmentManager
//        val ft = fragmentManager!!.beginTransaction()
//        val viewResourceID = (SourceFragment.view!!.parent as ViewGroup).id
//        ft.replace(viewResourceID, DestinationFragment)
//        ft.commit()
//    }
//
//    fun popFragment(SourceFragment: androidx.fragment.app.Fragment): Boolean {
//        fragmentManager!!.popBackStack()
//        return true
//    }

    companion object {
        fun replaceFragmentInContainer(
            DestinationFragment: androidx.fragment.app.Fragment,
            containerResourceID: Int,
            fragmentManager: androidx.fragment.app.FragmentManager
        ) {
            val ft = fragmentManager.beginTransaction()
            ft.replace(containerResourceID, DestinationFragment)
            ft.commit()
        }
    }

    open fun replaceFragmentWithoutBack(
        containerViewId: Int,
        fragment: Fragment?,
        fragmentTag: String?
    ) {
        requireActivity()!!.supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment!!, fragmentTag)
            .commitAllowingStateLoss()
    }

    open fun replaceFragmentWithBack(
        containerViewId: Int,
        fragment: Fragment?,
        fragmentTag: String?,
        backStackStateName: String?
    ) {
        requireActivity()!!.supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment!!, fragmentTag)
            .addToBackStack(backStackStateName)
            .commitAllowingStateLoss()
    }


    open fun replaceFragment(containerViewId: Int, frag: Fragment) {
        val manager = requireActivity()!!.supportFragmentManager
        if (manager != null) {
            val t = manager.beginTransaction()
            val currentFrag = manager.findFragmentById(containerViewId)

            //Check if the new Fragment is the same
            //If it is, don't add to the back stack
            if (currentFrag != null && currentFrag.javaClass == frag.javaClass) {
                t.replace(containerViewId, frag).commit()
            } else {
                t.replace(containerViewId, frag).addToBackStack(null).commit()
            }
        }
    }

    open fun addFragmentWithoutRemove(
        containerViewId: Int,
        fragment: Fragment,
        fragmentName: String?
    ) {
        requireActivity()!!.supportFragmentManager.beginTransaction() // remove fragment from fragment manager
            //fragmentTransaction.remove(getActivity().getSupportFragmentManager().findFragmentByTag(tag));
            // add fragment in fragment manager
            .add(containerViewId, fragment, fragmentName) // add to back stack
            .addToBackStack(fragment.tag)
            .commit()
    }

    open fun addDetailsFragment(
        containerViewId: Int,
        whereYouMove: Fragment?,
        previousFragmentTag: String?
    ) {
        val ft = requireActivity().supportFragmentManager.beginTransaction()
        ft.hide(requireFragmentManager().findFragmentByTag(previousFragmentTag)!!)
        ft.add(containerViewId, whereYouMove!!)
        ft.addToBackStack(null)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
    }

    open fun addFragmentWithRemove(
        containerViewId: Int,
        fragment: Fragment,
        fragmentName: String?
    ) {
        val tag = fragment.tag
        requireActivity().supportFragmentManager.beginTransaction() // remove fragment from fragment manager
            .remove(requireActivity().supportFragmentManager.findFragmentByTag(tag)!!) // add fragment in fragment manager
            .add(containerViewId, fragment, fragmentName) // add to back stack
            .addToBackStack(tag)
            .commit()
    }

    /**
     * Method for get screen height and width
     */
    private var width = 0

    /**
     * Method for get screen height and width
     */
    private var height: Int = 0

    open fun getDeviceHeightWidth(): IntArray? {
        val displaymetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displaymetrics)
        height = displaymetrics.heightPixels
        width = displaymetrics.widthPixels
        val i = IntArray(2)
        i[0] = height
        i[1] = width
        return i
    }

    open fun getAddressFromLatLong(latitude: Double, longitude: Double, status: Boolean): String? {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(requireActivity(), Locale.getDefault())
        return try {
            addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            val address =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode
            val knownName = addresses[0].featureName

            //            if (status)
            "$address $city"
            //            else
            //                return country;
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
