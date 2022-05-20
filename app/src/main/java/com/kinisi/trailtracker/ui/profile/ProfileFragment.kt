package com.kinisi.trailtracker.ui.profile

import android.content.Intent
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
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.kinisi.trailtracker.MainActivity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
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

        //Brings to reviews activity on review button click
        val reviewBtn: Button = binding.reviewsButton

        reviewBtn.setOnClickListener {
            val intent = Intent(context, ReviewsActivity::class.java)
            startActivity(intent)
        }

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

        val shareBtn: Button = binding.settingsButton
        shareBtn.setOnClickListener {
            copyTextToClipboard()
            pasteTextFromClipboard()
        }

        return root
    }

    private fun copyTextToClipboard() {
        var textToCopy = "Hello World"
        val clipboardManager = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
     //   Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }

    private fun pasteTextFromClipboard() {
        val clipboardManager = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var textToPaste = clipboardManager.primaryClip?.getItemAt(0)?.text
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        shareButton.setOnClickListener {
//            copyTextToClipboard()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}