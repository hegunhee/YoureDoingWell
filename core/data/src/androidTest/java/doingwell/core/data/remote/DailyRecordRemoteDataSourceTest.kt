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
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class DailyRecordRemoteDataSourceTest {

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
        sut = DefaultDailyRecordRemoteDataSource(
            Firebase.database.getReference("TEST").child("record").child("daily")
        )
    }

    @After
    fun tearDown() {
        runBlocking {
            val dateStamp = todayDateStamp()
            if (sut.dailyRecordsSize("userId", dateStamp) == 0) {
                sut.deleteDailyRecords("userId", dateStamp)
            }
        }
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

            val dailyRecord1 = createDailyRecordResponse(userId, "title1")
            val dailyRecord2 = createDailyRecordResponse(userId, "title2")

            val dateStamp = dailyRecord1.startedAt.dateStamp

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

    @Test
    fun given_whenGetDailyRecordsSize_thenWorksFine() {
        runBlocking {
            // Given

            // When
            val size = sut.dailyRecordsSize("userId", "20250507")

            // Then
            assertEquals(size, 0)
        }
    }

    @Test
    fun given_whenDeleteAllRecords_thenWorksFine() {
        runBlocking {
            // Given

            // When
            val deletedCount = sut.deleteDailyRecords("userId", "20250507")

            // Then
            assertEquals(deletedCount, 0)
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

    private fun todayDateStamp(): String {
        return listOf(
            now.year.toString().format(4),
            now.monthValue.toString().format(2),
            now.dayOfMonth.toString().format(2),
        ).joinToString("")
    }
}
