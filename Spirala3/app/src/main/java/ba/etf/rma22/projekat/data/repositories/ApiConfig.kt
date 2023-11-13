package ba.etf.rma22.projekat.data.repositories

object ApiConfig {
    var baseURL = "https://rma22ws.herokuapp.com"

    fun postaviBaseURL(baseUrl: String) {
        ApiConfig.baseURL = baseUrl
    }
}