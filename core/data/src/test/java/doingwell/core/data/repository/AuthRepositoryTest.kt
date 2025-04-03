package doingwell.core.data.repository

import com.hegunhee.model.user.UserData
import doingwell.core.data.datasource.remote.DefaultRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    @InjectMocks
    private lateinit var sut: DefaultAuthRepository

    @Mock
    private lateinit var remoteDataSource: DefaultRemoteDataSource

    @Test
    fun givenUserData_whenInsertUserData_thenReturnsUid() {
        runBlocking {
            // Given
            val userData = createUserData("TEST_UID", email = "TEST_EMAIL")
            whenever(remoteDataSource.findUser(userData.uid)).thenReturn(null)
            whenever(remoteDataSource.insertUserData(userData)).thenReturn(userData.uid)

            // When
            val uid = sut.insertUserData(userData).getOrThrow()

            // Then
            assertThat(uid).isEqualTo(userData.uid)
            verify(remoteDataSource).findUser(userData.uid)
            verify(remoteDataSource).insertUserData(userData)
        }
    }

    @Test
    fun givenExistUserData_whenInsertUserData_thenReturnsUid() {
        runBlocking {
            // Given
            val existUserData = createUserData("EXIST_UID", email = "EXIST_EMAIL")
            whenever(remoteDataSource.findUser(existUserData.uid)).thenReturn(existUserData)

            // When
            val uid = sut.insertUserData(existUserData).getOrThrow()

            // Then
            assertThat(uid).isEqualTo(existUserData.uid)
            verify(remoteDataSource).findUser(existUserData.uid)
        }
    }

    @Test
    fun givenExistUserDataUid_whenFindUserData_thenReturnsUserData() {
        runBlocking {
            // Given
            val existUid = "Exist_UID"
            val existUserData = createUserData(existUid, email = "EXIST_EMAIL")
            whenever(remoteDataSource.findUser(existUid)).thenReturn(existUserData)

            // When
            val findUserData = sut.findUser(existUid).getOrThrow()

            // Then
            assertThat(findUserData).isEqualTo(existUserData)
            verify(remoteDataSource).findUser(existUid)
        }
    }

    @Test
    fun givenNonExistUid_whenFindUserData_thenThrowsNoSuchElementException() {
        runBlocking {
            // Given
            val nonExistUid = "Non_Exist_UID"
            whenever(remoteDataSource.findUser(nonExistUid)).thenReturn(null)

            // When
            val t = catchThrowable { runBlocking { sut.findUser(nonExistUid).getOrThrow() } }

            // Then
            assertThat(t)
                .isInstanceOf(NoSuchElementException::class.java)
                .hasMessage("계정을 찾지 못했습니다.")

            verify(remoteDataSource).findUser(nonExistUid)

        }
    }

    @Test
    fun givenUid_whenDeleteUserData_thenReturnsUid() {
        runBlocking {
            // given
            val uid = "UID"
            whenever(remoteDataSource.deleteUser(uid)).thenReturn(uid)

            // when
            val deletedUid = sut.deleteUser(uid).getOrThrow()

            // then
            assertThat(deletedUid).isEqualTo(uid)
            verify(remoteDataSource).deleteUser(uid)
        }
    }


    private fun createUserData(uid: String, email: String): UserData {
        return UserData(uid = uid, email = email)
    }


}