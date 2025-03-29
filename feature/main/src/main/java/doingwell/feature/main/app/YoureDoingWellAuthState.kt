package doingwell.feature.main.app

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hegunhee.model.user.UserData
import doingwell.feature.main.R
import doingwell.feature.signin.isValidEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class YoureDoingWellAuthState(
    val context: Context,
    val coroutineScope: CoroutineScope
) {
    val auth = Firebase.auth

    private val _signInState: MutableStateFlow<SignInState> = MutableStateFlow(
        auth.currentUser?.let { firebaseUser ->
            SignInState(isSignIn = true, userData = firebaseUser.toUserData(), errorMessage = null)
        } ?: run {
            SignInState.DEFAULT
        }
    )
    val signInState : StateFlow<SignInState> = _signInState.asStateFlow()

    val userData : StateFlow<UserData?> = signInState.map {
        it.userData
    }.stateIn(
        scope = coroutineScope,
        initialValue = SignInState.DEFAULT.userData,
        started = SharingStarted.WhileSubscribed(5_000)
    )

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
                successCallback()
            }.addOnFailureListener {
                context.toastMessage(R.string.email_or_password_incorrect)
            }
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

data class SignInState(
    val isSignIn: Boolean,
    val userData: UserData?,
    val errorMessage: String?,
) {
    companion object {
        val DEFAULT = SignInState(
            isSignIn = false,
            userData = null,
            errorMessage = null,
        )
    }
}

fun FirebaseUser.toUserData(): UserData {
    return UserData(uid, email, photoUrl.toString())
}