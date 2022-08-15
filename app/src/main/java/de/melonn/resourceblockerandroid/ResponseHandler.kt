package de.melonn.resourceblockerandroid

import android.widget.Toast

class ResponseHandler(private val ma: MainActivity) {
    private val resources = ResourceStatusDAO.resources

    fun resourceStatsReceived(statsResponse: Map<String, ResourceStatusResponse>){

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

        ma.displayLoading(false)
    }

    fun resourceStatReceived(id: String, statusResponse: ResourceStatusResponse) {
        val index = resources.indexOfFirst { it.id == id }
        resources[index].num = statusResponse.num

        ma.notifyResourceChanged(index)
        ma.displayLoading(false)
    }

    fun error(type: ErrorType) {
        ma.displayError(type)
    }

}