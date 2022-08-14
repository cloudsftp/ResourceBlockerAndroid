package de.melonn.resourceblockerandroid

import android.widget.Toast

class ResponseHandler(private val ma: MainActivity) {

    fun resourceStatsReceived(statsResponse: Map<String, ResourceStatusResponse>){
        val resources = ResourceStatusDAO.resources

        val ids = resources.map { it.id }
        // val idsUpdated = mutableListOf<Int>()
        // val numIdsOriginal = ids.size

        statsResponse.forEach { (id, response) ->
            if (ids.contains(id)) {
                val index = resources.indexOfFirst { it.id == id }
                resources[index].num = response.num
                // idsUpdated.add(index)
            } else {
                resources.add(ResourceStatus(id, response.name, response.num))
            }
        }

        ma.notifyResourceAdapter()
        // ma.notifyDataChanged(idsUpdated)
        // ma.notifyDataAdded(numIdsOriginal, resources.size - numIdsOriginal)

    }

    fun resourceStatReceived(id: String, statusResponse: ResourceStatusResponse) {
        // TODO get position of resource in list, update resource
    }

    fun error(type: ErrorType) {
        ma.displayError(type)
    }

}