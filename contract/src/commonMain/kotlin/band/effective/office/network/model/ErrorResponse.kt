package band.effective.office.network.model

data class ErrorResponse(val code: Int, val description: String) {
    companion object {
        /**Create response by code*/
        fun getResponse(code: Int): ErrorResponse {
            val description = when (code) {
                404 -> "Not found"
                in 400..499 -> "Client error"
                in 500..599 -> "Server error"
                else -> "Unknown error"
            }
            return ErrorResponse(code, description)
        }
    }
}