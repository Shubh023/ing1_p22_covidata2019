package fr.epita.android.covidata2019

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bar : Bar = dataList[position]
        var maxVal = 0
        for (x in dataList)
        {
            if (x.size > maxVal) {
                maxVal = x.size
            }
        }

        var month = ""

        /*
        var day = bar.date.subSequence(8,9).toString()
        var year = bar.date.subSequence(0,3).toString()
        if (bar.date.subSequence(4,6) == "01")
        {
            month = "January"
        }
        else if (bar.date.subSequence(5,6) == "02")
        {
            month = "February"
        }
        else if (bar.date.subSequence(5,6) == "03")
        {
            month = "March"
        }
        else if (bar.date.subSequence(5,6) == "04")
        {
            month = "April"
        }
        else if (bar.date.subSequence(5,6) == "05")
        {
            month = "May"
        }
        else if (bar.date.subSequence(5,6) == "06")
        {
            month = "June"
        }
        else if (bar.date.subSequence(5,6) == "07")
        {
            month = "July"
        }
        else if (bar.date.subSequence(5,6) == "08")
        {
            month = "August"
        }
        else if (bar.date.subSequence(5,6) == "09")
        {
            month = "September"
        }
        else if (bar.date.subSequence(5,6) == "10")
        {
            month = "October"
        }
        else if (bar.date.subSequence(5,6) == "11")
        {
            month = "November"
        }
        else if (bar.date.subSequence(5,6) == "12")
        {
            month = "December"
        }
        */
        holder.textViewName.text = bar.date
        holder.textViewCases.text = "Cases : "  + bar.size.toString()
        if (maxVal != 0)
        {
            maxVal = (bar.size * 100) / maxVal
        }
        holder.progress.progress = maxVal
    }
}