package com.kinisi.trailtracker.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.kinisi.trailtracker.R
import com.kinisi.trailtracker.databinding.FragmentHomeBinding
import com.github.mikephil.charting.data.LineData




class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val lChart: LineChart = binding.progressLineChart
        val bChart: BarChart = binding.progressBarChart

        setLineChart(bChart,lChart)

        return root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLineChart( bChart:BarChart, lChart:LineChart) {

       // var title = "Bar Chart"
        println("CHART: "+ bChart)


        //x values
        val labels = ArrayList<String>()
        labels.add("Sunday")
        labels.add("Monday")
        labels.add("Tuesday")
        labels.add("Wednesday")
        labels.add("Thursday")
        labels.add("Friday")
        labels.add("Saturday")

        //y values
        val entries = ArrayList<BarEntry>()//: MutableList<BarEntry> = ArrayList()
        entries.add(BarEntry(1f, 4.6f))
        entries.add(BarEntry(2f, 4.0f))
        entries.add(BarEntry(3f, 5.0f))
        entries.add(BarEntry(4f, 6.5f))
        entries.add(BarEntry(5f,6.8f))
        entries.add(BarEntry(6f, 6.5f))
        entries.add(BarEntry(7f, 7.0f))


        //bar data set
        val set = BarDataSet(entries, "BarDataSet")
        set.setColor(resources.getColor(R.color.purple_200))
        val data = BarData(set)
        data.barWidth = 0.9f // set custom bar width
        println(data.dataSets)
        bChart.data = data

        //        hide grid lines
        bChart.axisLeft.setDrawGridLines(false)
        val xAxis1: XAxis = bChart.xAxis
        xAxis1.setDrawGridLines(false)
//        xAxis1.setDrawAxisLine(false)

        //remove right y-axis
        bChart.axisRight.isEnabled = false

        bChart.description.text = "Miles moved this week"
        bChart.description.setPosition(700f, 70f)
        bChart.description.setTextSize(15f)

        // to draw label on xAxis
        xAxis1.position = XAxis.XAxisPosition.BOTTOM
        //xAxis.valueFormatter = ""
        xAxis1.setDrawLabels(true)
        xAxis1.granularity = 1f
        xAxis1.labelRotationAngle = +90f

        bChart.setFitBars(true) // make the x-axis fit exactly all bars

//                remove legend
//        bChart.legend.isEnabled = false


        bChart.invalidate() // refresh
        bChart.setGridBackgroundColor(resources.getColor(R.color.purple_200))

        bChart.animateY(5000)



//         ******************************************************************
//        LineChart

        //x values
        val labels2 = ArrayList<String>()
        labels2.add("Sunday")
        labels2.add("Monday")
        labels2.add("Tuesday")
        labels2.add("Wednesday")
        labels2.add("Thursday")
        labels2.add("Friday")
        labels2.add("Saturday")

        //y values
        val lEntries = ArrayList<Entry>()
        lEntries.add(Entry(0f, 3f))
        lEntries.add(Entry(1f, 8f))
        lEntries.add(Entry(2f, 6f))
        lEntries.add(Entry(3f, 5f))
        lEntries.add(Entry(4f, 7f))
        lEntries.add(Entry(5f, 6f))
        lEntries.add(Entry(6f, 3f))
        lEntries.add(Entry(7f, 8f))
        lEntries.add(Entry(8f, 6f))
        lEntries.add(Entry(9f, 5f))
        lEntries.add(Entry(10f, 7.0f))
        lEntries.add(Entry(11f, 6.0f))
        lEntries.add(Entry(12f, 5.5f))
        lEntries.add(Entry(13f, 4.5f))
        lEntries.add(Entry(14f, 3.0f))
        lEntries.add(Entry(15f, 2.0f))
        lEntries.add(Entry(16f, 5.0f))
        lEntries.add(Entry(17f, 5.6f))
        lEntries.add(Entry(18f, 4.5f))
        lEntries.add(Entry(19f, 7.5f))
        lEntries.add(Entry(20f, 6.0f))
        lEntries.add(Entry(21f, 6.5f))
        lEntries.add(Entry(22f, 6.2f))
        lEntries.add(Entry(23f, 5.5f))
        lEntries.add(Entry(24f, 4.6f))
        lEntries.add(Entry(25f, 4.0f))
        lEntries.add(Entry(26f, 5.0f))
        lEntries.add(Entry(27f, 6.5f))
        lEntries.add(Entry(28f, 6.8f))
        lEntries.add(Entry(29f, 6.5f))
        lEntries.add(Entry(30f, 7.0f))


        print("entries:$lEntries")

        //line data set
        val lSet = LineDataSet(lEntries, "LineDataSet")
        lSet.setColor(resources.getColor(R.color.teal_200))
        //val data = LineData(set)
        val lData = LineData(lSet)

        // set data
        if (lChart != null) {
            lChart.setData(lData)
            lChart.invalidate() // refresh
            lChart.setGridBackgroundColor(resources.getColor(R.color.purple_200))
            lChart.animateX(5000)


            if (lChart != null) {
                //hide grid lines
                lChart.axisLeft.setDrawGridLines(false)
                val xAxis: XAxis = lChart.xAxis
                xAxis.setDrawGridLines(false)
               // xAxis.setDrawAxisLine(false)

                // to draw label on xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                //xAxis.valueFormatter = ""
                xAxis.setDrawLabels(true)
                xAxis.granularity = 1f
                xAxis.labelRotationAngle = +90f
            }

            //remove right y-axis
            if (lChart != null) {
                lChart.axisRight.isEnabled = false
            }

            //remove legend

            if (lChart != null) {
                lChart.animateX(1000, Easing.EaseInSine)
            }

//        lChart.legend.isEnabled = false

            //remove description label
            //lChart.description.isEnabled = false
            lChart.description.text = "Miles moved this month"
            lChart.description.setPosition(700f, 70f)
            lChart.description.textSize = 15f

        }

    }
}