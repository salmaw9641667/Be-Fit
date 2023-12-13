package uk.ac.tees.mad.W9641667

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import uk.ac.tees.mad.W9641667.adapter.ExerciseAdapter
import uk.ac.tees.mad.W9641667.data.Exercise

class MuscleFragment(private val muscle: String) : Fragment() {

    private lateinit var exercisesListView: ListView
    private lateinit var adapter: ExerciseAdapter
    private var exercises: MutableList<Exercise> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_layout, container, false)
        exercisesListView = view.findViewById(R.id.exercisesListView)
        adapter = ExerciseAdapter(requireContext(), exercises)
        exercisesListView.adapter = adapter
        loadExercises(muscle)
        return view
    }

    private fun loadExercises(muscle: String) {
        val url = "https://exercises-by-api-ninjas.p.rapidapi.com/v1/exercises?muscle=$muscle"

        val req = Volley.newRequestQueue(requireContext())

        val jsonObjectRequest = object : JsonArrayRequest(Method.GET, url, null,
            { response ->
                try {
                    val gson = Gson()
                    val type = object : TypeToken<List<Exercise>>() {}.type
                    val newExercises: List<Exercise> = gson.fromJson(response.toString(), type)

                    exercises.clear()  // Clear existing items
                    exercises.addAll(newExercises)  // Add new items
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            }) {

            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["X-RapidAPI-Key"] = "d1fe910fd7msh526a88b9ac59227p1672adjsncdc55da13174"
                headers["X-RapidAPI-Host"] = "exercises-by-api-ninjas.p.rapidapi.com"
                return headers
            }
        }

        req.add(jsonObjectRequest)

    }
}
