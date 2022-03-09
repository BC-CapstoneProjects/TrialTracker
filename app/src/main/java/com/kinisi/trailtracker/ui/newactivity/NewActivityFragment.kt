package com.kinisi.trailtracker.ui.newactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorites.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.net.ssl.HttpsURLConnection


import android.util.Log

import com.kinisi.trailtracker.models.DataModel
import org.json.JSONArray
import org.json.JSONTokener
import kotlin.collections.ArrayList
import com.kinisi.trailtracker.databinding.FragmentDashboardBinding

class NewActivityFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var adapter: RecyclerAdapter
    lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        parseJSON()
        var tmpArr: ArrayList<DataModel> = ArrayList()
        super.onViewCreated(itemView, savedInstanceState)
        recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = RecyclerAdapter(tmpArr)

        }

    }

    fun parseJSON(): java.util.ArrayList<DataModel> {
        var tmpArr: java.util.ArrayList<DataModel> = java.util.ArrayList()
        GlobalScope.launch(Dispatchers.IO) {
            val url =
                  URL("https://eflask-kinisi.herokuapp.com/api/closest&n=15&47.503900920958415,-122.04708518140542")
                 // URL("https://eflask-kinisi.herokuapp.com/api/closest&n=5&45.9604792,-123.6870344")
            val httpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.setRequestProperty(
                "Accept",
                "application/json"
            ) // The format of response we want to get from the server
            httpsURLConnection.requestMethod = "GET"
            httpsURLConnection.doInput = true
            httpsURLConnection.doOutput = false
            // Check if the connection is successful
            val responseCode = httpsURLConnection.responseCode
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {

                    val jsonObject = JSONTokener(response).nextValue() as JSONArray

                    for (i in 0 until jsonObject.length()) {
                        val jsonArray = jsonObject.getJSONObject(i)
                        // ID
                        val id = jsonArray.getString("id")
                        Log.i("ID: ", id)

                        val tags = jsonArray.getJSONObject("tags")
                        val name = tags.getString("name")
                        val type = tags.getString("sac_scale")
                        val dist = tags.getDouble("dist")

                        Log.i("Dist", dist.toString())
                        Log.i("Name ", name)
                        Log.i("Type ", type)

                        val model = DataModel(
                            id,
                            name,
                            type,
                            "%.2f".format(dist).toDouble().toString() + " mi"
                        )

                        tmpArr.add(model)
                        adapter = RecyclerAdapter(tmpArr)
                        adapter!!.notifyDataSetChanged()
                        Log.d("model: ", model.toString())
                        Log.d("tmp arr: ", tmpArr.toString())

                    }

                    binding.recyclerView.adapter = adapter
                }
            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
        }
        return tmpArr
    }

}