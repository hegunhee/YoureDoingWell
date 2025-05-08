package doingwell.core.data.remote

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hegunhee.model.user.UserData
import doingwell.core.data.datasource.remote.DefaultUserRemoteDataSource
import doingwell.core.data.datasource.remote.UserRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRemoteDataSourceTest {

    private lateinit var sut : UserRemoteDataSource

    @Before
    fun initContext() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        if(FirebaseApp.getApps(context).isEmpty()) {
            val options = FirebaseOptions.Builder()
                .setApplicationId(FirebaseTestParams.APPLICATION_ID)
                .setApiKey(FirebaseTestParams.API_KEY)
                .setDatabaseUrl(FirebaseTestParams.DATABASE_URL)
                .build()
            FirebaseApp.initializeApp(context, options)
        }
        sut = DefaultUserRemoteDataSource(Firebase.database.getReference("TEST").child("User"))
    }

    @Test
    fun givenUserData_whenInserting_thenWorksFine() {
        runBlocking {
            // given
            val userData = UserData(uid = "TEST", email = "TEST_EMAIL", photoUrl = "TEST_PHOTO_URL")

            // when
            val resultUid = sut.insertUserData(userData)

            // then
            assertEquals(resultUid, userData.uid)
            sut.deleteUser(resultUid)

        }

    }

    @Test
    fun given_whenFinding_thenWorksFine() {
        runBlocking {
            // given
            val uid = "TEST1"
            val userData1 = UserData(uid = uid, email = "TEST_EMAIL1", photoUrl = "TEST_PHOTO_URL1")
            val userData2 = UserData(uid = "TEST2", email = "TEST_EMAIL2", photoUrl = "TEST_PHOTO_URL2")
            sut.insertUserData(userData1)
            sut.insertUserData(userData2)

            // when
            val findUser = sut.findUser(uid)

            // then
            assertEquals(findUser?.uid, uid)
            sut.deleteUser(userData1.uid)
            sut.deleteUser(userData2.uid)
        }
    }
}