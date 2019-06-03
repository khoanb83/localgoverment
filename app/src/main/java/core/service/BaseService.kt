package core.service
import android.annotation.SuppressLint
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class BaseService {

    /** * Companion object to create the BaseService */
    companion object Factory {

        private const val TIMEOUT = 10000

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ServiceURL.BASE_URL)
                .client(getClient())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

        private fun getClient(): OkHttpClient {
            val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
//            val okHttpClient = OkHttpClient.Builder()
            return okHttpClient
                .certificatePinner(getDefaultCertificatePinner())
                .readTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .hostnameVerifier(getHostnameVerifier())
                .build()
        }

        private fun getDefaultCertificatePinner(): CertificatePinner {
            return CertificatePinner.Builder().build()
        }

        private fun getHostnameVerifier():HostnameVerifier {
            return object: HostnameVerifier {
                @SuppressLint("BadHostnameVerifier")
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            }
        }

    }

}
