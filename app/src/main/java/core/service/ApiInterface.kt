package core.service


import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
//import th.co.gisc.md.core.service.ServiceURL
import java.util.*

interface ApiInterface {

    @POST("{postUrl}")
    @Headers("Content-Type: text/plain")
    fun getDataFromURL(@Path("postUrl") postUrl: String, @Body body: HashMap<String, Any>): Call<JsonObject>

    @POST("{postUrl}")
    @Headers("Content-Type: text/plain")
    fun getDataFromURL(@Path("postUrl") postUrl: String, @Body body: HashMap<String, Any>, @Header("Authorization") authorization: String): Call<JsonObject>

    @Multipart
    @POST(ServiceURL.APP_FILE)
    @Headers("Content-Type: multipart/form-data")
    fun uploadFile(
        @Header("Authorization") authorization: String,
        @Part("ACTION") action: RequestBody,
        @Part("PATH") path: RequestBody,
        @Part files: Array<MultipartBody.Part?>
    ): Call<JsonObject>
}