package fr.epita.android.covidata2019

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BarAdapter(val dataList : ArrayList<Bar>) : RecyclerView.Adapter<BarAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewName = itemView.findViewById(R.id.dateTextView) as TextView
        val textViewCases = itemView.findViewById(R.id.casesTextView) as TextView
        val progress = itemView.findViewById(R.id.progressBar) as ProgressBar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.bar_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bar : Bar = dataList[position]
        var maxVal = 0
        for (x in dataList)
        {
            if (x.size >= maxVal) {
                maxVal = x.size
            }
        }

        holder?.textViewName.text = bar.date.toString()
        holder?.textViewCases.text = "Cases : " + bar.size.toString()
        var size = 0
        if (maxVal == 0)
        {
            size = bar.size
        }
        else{
            size = bar.size / maxVal
        }
        holder?.progress.progress = size
    }
}