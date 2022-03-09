package com.kinisi.trailtracker.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.kinisi.trailtracker.R
import kotlinx.android.synthetic.main.activity_stats.*
import android.R.attr.data
import android.R.attr.data
import android.R.attr.data
import android.R.attr.data
import android.widget.Button
import android.widget.TextView
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.listener.OnChartValueSelectedListener


class Stats: AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        setBarChart()
    }

    private fun setBarChart() {

        title = "Bar Chart"
        val bChart = findViewById<BarChart>(R.id.progressBarChart)
        println(bChart)


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
        entries.add(BarEntry(1f, 44f))
        entries.add(BarEntry(2f, 56f))
        entries.add(BarEntry(3f, 62f))
        entries.add(BarEntry(4f, 66f))
        entries.add(BarEntry(5f, 68f))
        entries.add(BarEntry(6f, 56f))
        entries.add(BarEntry(7f, 64f))
        entries.add(BarEntry(8f, 70f))
        entries.add(BarEntry(9f, 74f))
        entries.add(BarEntry(10f, 72f))
        entries.add(BarEntry(11f, 76f))
        entries.add(BarEntry(12f, 82f))


        //bar data set
        val set = BarDataSet(entries, "BarDataSet")
        set.setColor(resources.getColor(R.color.purple_200))
        val data = BarData(set)
        data.barWidth = 0.9f // set custom bar width

        bChart.data = data
        bChart.setFitBars(true) // make the x-axis fit exactly all bars

        bChart.invalidate() // refresh
        bChart.setGridBackgroundColor(resources.getColor(R.color.purple_200))

        bChart.animateY(5000)
        //        hide grid lines
        bChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = bChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        bChart.axisRight.isEnabled = false

        //remove legend
//        bChart.legend.isEnabled = false

        //remove description label
        //bChart.description.isEnabled = false
        bChart.description.text = "Miles moved this year"
        bChart.description.setPosition(700f,70f)
        bChart.description.setTextSize(20f)
        // bChart.description.setPosition()

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //xAxis.valueFormatter = ""
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }
}





