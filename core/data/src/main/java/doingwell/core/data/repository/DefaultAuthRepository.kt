package doingwell.core.data.repository

import com.hegunhee.model.user.UserData
import doingwell.core.data.datasource.remote.RemoteDataSource
import doingwell.core.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAuthRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : AuthRepository {

    override suspend fun insertUserData(userData: UserData): Result<String> {
        return runCatching {
            if (remoteDataSource.findUser(userData.uid) == null) {
                remoteDataSource.insertUserData(userData)
            }
            userData.uid
        }
    }

    override suspend fun findUser(uid: String): Result<UserData> {
        return runCatching {
            remoteDataSource.findUser(uid) ?: throw NoSuchElementException("계정을 찾지 못했습니다.")
        }
    }

    override suspend fun deleteUser(uid: String): Result<String> {
        return runCatching {
            remoteDataSource.deleteUser(uid)
        }
    }

}
