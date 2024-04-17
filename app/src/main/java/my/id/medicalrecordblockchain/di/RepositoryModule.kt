package my.id.medicalrecordblockchain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.id.medicalrecordblockchain.data.repository.doctor.DoctorRepository
import my.id.medicalrecordblockchain.data.repository.doctor.DoctorRepositoryImpl
import my.id.medicalrecordblockchain.data.repository.patient.PatientRepository
import my.id.medicalrecordblockchain.data.repository.patient.PatientRepositoryImpl
import my.id.medicalrecordblockchain.data.repository.user.UserRepository
import my.id.medicalrecordblockchain.data.repository.user.UserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl

    @Provides
    @Singleton
    fun providePatientRepository(patientRepositoryImpl: PatientRepositoryImpl): PatientRepository =
        patientRepositoryImpl

    @Provides
    @Singleton
    fun provideDoctorRepository(doctorRepositoryImpl: DoctorRepositoryImpl): DoctorRepository =
        doctorRepositoryImpl
}