package de.melonn.resourceblockerandroid

import android.widget.Toast

class ResponseHandler(private val resourceAdapter: ResourceAdapter, private val ma: MainActivity) {

    fun resourceStatsReceived(statsResponse: Map<String, ResourceStatusResponse>){
        val resources = resourceAdapter.resources

        val ids = resources.map { it.id }
        val idsUpdated = mutableListOf<Int>()
        val numIdsOriginal = ids.size

        statsResponse.forEach { (id, response) ->
            if (ids.contains(id)) {
                val index = resources.indexOfFirst { it.id == id }
                resources[index].num = response.num
                idsUpdated.add(index)
            } else {
                resources.add(ResourceStatus(id, response.name, response.num))
            }
        }

        ma.runOnUiThread {
            idsUpdated.forEach { resourceAdapter.notifyItemChanged(it) }
            if (numIdsOriginal < ids.size) {
                resourceAdapter.notifyItemRangeInserted(numIdsOriginal, ids.size - numIdsOriginal)
            }
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