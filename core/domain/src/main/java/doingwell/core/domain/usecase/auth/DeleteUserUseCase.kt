package doingwell.core.domain.usecase.auth

import doingwell.core.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(uid: String): Result<String> {
        return authRepository.deleteUser(uid)
    }

}
