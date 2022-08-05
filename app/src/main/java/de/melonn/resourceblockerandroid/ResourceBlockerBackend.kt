package de.melonn.resourceblockerandroid

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException

data class ResourcesResponse(val resources: List<String>)
data class ResourceStatus(val name: String, val num: Int)
data class UpdateResourceRequest(val delta: Int)

class ResourceBlockerBackend(host: String, port: Int) {

    private val baseAddress = "http://$host:$port/"
    private val client = OkHttpClient()

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val resourcesJsonAdapter = moshi.adapter(ResourcesResponse::class.java)
    private val statusJsonAdapter = moshi.adapter(ResourceStatus::class.java)
    private val updateJsonAdapter = moshi.adapter(UpdateResourceRequest::class.java)

    fun requestResourceIds( resultCallback: (List<String>) -> Unit,
                            failureCallback: () -> Unit) {

        val request = Request.Builder().url(baseAddress).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                failureCallback()
            }

            override fun onResponse(call: Call, response: Response) {
                val result = resourcesJsonAdapter.fromJson(response.body!!.source())
                resultCallback(result!!.resources)
            }
        })
    }

}