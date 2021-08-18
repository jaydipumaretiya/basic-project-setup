package app.basic.project.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.BuildConfig
import app.basic.project.R
import app.basic.project.util.AppUtils
import app.basic.project.util.NetworkUtils
import app.basic.project.util.SessionManager

/**
 * @author Enlistech.
 * Email - enlistechs@gmail.com
 */
abstract class BaseActivity(layoutResId: Int) : AppCompatActivity(layoutResId) {

    var sessionManager: SessionManager? = null
    var dialog: Dialog? = null

    val isNetworkConnected: Boolean
        get() {
            return if (!NetworkUtils.isNetworkConnected(this@BaseActivity)) {
                showErrorToast(getString(R.string.error_internet))
                false
            } else {
                true
            }
        }

    override fun setContentView(layoutResID: Int) {
        AppUtils.setLocale(this)
        super.setContentView(layoutResID)
        sessionManager = SessionManager(this)
        setContent()
    }

    protected abstract fun setContent()

    private fun showErrorToast(errorMsg: String) {
        Toast.makeText(this@BaseActivity, errorMsg, Toast.LENGTH_LONG).show()
    }

    fun showSuccessToast(successMsg: String) {
        Toast.makeText(this@BaseActivity, successMsg, Toast.LENGTH_SHORT).show()
    }

    fun printLog(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, "--------------------------$message")
        }
    }

    protected fun setLinearRecyclerView(
        recyclerView: RecyclerView,
        reverseLayout: Boolean
    ): RecyclerView {
        val linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            reverseLayout
        )
        recyclerView.setHasFixedSize(false)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = linearLayoutManager
        return recyclerView
    }

    protected fun setGridRecyclerView(
        recyclerView: RecyclerView,
        spanCount: Int
    ): RecyclerView {
        val gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(this, spanCount)
        recyclerView.setHasFixedSize(false)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = gridLayoutManager
        return recyclerView
    }

    fun showLoading() {
        dialog = Dialog(this@BaseActivity)
        val inflate = LayoutInflater.from(this@BaseActivity).inflate(R.layout.dialog_progress, null)
        dialog!!.setContentView(inflate)
        dialog!!.setCancelable(false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.show()
    }

    fun hideLoading() {
        if (dialog!!.isShowing) {
            dialog!!.cancel()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    public fun getContextOfActivity(): Context {
        return this
    }
}
