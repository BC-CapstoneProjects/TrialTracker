package com.kinisi.trailtracker.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kinisi.trailtracker.R
import kotlinx.android.synthetic.main.activity_update_profile.*
import java.util.*
import android.widget.RadioGroup.*
class UpdateProfile : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    //lateinit var db: Fire
    //Firebase db = FirebaseFirestore.getInstance()
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kinisi.trailtracker.R.layout.activity_update_profile)
        auth=FirebaseAuth.getInstance()

        //Set maximum date to current date
        dob.setMaxDate(Date().time)
       // val name = db.collection("users/"+auth.currentUser!!.uid+"/name");




        btnUpdateProfile.setOnClickListener {
                    //Update image

                    //Collect info
                    val name = Name.text.toString()
                    val DOB = Timestamp(Date(dob.year - 1900, dob.month, dob.dayOfMonth))
                    val height = height.text.toString()
                    val weight = weight.text.toString()
                    val sex = sex_RadioGroup.findViewById<RadioButton>(sex_RadioGroup.checkedRadioButtonId).text.toString()

                    //Set info to user
                    val user = hashMapOf(
                        "name" to name,
                        "height" to height,
                        "weight" to weight,
                        "dob" to DOB,
                        "sex" to sex
                    )
                    auth.currentUser?.let { it1 ->
                        db.collection("users").document(auth.currentUser!!.uid)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(
                                    "docSnippets",
                                    "DocumentSnapshot successfully written!"
                                )
                            }
                            .addOnFailureListener { e ->
                                Log.w(
                                    "docSnippets",
                                    "Error writing document",
                                    e
                                )
                            }
                    }

                }

//        btnUpdateImage.setOnClickListener {
//            // update the profile image here
//            val picUpdate = UserProfileChangeRequest.Builder()
//                .setPhotoUri(Uri.parse("new.photo.url.here"))
//                .build()
//            FirebaseAuth.getInstance().currentUser?.updateProfile(picUpdate)
//        }



    }
}