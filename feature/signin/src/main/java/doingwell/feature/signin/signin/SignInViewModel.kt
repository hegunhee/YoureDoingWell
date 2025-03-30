package doingwell.feature.signin.signin

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val signInClient: SignInClient,
    val signInRequest: BeginSignInRequest,
) : ViewModel(){

}