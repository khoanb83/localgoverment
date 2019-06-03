

import com.google.gson.JsonObject
import core.service.ApiInterface
import core.service.BaseService
import core.service.ServiceURL
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class ServiceManager {
    companion object Factory {
        private fun instance(): ApiInterface {
            return BaseService.create()
        }
        fun callLogin(username: String, password: String): Call<JsonObject> {
            val params: HashMap<String, Any> = hashMapOf(
                "USERNAME" to username,
                "PASSWORD" to password
            )
            return instance().getDataFromURL(ServiceURL.APP_LOGIN, params)
        }
    }
}