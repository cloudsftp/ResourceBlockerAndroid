package de.melonn.resourceblockerandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.melonn.resourceblockerandroid.databinding.ResourceCardBinding

class ResourceAdapter(private val resources: List<ResourceStatus>) :
    RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ResourceCardBinding.bind(view)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.resource_card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var text = "bla"

        for (i in 0..position) {
            text += "a"
        }

        viewHolder.binding.textView.text = text
    }

    override fun getItemCount() = resources.size

}

