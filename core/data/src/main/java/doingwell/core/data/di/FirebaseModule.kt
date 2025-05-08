package doingwell.core.data.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import doingwell.core.data.di.qualifier.DailyRecordDatabase
import doingwell.core.data.di.qualifier.UserDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseReference() : DatabaseReference {
        return Firebase.database.reference
    }

    @Singleton
    @Provides
    @UserDatabase
    fun provideUserFirebaseReference() : DatabaseReference {
        return Firebase.database.reference.child("User")
    }

    @Singleton
    @Provides
    @DailyRecordDatabase
    fun provideDailyRecordFirebaseReference() : DatabaseReference {
        return Firebase.database.reference.child("Daily").child("Record")
    }

}
