package my.id.medicalrecordblockchain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.id.medicalrecordblockchain.BuildConfig
import my.id.medicalrecordblockchain.data.APIService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit.Builder
    ): APIService = retrofit
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create()
}