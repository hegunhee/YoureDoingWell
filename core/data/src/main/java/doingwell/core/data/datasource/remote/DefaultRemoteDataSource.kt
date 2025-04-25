package doingwell.core.data.datasource.remote

import com.google.firebase.database.DatabaseReference
import com.hegunhee.model.user.UserData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultRemoteDataSource @Inject constructor(
    private val database: DatabaseReference
) : RemoteDataSource {

    override suspend fun insertUserData(userData: UserData): String {
        return try {
            database.child("User").child(userData.uid).setValue(userData).await()
            userData.uid
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun findUser(uid: String): UserData? {
        return try {
            val snapshot = database.child("User").child(uid).get().await()
            snapshot.getValue(UserData::class.java)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteUser(uid: String): String {
        return try {
            database.child("User").child(uid).removeValue().await()
            uid
        } catch (e: Exception) {
            throw e
        }
    }

}
