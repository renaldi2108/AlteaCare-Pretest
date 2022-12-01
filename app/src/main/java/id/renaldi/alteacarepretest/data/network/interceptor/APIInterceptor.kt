package id.renaldi.alteacarepretest.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class APIInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().newBuilder()
            .header("Accept", "application/json")
            .build()

        return chain.proceed(req)
    }
}