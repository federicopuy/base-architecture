package com.android.federicopuy.basearchitecture.networking

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * Wraps the result of an api call
 */
sealed class ApiResult<out T> {
    data class Success<out T>(val value: T) : ApiResult<T>()
    sealed class Error(open val code: Int) : ApiResult<Nothing>() {
        data class Http(override val code: Int, val errorBody: ErrorBody?) : Error(code)
        object Network : Error(HttpURLConnection.HTTP_NOT_FOUND)
        data class UnknownError(val t:Throwable)
    }
}

data class ErrorBody(@SerializedName("message") val message: String? = null)

/**
 * Executes api call inside try/catch and wraps results inside ApiResult
 *
 * @param apiCall that will be executed
 *
 * @return the result wrapped inside a sealed class
 */
suspend fun <T> suspendApiCallCoroutine(apiCall: suspend () -> T): ApiResult<T> {
    return suspendCoroutine { suspend ->
        CoroutineScope(suspend.context).launch {
            val wrapper = try {
                val result = apiCall.invoke()
                ApiResult.Success(result)
            } catch (e: HttpException) {
                val (code, body) = e.extract()
                ApiResult.Error.Http(code, body)
            } catch (e: UnknownHostException) {
                ApiResult.Error.Network
            } catch (e: Throwable) {
                ApiResult.Error.UnknownError(e)
                return@launch
            }
            suspend.resume(wrapper)
        }
    }
}

private fun HttpException.extract(): Pair<Int, ErrorBody?> {
    val body = try {
        response()?.errorBody()
            ?.string()
            ?.let { Gson().fromJson(it, ErrorBody::class.java) }
    } catch (e: JsonSyntaxException) {
        null
    }
    return Pair(code(), body)
}