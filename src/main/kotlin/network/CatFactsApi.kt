package network

import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactsApi {
    @GET("facts")
    suspend fun getFacts(@Query("page") page: Int): CatFactsResponse
}