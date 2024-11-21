package band.effective.office.tv.di.view_models

import band.effective.office.tv.repository.synology.AuthSynologyRepository
import band.effective.office.tv.repository.synology.PhotoSynologyRepository
import band.effective.office.tv.repository.synology.SynologyRepository
import band.effective.office.tv.repository.synology.impl.AuthSynologyRepositoryImpl
import band.effective.office.tv.repository.synology.impl.PhotoSynologyRepositoryImpl
import band.effective.office.tv.repository.synology.impl.SynologyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface PhotoViewModelModule {

    @ViewModelScoped
    @Binds
    fun bindsAuthSynologyRepository(repository: AuthSynologyRepositoryImpl): AuthSynologyRepository

    @ViewModelScoped
    @Binds
    fun bindsPhotoSynologyRepository(repository: PhotoSynologyRepositoryImpl): PhotoSynologyRepository

    @ViewModelScoped
    @Binds
    fun bindsSynologyRepository(repository: SynologyRepositoryImpl): SynologyRepository
}