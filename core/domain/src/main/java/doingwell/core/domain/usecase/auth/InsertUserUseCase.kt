package doingwell.core.domain.usecase.auth

import com.hegunhee.model.user.UserData
import doingwell.core.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(userData: UserData): Result<String> {
        return authRepository.insertUserData(userData)
    }

}
