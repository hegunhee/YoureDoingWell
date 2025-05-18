package doingwell.core.data.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DailyRecordDatabase

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserDatabase

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PhotoStorage
