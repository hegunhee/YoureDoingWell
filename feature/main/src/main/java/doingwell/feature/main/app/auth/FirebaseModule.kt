package doingwell.feature.main.app.auth

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import doingwell.feature.main.R

@Module
@InstallIn(ViewModelComponent::class)
object FirebaseModule {

    @Provides
    fun provideSignInClient(
        @ApplicationContext context : Context,
    ): SignInClient {
        return Identity.getSignInClient(context)
    }

    @Provides
    fun provideSignInRequest(@ApplicationContext context: Context): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()
            ).setAutoSelectEnabled(true)
            .build()

    }
}