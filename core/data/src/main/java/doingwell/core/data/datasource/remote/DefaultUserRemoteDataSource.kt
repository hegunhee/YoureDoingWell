package doingwell.core.data.datasource.remote

import com.google.firebase.database.DatabaseReference
import com.hegunhee.model.user.UserData
import doingwell.core.data.di.qualifier.UserDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUserRemoteDataSource @Inject constructor(
    @UserDatabase private val database: DatabaseReference
) : UserRemoteDataSource {

    override suspend fun insertUserData(userData: UserData): String {
        database.child(userData.uid).setValue(userData).await()
        return userData.uid
    }

    override suspend fun findUser(uid: String): UserData? {
        val snapshot = database.child(uid).get().await()
        return snapshot.getValue(UserData::class.java)
    }

    override suspend fun deleteUser(uid: String): String {
        database.child(uid).removeValue().await()
        return uid
    }

}
