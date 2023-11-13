package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {

    // Istrazivanje
    @GET("istrazivanje")
    suspend fun getAllIstrazivanja(@Query("offset") offset: Int): Response<List<Istrazivanje>>

    @GET("istrazivanje/{id}")
    suspend fun getIstrazivanjeById(@Path("id") id: Int): Call<Istrazivanje>

    @GET("grupa/{gid}/istrazivanje")
    suspend fun getIstrazivanjaByGrupaId(@Path("gid") gid: Int): Call<List<Istrazivanje>>


    // Grupa

    // Metoda ispod vraca grupe gdje je anketa dostupna
    @GET("anketa/{id}/grupa")
    suspend fun getGrupeByAnketaId(@Path("id") id: Int): Response<List<Grupa>>

    @POST("grupa/{gid}/student/{id}")
    suspend fun addStudentWithHashIdToAGroupWithId(@Path("gid") gid: Int, @Path("id") id: String): Response<ResponseMessage>

    @GET("student/{id}/grupa")
    suspend fun getUpisaneGrupeByHashId(@Path("id") id: String): Response<List<Grupa>>

    @GET("grupa")
    suspend fun getAllGrupe(): Response<List<Grupa>>

    @GET("grupa/{id}")
    suspend fun getGrupaById(@Path("id") id: Int): Response<Grupa>

    // Anketa

    @GET("anketa")
    suspend fun getAllAnketa(@Query("offset") offset: Int): Response<List<Anketa>>

    @GET("anketa/{id}")
    suspend fun getAnketaById(@Path("id") id: Int): Response<Anketa>

    @GET("grupa/{id}/ankete")
    suspend fun getAnketeByGrupaId(@Path("id") id: Int): Response<List<Anketa>>

    // Odgovor
    @GET("student/{id}/anketataken/{ktid}/odgovori")
    suspend fun getOdgovoriForAnketaAndStudent(@Path("id") id: String, @Path("ktid") ktid: Int): Response<List<Odgovor>>

    // todo provjeriti
    @POST("student/{id}/anketataken/{ktid}/odgovor")
    suspend fun postOdgovoriForAnketaAndStudent(@Path("id") id: String, @Path("ktid") ktid: Int, @Body odgovorBody: OdgovorBody): Response<Odgovor>

    // AnketaTaken
    @GET("/student/{id}/anketataken")
    suspend fun getPokusajiByHash(@Path("id") id: String): Response<List<AnketaTaken>>

    @POST("/student/{id}/anketa/{kid}")
    suspend fun postZapocniPokusaj(@Path("id") id: String, @Path("kid") kid: Int): AnketaTaken
    // Account

    // Pitanje
    @GET("anketa/{id}/pitanja")
    suspend fun getPitanjaByAnketaId(@Path("id") id: Int): Response<List<Pitanje>>
}