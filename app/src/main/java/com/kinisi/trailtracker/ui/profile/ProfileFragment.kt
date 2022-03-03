package com.kinisi.trailtracker.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kinisi.trailtracker.R
import com.kinisi.trailtracker.databinding.FragmentHomeBinding
import com.kinisi.trailtracker.databinding.FragmentProfileBinding
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.kinisi.trailtracker.MainActivity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

      /*  val textView: TextView = binding.textProfile
        profileViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        //Brings to history activity on history button click
        val historyBtn: Button = binding.historyButton

        historyBtn.setOnClickListener {
            val intent = Intent(context, HistoryActivity::class.java)
            startActivity(intent)
        }

        //Brings to Stats activity on stats button click
        val statsBtn: Button = binding.statsButton

        statsBtn.setOnClickListener {
            val intent = Intent(context, Stats::class.java)
            startActivity(intent)
        }



        //Brings to UpdateProfile on settings button click
        val settingsBtn: Button = binding.settingsButton

        settingsBtn.setOnClickListener {
            val intent = Intent(context, UpdateProfile::class.java)
            startActivity(intent)
        }

        return root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




   }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}