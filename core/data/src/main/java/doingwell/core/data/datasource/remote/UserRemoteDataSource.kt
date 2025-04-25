package doingwell.core.data.datasource.remote

import com.hegunhee.model.user.UserData

interface UserRemoteDataSource {

    suspend fun insertUserData(userData: UserData): String

    suspend fun findUser(uid: String): UserData?

    suspend fun deleteUser(uid: String): String
}
