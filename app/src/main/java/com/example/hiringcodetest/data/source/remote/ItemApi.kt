package com.example.hiringcodetest.data.source.remote

import android.util.Log
import com.example.hiringcodetest.domain.model.Item
import com.example.hiringcodetest.domain.model.ServerResult
import com.example.hiringcodetest.utils.ErrorUtil
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class ItemApi @Inject constructor(
    private val retrofit: Retrofit,
    private val retrofitService: RetrofitService
) {

    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        errorMessage: String
    ): ServerResult<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return ServerResult.success(result.body())
            } else {
                val errorResponse = ErrorUtil.parseError(result, retrofit)
                ServerResult.error(errorResponse?.errorMessage ?: errorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            println(Log.d("API", e.message ?: "none"))
            ServerResult.error("Unknown Error", null)
        }
    }

    suspend fun getItemsList(): ServerResult<List<Item>> {
        return getResponse(
            request = { retrofitService.getItems() },
            errorMessage = "Error fetching items from server"
        )
    }
}