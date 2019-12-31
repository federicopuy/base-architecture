package com.android.federicopuy.basearchitecture.networking

import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import org.mockito.Mockito.`when` as whenever

class NetworkingTest {

    @Test
    fun `when suspendApiCallCoroutine returns successfully, Success is emitted`() {
        runBlocking {
            val lambdaResult = true
            val result =
                suspendApiCallCoroutine { lambdaResult }
            assertEquals(ApiResult.Success(lambdaResult), result)
        }
    }

    @Test
    fun `when suspendApiCallCoroutine throws HttpException`() {
        runBlocking {
            val errorBody = ErrorBody(
                message = "Error"
            )

            val responseBody = mock(ResponseBody::class.java)
            whenever(responseBody.string()).thenReturn("{\"message\": \"Error\"}")

            val result =
                suspendApiCallCoroutine {
                    throw HttpException(Response.error<Any>(422, responseBody))
                }

            assertEquals(ApiResult.Error.Http(422, errorBody), result)
        }
    }

    @Test
    fun `when suspendApiCallCoroutine throws UnknownHostException`() {
        runBlocking {
            val result =
                suspendApiCallCoroutine {
                    throw UnknownHostException()
                }
            assertEquals(ApiResult.Error.Network, result)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `when suspendApiCallCoroutine throws unknown exception`() {
        runBlocking {
            suspendApiCallCoroutine {
                throw IllegalStateException()
            }
        }
    }
}