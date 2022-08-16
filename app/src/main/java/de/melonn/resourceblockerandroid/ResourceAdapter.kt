package de.melonn.resourceblockerandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.melonn.resourceblockerandroid.databinding.ResourceCardBinding

class ResourceAdapter(private val server: ResourceBlockerBackend,
                      private val responseHandler: ResponseHandler)
    : RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ResourceCardBinding.bind(view)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.resource_card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val resource = ResourceStatusDAO.resources[position]
        viewHolder.binding.txtResourceName.text = resource.name
        viewHolder.binding.txtResourceNum.text = resource.num.toString()

        viewHolder.binding.btnResourceMinus.setOnClickListener {
            server.updateResource(resource.id, -1, responseHandler)
        }

        viewHolder.binding.btnResourcePlus.setOnClickListener {
            server.updateResource(resource.id, +1, responseHandler)
        }
    }

    override fun getItemCount() = ResourceStatusDAO.resources.size

}

