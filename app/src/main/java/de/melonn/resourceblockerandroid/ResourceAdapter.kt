package de.melonn.resourceblockerandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.melonn.resourceblockerandroid.databinding.ResourceCardBinding

class ResourceAdapter(private val responseHandler: ResponseHandler)
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
        viewHolder.binding.txtResourceName.text = ResourceStatusDAO.resources[position].name
        viewHolder.binding.txtResourceNum.text = ResourceStatusDAO.resources[position].num.toString()
    }

    override fun getItemCount() = ResourceStatusDAO.resources.size

}

