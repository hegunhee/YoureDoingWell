package doingwell.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import doingwell.core.data.datasource.remote.DailyRecordRemoteDataSource
import doingwell.core.data.datasource.remote.DefaultDailyRecordRemoteDataSource
import doingwell.core.data.datasource.remote.DefaultUserRemoteDataSource
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

}
