package de.melonn.resourceblockerandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.melonn.resourceblockerandroid.databinding.ResourceCardBinding

class ResourceAdapter : RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {
    val resources = mutableListOf<ResourceStatus>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ResourceCardBinding.bind(view)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.resource_card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.textView.text = resources[position].name
    }

    override fun getItemCount() = resources.size

}

