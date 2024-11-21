package band.effective.office.tv.network.synology

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.network.ErrorReason
import band.effective.office.tv.network.synology.models.response.SynologyAlbumsResponse
import band.effective.office.tv.network.synology.models.response.SynologyAuthResponse
import band.effective.office.tv.network.synology.models.response.SynologyListResponse
import band.effective.office.tv.network.synology.models.response.SynologyPhotoSAlbumsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SynologyApi {
    @GET("/webapi/auth.cgi?api=SYNO.API.Auth&session=FileStation&format=sid")
    suspend fun auth(
        @Query("version") version: Int,
        @Query("method") method: String,
        @Query("account") login: String,
        @Query("passwd") password: String
    ): Either<ErrorReason, SynologyAuthResponse>

    @GET("/webapi/entry.cgi/SYNO.Foto.Browse.Album?api=SYNO.Foto.Browse.Album")
    suspend fun getAlbums(
        @Query("_sid") sid: String,
        @Query("version") version: Int,
        @Query("method") method: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Either<ErrorReason, SynologyAlbumsResponse>

    @GET("/webapi/entry.cgi/SYNO.Foto.Browse.Item?api=SYNO.Foto.Browse.Item")
   suspend fun getPhotosFromAlbum(
        @Query("_sid") sid: String,
        @Query("version") version: Int,
        @Query("method") method: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("album_id") albumId: Int,
        @Query("additional") additional: String =
            "[\"thumbnail\",\"resolution\"," +
            "\"orientation\",\"video_convert\"," +
            "\"video_meta\",\"provider_user_id\"]"
    ): Either<ErrorReason, SynologyPhotoSAlbumsResponse>

    @GET("/webapi/entry.cgi?api=SYNO.FileStation.List")
    suspend fun getFiles(
        @Query("_sid") sid: String,
        @Query("version") version: Int,
        @Query("method") method: String,
        @Query("folder_path") folderPath: String
    ): Either<ErrorReason, SynologyListResponse>

}