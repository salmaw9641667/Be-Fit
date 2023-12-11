package uk.ac.tees.mad.W9641667.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import uk.ac.tees.mad.W9641667.R
import uk.ac.tees.mad.W9641667.data.Exercise

class ExerciseAdapter(private val context: Context, private val dataSource: List<Exercise>) : ArrayAdapter<Exercise>(context, 0, dataSource) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)
        val exercise = getItem(position)


        val nameTextView = view.findViewById<TextView>(R.id.exerciseName)
        val typeTextView = view.findViewById<TextView>(R.id.exerciseType)
        val equipmentTextView = view.findViewById<TextView>(R.id.exerciseEquipment)
        val difficultyTextView = view.findViewById<TextView>(R.id.exerciseDifficulty)
        val instructionsTextView = view.findViewById<TextView>(R.id.exerciseInstructions)

        nameTextView.text = exercise?.name
        typeTextView.text = "Type: ${exercise?.type}"
        equipmentTextView.text = "Equipment: ${exercise?.equipment}"
        difficultyTextView.text = "Difficulty: ${exercise?.difficulty}"
        instructionsTextView.text = "Instructions: ${exercise?.instructions}"


        return view
    }
}
