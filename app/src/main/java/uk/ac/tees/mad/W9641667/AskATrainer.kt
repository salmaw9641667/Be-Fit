package uk.ac.tees.mad.W9641667

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AskATrainer : AppCompatActivity() {

    private lateinit var categorySpinner: Spinner
    private lateinit var problemInput: EditText
    private lateinit var uploadImageButton: Button
    private lateinit var selectedImageView: ImageView
    private lateinit var askButton: Button
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_atrainer)


        categorySpinner = findViewById(R.id.categorySpinner)
        problemInput = findViewById(R.id.problemInput)
        uploadImageButton = findViewById(R.id.uploadImageButton)
        selectedImageView = findViewById(R.id.selectedImageView)
        askButton = findViewById(R.id.askButton)

        uploadImageButton.setOnClickListener {
            openImageChooser()
        }

        askButton.setOnClickListener {
            submitData()
        }
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 564)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 564 && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            selectedImageView.visibility = View.VISIBLE
            selectedImageView.setImageURI(selectedImageUri)
        }
    }

    private fun submitData() {
        val selectedCategory = categorySpinner.selectedItem.toString()
        val problemDescription = problemInput.text.toString()

        if (selectedImageUri != null && problemDescription.isNotBlank()) {
            // Upload image to Firebase Storage
            val imageRef = FirebaseStorage.getInstance().reference.child("images/${selectedImageUri!!.lastPathSegment}")
            val uploadTask = imageRef.putFile(selectedImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()

                    // Save data to Firebase Realtime Database
                    val dataToSave = mapOf(
                        "category" to selectedCategory,
                        "problem" to problemDescription,
                        "image" to imageUrl
                    )

                    val databaseRef = FirebaseDatabase.getInstance().getReference("trainerRequests")
                    val requestId = databaseRef.push().key
                    requestId?.let {
                        databaseRef.child(it).setValue(dataToSave)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Data submitted successfully", Toast.LENGTH_SHORT).show()
                           finish()
                            }
                            .addOnFailureListener {

                                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }.addOnFailureListener {

                Toast.makeText(this, "Image upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        } else {

            Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show()
        }
    }

}