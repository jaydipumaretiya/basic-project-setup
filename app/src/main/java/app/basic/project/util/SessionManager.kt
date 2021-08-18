package app.basic.project.util

import android.content.Context
import android.content.SharedPreferences

/**
 * @author Enlistech.
 */
class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    private val preferencesName = "traseel"
    private val login = "login"
    private val user = "user"

    init {
        sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply()
    }

//    var storedUser: User?
//        get() {
//            val json = sharedPreferences.getString(user, "")
//            val type = object : TypeToken<User>() {
//            }.type
//            return Gson().fromJson(json, type)
//        }
//        set(storedAppData) {
//            editor.putString(user, Gson().toJson(storedAppData))
//            editor.commit()
//        }
//
//    var userType: Int
//        get() = sharedPreferences.getInt(USER_TYPE, Constants.CUSTOMER)
//        set(userType) {
//            editor.putInt(USER_TYPE, userType)
//            editor.commit()
//        }
//
//    var isLogin: Boolean
//        get() = sharedPreferences.getBoolean(login, false)
//        set(isLogin) {
//            editor.putBoolean(login, isLogin)
//            editor.commit()
//        }
}
