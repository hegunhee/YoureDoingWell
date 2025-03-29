package doingwell.feature.main.app.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hegunhee.model.user.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import doingwell.feature.main.R
import doingwell.feature.signin.isValidEmail
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YoureDoingWellAuthViewModel @Inject constructor() : ViewModel() {

    val auth = Firebase.auth

    private val _toastMessage: MutableSharedFlow<Int> = MutableSharedFlow()
    val toastMessage: SharedFlow<Int> = _toastMessage.asSharedFlow()

    private val _signInState: MutableStateFlow<SignInState> = MutableStateFlow(
        auth.currentUser?.let { firebaseUser ->
            SignInState(isSignIn = true, userData = firebaseUser.toUserData(), errorMessage = null)
        } ?: run {
            SignInState.DEFAULT
        }
    )
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

    val userData: StateFlow<UserData?> = signInState.map {
        it.userData
    }.stateIn(
        scope = viewModelScope,
        initialValue = SignInState.DEFAULT.userData,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun signInWithEmailAndPassword(email: String, password: String, successCallback: () -> Unit) {
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
                }
                successCallback()
                _signInState.update {
                    it.copy(
                        isSignIn = true,
                        userData = authResult.user?.toUserData(),
                        errorMessage = null
                    )
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
        reCheckPassword: String,
        successCallback: () -> Unit
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
                }
                // db에 uid 등록
                successCallback()
                _signInState.update {
                    it.copy(
                        isSignIn = true,
                        userData = authResult.user?.toUserData(),
                        errorMessage = null
                    )
                }
            }.addOnFailureListener {
                if (it is FirebaseAuthUserCollisionException) {
                    viewModelScope.launch {
                        _toastMessage.emit(R.string.email_exist)
                    }
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _signInState.update {
            SignInState.DEFAULT
        }
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