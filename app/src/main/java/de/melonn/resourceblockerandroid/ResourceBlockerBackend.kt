package de.melonn.resourceblockerandroid

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.lang.Exception

data class ResourcesResponse(val stats: Map<String, ResourceStatusResponse>)
data class ResourceStatusResponse(val name: String, val num: Int)
data class ResourceStatus(val id: String, val name: String, val num: Int)
data class UpdateResourceRequest(val id: String, val delta: Int)

val JSON = "application/json".toMediaTypeOrNull()

enum class ErrorType {
    Connection,
    Internal
}

class ResourceBlockerBackend(host: String, port: Int) {

    private val baseAddress = "http://$host:$port/"
    private val client = OkHttpClient()

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val resourcesJsonAdapter = moshi.adapter(ResourcesResponse::class.java)
    private val statusJsonAdapter = moshi.adapter(ResourceStatusResponse::class.java)
    private val updateJsonAdapter = moshi.adapter(UpdateResourceRequest::class.java)

    fun requestResourceIds(resultCallback: (Map<String, ResourceStatusResponse>) -> Unit,
                           failureCallback: (ErrorType) -> Unit) {

        val request = Request.Builder().url(baseAddress).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                failureCallback(ErrorType.Connection)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val result = resourcesJsonAdapter.fromJson(response.body!!.source())
                    resultCallback(result!!.stats)
                } catch (e: Exception) {
                    e.printStackTrace()
                    failureCallback(ErrorType.Internal)
                }
            }
        })
    }

    fun updateResource(id: String, delta: Int,
                       resultCallback: (ResourceStatus) -> Unit,
                       failureCallback: (ErrorType) -> Unit) {

        val updateRequest = UpdateResourceRequest(id, delta)
        val updateRequestJson = updateJsonAdapter.toJson(updateRequest)
        val request = Request.Builder()
            .url(baseAddress + id)
            .post(updateRequestJson.toRequestBody(JSON))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                failureCallback(ErrorType.Connection)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val statusResponse = statusJsonAdapter.fromJson(response.body!!.source())!!
                    val status = ResourceStatus(id, statusResponse.name, statusResponse.num)
                    resultCallback(status)
                } catch (e: Exception) {
                    e.printStackTrace()
                    failureCallback(ErrorType.Internal)
                }
            }

        })

    }

}