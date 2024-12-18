package band.effective.office.shareddesk.data.remote

import band.effective.office.shareddesk.data.DataSource
import band.effective.office.shareddesk.data.models.WorkplaceDto
import band.effective.office.shareddesk.data.models.WorkplacesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

internal class RemoteDataSource(
    private val ktorClient: HttpClient,
) : DataSource {

    private val hostUrl = "YOUR_HOST"

    override suspend fun getOfficeSourceMap(): Result<ByteArray> {
        ktorClient.get(
            url = Url("$hostUrl/resources/office_source_map.svg")
        ) {
            header("ngrok-skip-browser-warning", "true")
            header("no-cors", "true")
        }
            .apply {
                return if (status.value == 200) {
                    Result.success(bodyAsBytes())
                } else Result.failure(Exception())
            }
    }

    override suspend fun saveOfficeSourceMap(byteArray: ByteArray): Result<Boolean> {
        ktorClient.post {
            url("$hostUrl/save-map")
            setBody(byteArray)
            header("ngrok-skip-browser-warning", "true")
            header("no-cors", "true")
        }.apply {
            return Result.success(status == HttpStatusCode.OK)
        }
    }

    override suspend fun getWorkplaces(): Result<List<WorkplaceDto>> {
        val result = ktorClient.get {
            header("ngrok-skip-browser-warning", "true")
            header("no-cors", "true")
            url("$hostUrl/workspaces")
        }
        return with(result) {
            if (status.value == 200) {
                Result.success(body<WorkplacesResponse>().list)
            } else {
                Result.failure(Exception())
            }
        }
    }

    override suspend fun addNewWorkplace(worplace: WorkplaceDto): Result<Boolean> {
        val result = ktorClient.post {
            header("ngrok-skip-browser-warning", "true")
            header("no-cors", "true")
            contentType(ContentType.Application.Json)
            url("$hostUrl/workspaces/new")
            setBody(worplace)
        }
        return with(result) {
            if (status.value == 200) {
                Result.success(true)
            } else {
                Result.failure(Exception())
            }
        }
    }
}