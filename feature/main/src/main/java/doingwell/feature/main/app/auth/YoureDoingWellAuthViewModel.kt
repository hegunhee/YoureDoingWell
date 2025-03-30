package doingwell.feature.main.app.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hegunhee.model.user.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import doingwell.feature.main.R
import doingwell.feature.signin.isValidEmail
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YoureDoingWellAuthViewModel @Inject constructor() : ViewModel() {

    val auth = Firebase.auth

    private val _toastMessage: MutableSharedFlow<Int> = MutableSharedFlow()
    val toastMessage: SharedFlow<Int> = _toastMessage.asSharedFlow()

    private val _authState : MutableSharedFlow<AuthState> = MutableSharedFlow()
    val authState : SharedFlow<AuthState> = _authState

    private val _userData : MutableStateFlow<UserData?> = MutableStateFlow(auth.currentUser?.toUserData())
    val userData : StateFlow<UserData?> = _userData.asStateFlow()

    fun signInWithEmailAndPassword(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            viewModelScope.launch {
                _toastMessage.emit(R.string.email_or_password_empty)
            }
            return
        }
        if (!isValidEmail(email)) {
            viewModelScope.launch {
                _toastMessage.emit(R.string.email_format_incorrect)
            }
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                viewModelScope.launch {
                    _toastMessage.emit(R.string.success_login)
                    _authState.emit(AuthState.SignIn)
                    _userData.value = authResult.user?.toUserData()
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _toastMessage.emit(R.string.email_or_password_incorrect)
                }
            }
    }

    fun signUpWithEmail(
        email: String,
        password: String,
        reCheckPassword: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            viewModelScope.launch {
                _toastMessage.emit(R.string.email_or_password_empty)
            }
            return
        }
        if (!isValidEmail(email)) {
            viewModelScope.launch {
                _toastMessage.emit(R.string.email_format_incorrect)
            }
            return
        }
        if (password != reCheckPassword) {
            viewModelScope.launch {
                _toastMessage.emit(R.string.password_recheck_password_is_not_same)
            }
            return
        }
        auth
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                viewModelScope.launch {
                    _toastMessage.emit(R.string.signup_success)
                    _authState.emit(AuthState.SignIn)
                    _userData.value = authResult.user?.toUserData()
                }
                // db에 uid 등록
            }.addOnFailureListener {
                if (it is FirebaseAuthUserCollisionException) {
                    viewModelScope.launch {
                        _toastMessage.emit(R.string.email_exist)
                    }
                }
            }
    }

    fun signInWithGoogle(googleIdToken : String) {
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        auth.signInWithCredential(googleCredential)
            .addOnSuccessListener { authResult ->
                viewModelScope.launch {
                    _toastMessage.emit(R.string.success_login)
                    _authState.emit(AuthState.SignIn)
                    _userData.value = authResult.user?.toUserData()
                }
            }
    }

    fun resetPasswordWithEmail(email:String) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _toastMessage.emit(doingwell.feature.signin.R.string.password_reset_email_send)
                }
            }
    }

    fun signOut() {
        auth.signOut()
        viewModelScope.launch {
            _authState.emit(AuthState.SignOut)
            _userData.value = null
        }
    }

    private fun FirebaseUser.toUserData(): UserData {
        return UserData(uid, email, photoUrl.toString())
    }

}
