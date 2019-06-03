package core.service
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ServiceCallback<T> : Callback<T> {

    abstract fun onSuccess(response: JsonObject)

    abstract fun onFailure(t: Throwable)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            val body = response.body()
            val rawJson = body.toString()
            val json: JsonObject = JsonParser().parse(rawJson).asJsonObject!!
            if (json.get("success").asBoolean) {
                try {
                    onSuccess(json)
                } catch (e: Exception) {
                    onFailure(e)
                }
            } else {
                onFailure(Throwable(json.get("message").asString))
            }
        } else {
            onFailure(Throwable(response.message() ?: "Response Invalid"))
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFailure(t)
    }

}