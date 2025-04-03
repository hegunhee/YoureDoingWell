package doingwell.core.data.datasource.remote

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hegunhee.model.user.UserData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultRemoteDataSource @Inject constructor(

) : RemoteDataSource {

    val database = Firebase.database

    override suspend fun insertUserData(userData: UserData): String {
        return try {
            database.getReference("User").child(userData.uid).setValue(userData).await()
            userData.uid
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun findUser(uid: String): UserData? {
        return try {
            val snapshot = database.getReference("User").child(uid).get().await()
            snapshot.getValue(UserData::class.java)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteUser(uid: String): String {
        return try {
            database.getReference("User").child(uid).removeValue().await()
            uid
        } catch (e: Exception) {
            throw e
        }
    }

}
