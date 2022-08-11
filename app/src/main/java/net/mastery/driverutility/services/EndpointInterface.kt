package net.mastery.driverutility.services

import net.mastery.driverutility.BuildConfig
import net.mastery.driverutility.models.Driver
import net.mastery.driverutility.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface EndpointInterface {

    @POST("login")
    fun signIn(@QueryName username: String, @QueryName password: String) : Call<User>

    @Headers("ClientId: ${BuildConfig.CLIENT_ID}")
    @GET("drivers")
    fun getDrivers() : Call<List<Driver>>

    companion object {

        var BASE_URL = "https://private-f699e3-masteryinterview.apiary-mock.com/"

        fun create() : EndpointInterface {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(EndpointInterface::class.java)
        }
    }
}