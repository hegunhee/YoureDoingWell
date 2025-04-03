package doingwell.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import doingwell.core.data.repository.DefaultAuthRepository
import doingwell.core.domain.repository.AuthRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideAuthRepository(
        defaultAuthRepository: DefaultAuthRepository,
    ) : AuthRepository

}
