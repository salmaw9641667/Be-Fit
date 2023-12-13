package uk.ac.tees.mad.W9641667

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import uk.ac.tees.mad.W9641667.adapter.FoodItemAdapter
import uk.ac.tees.mad.W9641667.data.FoodItem

class CaloriesCounter : AppCompatActivity() {

    private lateinit var foodNameEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var resultsListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories_counter)

        foodNameEditText = findViewById(R.id.foodNameEditText)
        submitButton = findViewById(R.id.submitSearchButton)
        resultsListView = findViewById(R.id.resultsListView)

        submitButton.setOnClickListener {
            val foodName = foodNameEditText.text.toString()
            if (foodName.isNotEmpty()) {
                fetchFoodData(foodName)
            }
        }
    }

    private fun fetchFoodData(foodName: String) {
        val url = "https://dietagram.p.rapidapi.com/apiFood.php?name=$foodName&lang=en"
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null,
            { response ->
                try {
                    val dishesArray = response.getJSONArray("dishes")
                    val gson = Gson()
                    val type = object : TypeToken<List<FoodItem>>() {}.type
                    val foodItems: List<FoodItem> = gson.fromJson(dishesArray.toString(), type)
                    val adapter = FoodItemAdapter(foodItems)
                    resultsListView.adapter = adapter

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
                headers["X-RapidAPI-Host"] = "dietagram.p.rapidapi.com"
                return headers
            }
        }

        requestQueue.add(jsonObjectRequest)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

}