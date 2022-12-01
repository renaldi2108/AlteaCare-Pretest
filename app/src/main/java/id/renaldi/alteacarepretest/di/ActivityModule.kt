package id.renaldi.alteacarepretest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import id.renaldi.alteacarepretest.data.network.service.DoctorService
import id.renaldi.alteacarepretest.data.repository.DoctorRepository

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    @ActivityScoped
    fun provideDoctorRepository(
        service: DoctorService
    ): DoctorRepository = DoctorRepository(service)
}