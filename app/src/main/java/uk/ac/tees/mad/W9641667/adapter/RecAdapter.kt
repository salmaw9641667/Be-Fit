package uk.ac.tees.mad.W9641667.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import uk.ac.tees.mad.W9641667.R
import uk.ac.tees.mad.W9641667.data.Rec

class RecAdapter(private val context: Context, private val dataSource: List<Rec>) : ArrayAdapter<Rec>(context, 0, dataSource) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_rec, parent, false)
        val recipe = getItem(position)

        val titleTextView = view.findViewById<TextView>(R.id.recipeTitle)
        val detailsTextView = view.findViewById<TextView>(R.id.recipeDetails)
        val imageView = view.findViewById<ImageView>(R.id.recipeImage)

        titleTextView.text = recipe?.title
        detailsTextView.text = "Calories: ${recipe?.calories}, Carbs: ${recipe?.carbs}, Fat: ${recipe?.fat}, Protein: ${recipe?.protein}"
        Glide.with(context).load(recipe?.image).into(imageView)

        return view
    }
}
