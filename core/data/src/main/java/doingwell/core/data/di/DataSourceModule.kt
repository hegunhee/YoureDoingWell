package doingwell.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import doingwell.core.data.datasource.local.DefaultPhotoLocalDataSource
import doingwell.core.data.datasource.local.PhotoLocalDataSource
import doingwell.core.data.datasource.remote.DailyRecordRemoteDataSource
import doingwell.core.data.datasource.remote.DefaultDailyRecordRemoteDataSource
import doingwell.core.data.datasource.remote.DefaultPhotoStorageRemoteDataSource
import doingwell.core.data.datasource.remote.DefaultUserRemoteDataSource
import doingwell.core.data.datasource.remote.PhotoStorageRemoteDataSource
import doingwell.core.data.datasource.remote.UserRemoteDataSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun provideUserRemoteDataSource(
        defaultUserRemoteDataSource: DefaultUserRemoteDataSource,
    ): UserRemoteDataSource

    @Singleton
    @Binds
    abstract fun provideDailyRecordRemoteDataSource(
        defaultDailyRecordRemoteDataSource: DefaultDailyRecordRemoteDataSource,
    ): DailyRecordRemoteDataSource

    @Singleton
    @Binds
    abstract fun providePhotoLocalDataSource(
        defaultPhotoLocalDataSource: DefaultPhotoLocalDataSource,
    ) : PhotoLocalDataSource

    @Singleton
    @Binds
    abstract fun providePhotoStorageRemoteDataSource(
        defaultPhotoStorageRemoteDataSource: DefaultPhotoStorageRemoteDataSource,
    ) : PhotoStorageRemoteDataSource

}
