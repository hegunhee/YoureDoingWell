package doingwell.feature.main.app

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import doingwell.feature.main.R
import doingwell.feature.signin.isValidEmail
import kotlinx.coroutines.CoroutineScope

class YoureDoingWellAuthState(
    val context: Context,
    val coroutineScope: CoroutineScope
) {
    val auth = Firebase.auth

    fun signInWithEmailAndPassword(email: String, password: String, successCallback: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            context.toastMessage(R.string.email_or_password_empty)
            return
        }
        if (!isValidEmail(email)) {
            context.toastMessage(R.string.email_format_incorrect)
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                context.toastMessage(R.string.success_login)
            }.addOnFailureListener {
                context.toastMessage(R.string.email_or_password_incorrect)
            }
        successCallback()
    }

    fun signUpWithEmail(
        email: String,
        password: String,
        reCheckPassword: String,
        successCallback: () -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            context.toastMessage(R.string.email_or_password_empty)
            return
        }
        if (!isValidEmail(email)) {
            context.toastMessage(R.string.email_format_incorrect)
            return
        }
        if (password != reCheckPassword) {
            context.toastMessage(R.string.password_recheck_password_is_not_same)
            return
        }
        auth
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                context.toastMessage(R.string.signup_success)
                // db에 uid 등록
                successCallback()
            }.addOnFailureListener {
                if (it is FirebaseAuthUserCollisionException) {
                    context.toastMessage(R.string.email_exist)
                }
            }
    }

    private fun Context.toastMessage(@StringRes stringRes: Int) {
        Toast.makeText(this, getText(stringRes), Toast.LENGTH_SHORT).show()
    }
}