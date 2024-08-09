package com.ssccgl.pinnacle.testportal.UI
// SecureStorage.kt
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SecureStorage {
    private const val PREFS_FILENAME = "login_prefs"
    private const val EMAIL_OR_MOBILE_KEY = "emailOrMobile"
    private const val PASSWORD_KEY = "password"

    private fun getSharedPreferences(context: Context) =
        EncryptedSharedPreferences.create(
            PREFS_FILENAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveCredentials(context: Context, emailOrMobile: String, password: String) {
        val sharedPreferences = getSharedPreferences(context)
        with(sharedPreferences.edit()) {
            putString(EMAIL_OR_MOBILE_KEY, emailOrMobile)
            putString(PASSWORD_KEY, password)
            apply()
        }
    }

    fun getCredentials(context: Context): Pair<String?, String?> {
        val sharedPreferences = getSharedPreferences(context)
        val emailOrMobile = sharedPreferences.getString(EMAIL_OR_MOBILE_KEY, null)
        val password = sharedPreferences.getString(PASSWORD_KEY, null)
        return Pair(emailOrMobile, password)
    }

    fun clearCredentials(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}
