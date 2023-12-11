package uk.ac.tees.mad.W9641667

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import uk.ac.tees.mad.W9641667.adapter.RecAdapter
import uk.ac.tees.mad.W9641667.data.Rec

class DietPlan : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: RecAdapter
    private lateinit var minCaloriesInput: EditText
    private lateinit var maxCaloriesInput: EditText
    private lateinit var searchButton: Button
    private var recipesList: MutableList<Rec> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plan)

        listView = findViewById(R.id.listView)
        minCaloriesInput = findViewById(R.id.minCaloriesInput)
        maxCaloriesInput = findViewById(R.id.maxCaloriesInput)
        searchButton = findViewById(R.id.searchButton)

        adapter = RecAdapter(this, recipesList)
        listView.adapter = adapter

        searchButton.setOnClickListener {
            val minCalories = minCaloriesInput.text.toString()
            val maxCalories = maxCaloriesInput.text.toString()
            if (minCalories.isNotBlank() && maxCalories.isNotBlank()) {
                loadRecipes(minCalories, maxCalories)
            }
        }
    }

    private fun loadRecipes(minCalories: String, maxCalories: String) {
        val url = "https://api.spoonacular.com/recipes/findByNutrients?minCalories=$minCalories&maxCalories=$maxCalories&number=10&apiKey=b9be65b94d994b5fa702cf79bcc6bfa7"
        val requestQueue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                val gson = Gson()
                val type = object : TypeToken<List<Rec>>() {}.type
                val newRecipes: List<Rec> = gson.fromJson(response.toString(), type)

                recipesList.clear()
                recipesList.addAll(newRecipes)
                adapter.notifyDataSetChanged()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error ->
            error.printStackTrace()
        })

        requestQueue.add(jsonArrayRequest)
    }

}