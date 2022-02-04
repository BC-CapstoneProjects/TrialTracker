package com.kinisi.trailtracker.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.kinisi.trailtracker.R
import kotlinx.android.synthetic.main.activity_update_profile.*

class UpdateProfile : AppCompatActivity() {
   // FirebaseFirestore db = FirebaseFirestore.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kinisi.trailtracker.R.layout.activity_update_profile)

        btnUpdateImage.setOnClickListener {
            // update the profile image here
            val picUpdate = UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse("new.photo.url.here"))
                .build()

            FirebaseAuth.getInstance().currentUser?.updateProfile(picUpdate)
        }

        btnUpdateName.setOnClickListener {
            // update the first name here
            val nameUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName(Name.text.toString())
                .build()

            FirebaseAuth.getInstance().currentUser?.updateProfile(nameUpdate)
        }

        btnUpdateHeight.setOnClickListener {
            // update the name here
            val nameUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName(height.text.toString())
                .build()

            FirebaseAuth.getInstance().currentUser?.updateProfile(nameUpdate)
        }


    }
}