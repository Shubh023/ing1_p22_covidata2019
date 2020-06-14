package fr.epita.android.covidata2019.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.epita.android.covidata2019.R
import fr.epita.android.covidata2019.models.Country
import kotlinx.android.synthetic.main.list_item.view.*

class CountryAdapter(private val dataList : ArrayList<Country>, private val countryClicked: OnCountryListener)
    : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View, countryClicked: OnCountryListener) : RecyclerView.ViewHolder(
        itemView
    )
    {
        val textCountryName : TextView = itemView.findViewById(R.id.countryName) as TextView
        private val listener : OnCountryListener = countryClicked

        init {
            textCountryName.setOnClickListener{
                listener.onCountryClick(it.countryName.text.toString())
            }
        }
    }
    //private val listener : OnCountryListener = countryClicked
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(
            v,
            countryClicked
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country : Country = dataList[position]
        holder.textCountryName.text = country.country
    }



    public interface OnCountryListener {
        fun onCountryClick(name : String)
    }

}