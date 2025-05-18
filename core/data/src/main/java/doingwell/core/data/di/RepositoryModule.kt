package doingwell.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import doingwell.core.data.repository.DefaultAuthRepository
import doingwell.core.data.repository.DefaultDailyRecordRepository
import doingwell.core.data.repository.DefaultPhotoRepository
import doingwell.core.data.repository.DefaultPhotoStorageRepository
import doingwell.core.domain.repository.AuthRepository
import doingwell.core.domain.repository.DailyRecordRepository
import doingwell.core.domain.repository.PhotoRepository
import doingwell.core.domain.repository.PhotoStorageRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideAuthRepository(
        defaultAuthRepository: DefaultAuthRepository,
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun provideDailyRecordRepository(
        defaultDailyRecordRepository: DefaultDailyRecordRepository,
    ): DailyRecordRepository

    @Singleton
    @Binds
    abstract fun providePhotoRepository(
        defaultPhotoRepository: DefaultPhotoRepository,
    ): PhotoRepository

    @Singleton
    @Binds
    abstract fun providePhotoStorageRepository(
        defaultPhotoStorageRepository: DefaultPhotoStorageRepository,
    ): PhotoStorageRepository
}
