package id.renaldi.alteacarepretest.utility.base

import id.renaldi.alteacarepretest.data.network.exception.NoInternetException
import id.renaldi.alteacarepretest.data.network.exception.NotFoundException
import id.renaldi.alteacarepretest.data.network.exception.UnknownException
import id.renaldi.alteacarepretest.utility.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRepository {

    protected fun <T : Any> flowNetworkCall(call: suspend () -> Response<T>): Flow<T> = flow {
        when (val result = createNetworkCall { call() }) {
            is DataResult.Success -> emit(result.data)
            is DataResult.Error -> throw result.error
        }
    }.flowOn(Dispatchers.IO)

    /**
     * @param Retrofit API Response
     * @return save typed DataResult
     */
    private suspend fun <T : Any> createNetworkCall(call: suspend () -> Response<T>): DataResult<T> {

        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            t.printStackTrace()
            return DataResult.Error(mapToNetworkError(t))
        }

        if (response.isSuccessful) {
            if (response.body() != null) return DataResult.Success(response.body()!!)
        } else {
            val errorBody = response.errorBody()
            return if (errorBody != null) DataResult.Error(mapApiException(response.code()))
            else DataResult.Error(UnknownHostException())
        }

        return DataResult.Error(HttpException(response))
    }

    private fun mapApiException(code: Int): Exception {
        return when (code) {
            HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException()
            else -> UnknownException()
        }
    }

    private fun mapToNetworkError(t: Throwable): Exception {
        return when (t) {
            is SocketTimeoutException -> SocketTimeoutException("Connection Timed Out")
            is UnknownHostException -> NoInternetException()
            else -> UnknownException()
        }
    }
}