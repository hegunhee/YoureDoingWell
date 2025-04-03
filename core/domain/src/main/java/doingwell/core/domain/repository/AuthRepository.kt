package doingwell.core.domain.repository

import com.hegunhee.model.user.UserData

interface AuthRepository {

    suspend fun insertUserData(userData : UserData): Result<String>

    suspend fun findUser(uid: String): Result<UserData>

    suspend fun deleteUser(uid: String): Result<String>

}
