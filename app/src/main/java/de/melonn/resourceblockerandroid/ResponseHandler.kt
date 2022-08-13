package de.melonn.resourceblockerandroid

import android.annotation.SuppressLint
import android.widget.Toast

class ResponseHandler(private val resources: MutableList<ResourceStatus>, private val ma: MainActivity) {

    @SuppressLint("NotifyDataSetChanged")
    fun resourceStatsReceived(statsResponse: Map<String, ResourceStatusResponse>): Unit {
        // TODO check if all IDs exist, add missing
        // TODO update all existing IDs

        resources.add(ResourceStatus("test", "Test", 1))
        resources.add(ResourceStatus("test", "Test", 1))

        ma.runOnUiThread {
            ma.resourceAdapter.notifyDataSetChanged()
        }

    }

    fun resourceStatReceived(id: String, statusResponse: ResourceStatusResponse) {
        // TODO get position of resource in list, update resource
    }

    fun error(type: ErrorType) {
        ma.runOnUiThread {
            val text = when (type) {
                ErrorType.Internal -> R.string.internal_error
                else -> R.string.connection_error
            }

            Toast.makeText(ma.applicationContext, text, Toast.LENGTH_LONG).show()

        }
    }

}