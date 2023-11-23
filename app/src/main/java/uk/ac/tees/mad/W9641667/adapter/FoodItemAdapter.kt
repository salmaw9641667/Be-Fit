package uk.ac.tees.mad.W9641667.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import uk.ac.tees.mad.W9641667.R
import uk.ac.tees.mad.W9641667.data.FoodItem

class FoodItemAdapter(private val items: List<FoodItem>) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.food_item, parent, false)
        val item = getItem(position) as FoodItem

        val tvFoodName: TextView = view.findViewById(R.id.tvFoodName)
        val tvCalories: TextView = view.findViewById(R.id.tvCalories)
        val tvCarbon: TextView = view.findViewById(R.id.tvCarbon)
        val tvFat: TextView = view.findViewById(R.id.tvFat)

        tvFoodName.text = item.name
        tvCalories.text = "Calories: ${item.caloric}"
        tvCarbon.text = "Carbs: ${item.carbon}g"
        tvFat.text = "Fat: ${item.fat}g"

        return view
    }
}
