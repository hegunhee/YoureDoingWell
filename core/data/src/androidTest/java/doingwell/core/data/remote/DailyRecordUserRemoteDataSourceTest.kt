package doingwell.core.data.remote

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.database
import com.hegunhee.model.user.DateTimeInfo
import doingwell.core.data.datasource.remote.DailyRecordRemoteDataSource
import doingwell.core.data.datasource.remote.DefaultDailyRecordRemoteDataSource
import doingwell.core.data.datasource.remote.model.DateTimeResponse
import doingwell.core.data.datasource.remote.model.record.DailyRecordResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class DailyRecordUserRemoteDataSourceTest {

    private lateinit var sut: DailyRecordRemoteDataSource

    private val now: LocalDateTime = LocalDateTime.now()

    @Before
    fun initContext() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        if (FirebaseApp.getApps(context).isEmpty()) {
            val options = FirebaseOptions.Builder()
                .setApplicationId(FirebaseTestParams.APPLICATION_ID)
                .setApiKey(FirebaseTestParams.API_KEY)
                .setDatabaseUrl(FirebaseTestParams.DATABASE_URL)
                .build()
            FirebaseApp.initializeApp(context, options)
        }
        sut = DefaultDailyRecordRemoteDataSource(Firebase.database.getReference("TEST"))
    }

    @Test
    fun givenDailyRecordResponse_whenInserting_thenWorksFine() {
        runBlocking {
            // Given
            val dailyRecordResponse = createDailyRecordResponse("userId", "title")

            // When
            val key = sut.insertDailyRecord(dailyRecordResponse)

            println(key)
            // Then
            assertTrue(key >= 0)

            sut.deleteDailyRecord(key, "userId", dailyRecordResponse.startedAt.dateStamp)
        }
    }

    @Test
    fun givenDailyRecordResponse_whenGetByUserId_thenWorksFine() {
        runBlocking {
            // Given
            val userId = "userId"
            val dateStamp = "20250507"

            val dailyRecord1 = createDailyRecordResponse(userId, "title1")
            val dailyRecord2 = createDailyRecordResponse(userId, "title2")

            val beforeRecords = listOf(dailyRecord1, dailyRecord2)

            val key0 = sut.insertDailyRecord(dailyRecord1)
            val key1 = sut.insertDailyRecord(dailyRecord2)

            // When
            val records = sut.findDailyRecords(userId, dateStamp).sortedBy { it.title }

            // Then
            assertEquals(records.size, beforeRecords.size)
            assertEquals(records.map { it.title }, beforeRecords.map { it.title })


            sut.deleteDailyRecord(key0, userId, dateStamp)
            sut.deleteDailyRecord(key1, userId, dateStamp)
        }
    }

    private fun createDailyRecordResponse(
        userId: String,
        title: String
    ): DailyRecordResponse {
        return DailyRecordResponse(
            userId = userId,
            title = title,
            description = null,
            photos = null,
            startedAt = createDateTimeResponse(),
            endedAt = createDateTimeResponse(),
        )
    }

    private fun createDateTimeResponse(): DateTimeResponse {
        return DateTimeResponse.of(
            DateTimeInfo(
                now.year,
                now.monthValue,
                now.dayOfMonth,
                now.hour,
                now.minute
            )
        )
    }
}
