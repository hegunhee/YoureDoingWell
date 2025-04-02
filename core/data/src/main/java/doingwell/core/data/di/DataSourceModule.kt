package doingwell.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import doingwell.core.data.datasource.remote.DefaultRemoteDataSource
import doingwell.core.data.datasource.remote.RemoteDataSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun provideRemoteDataSource(
        defaultRemoteDataSource: DefaultRemoteDataSource,
    ): RemoteDataSource

}
