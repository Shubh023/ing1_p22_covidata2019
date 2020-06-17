package fr.epita.android.covidata2019.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.epita.android.covidata2019.R
import fr.epita.android.covidata2019.models.Bar

class BarAdapter(val dataList : ArrayList<Bar>) : RecyclerView.Adapter<BarAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewName = itemView.findViewById(R.id.dateTextView) as TextView
        val textViewCases = itemView.findViewById(R.id.casesTextView) as TextView
        val progress = itemView.findViewById(R.id.progressBar) as ProgressBar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bar_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size;
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bar : Bar = dataList[position]
        var maxVal = 0
        for (x in dataList)
        {
            if (x.size > maxVal) {
                maxVal = x.size
            }
        }

        holder.textViewName.text = bar.date
        holder.textViewCases.text = "Cases : "  + bar.size.toString()
        if (maxVal != 0)
        {
            maxVal = (bar.size * 100) / maxVal
        }
        holder.progress.progress = maxVal
    }
}