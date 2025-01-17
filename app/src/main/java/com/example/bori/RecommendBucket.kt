package com.example.bori

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.text.SimpleDateFormat
import java.util.*

class RecommendBucket : Fragment(){
    private lateinit var spinner : Spinner
    private lateinit var springButton : android.widget.Button
    private lateinit var summerButton : android.widget.Button
    private lateinit var fallButton : android.widget.Button
    private lateinit var winterButton : android.widget.Button
    private lateinit var addButton:android.widget.Button
    var seasonPositon: Int=0



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_recommend_bucket, container, false)



        spinner = view.findViewById(R.id.myBucketRecommend_spinner)

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.spinner_array_string,
                R.layout.spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.spinner_items)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }
        spinner.setSelection(1)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val mainActivity = activity as Main
                when (position) {
                    0 -> {
                        mainActivity.changeMyBucketFragment(0)
                    }
                    1 -> {

                    }
                    else -> {
                    }
                }
            }
        }

        addButton= view.findViewById(R.id.myBucketRecommend_addButton)
            addButton.setOnClickListener {
                val dialogView = layoutInflater.inflate(R.layout.activity_add_bucket_modal, null)

                val addBucketDialog = Dialog(view.context)
                addBucketDialog.setContentView(dialogView)

                addBucketDialog.setCancelable(true)
                addBucketDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                addBucketDialog.window!!.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                addBucketDialog.show()


                val addButton = dialogView.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.addBucket_addButton)
                addButton.setOnClickListener {
                    val newBucketText = dialogView.findViewById<EditText>(R.id.addBucket_newEditText)
                    if(newBucketText.text.isNotEmpty()){
                        when (seasonPositon) {
                            0 -> {
                                saveStore("recommend_spring", newBucketText.text.toString())
                            }
                            1 -> {
                                saveStore("recommend_summer", newBucketText.text.toString())
                            }
                            2 -> {
                                saveStore("recommend_fall", newBucketText.text.toString())
                            }
                            3 -> {
                                saveStore("recommend_winter", newBucketText.text.toString())
                            }
                        }
                    }else{
                        Toast.makeText(getActivity(), "버캣을 작성해주세요.",
                            Toast.LENGTH_SHORT).show()
                    }
                    addBucketDialog.dismiss()
                }

                val xButton = dialogView.findViewById<ImageButton>(R.id.addBucket_xButton)
                xButton.setOnClickListener{
                    addBucketDialog.dismiss()
                }
            }

        var myBucketSpring = MyBucketSpring()
        var myBucketSummer = MyBucketSummer()
        var myBucketFall = MyBucketFall()
        var myBucketWinter = MyBucketWinter()
        springButton = view.findViewById(R.id.myBucketRecommend_springButton)
        summerButton = view.findViewById(R.id.myBucketRecommend_summerButton)
        fallButton = view.findViewById(R.id.myBucketRecommend_fallButton)
        winterButton = view.findViewById(R.id.myBucketRecommend_winterButton)

        val date = Date(System.currentTimeMillis())
        val sdf = SimpleDateFormat("MM")
        when (sdf.format(date)) {
            in arrayListOf("03", "04", "05") -> { // 3월 ~ 5월 (봄)
                spring()
                springButton.isSelected = true
            }
            in arrayListOf("06", "07", "08") -> { // 6월 ~ 8월 (여름)
                summer()
                summerButton.isSelected = true
            }
            in arrayListOf("09", "10", "11") -> { // 9월 ~ 11월 (가을)
                fall()
                fallButton.isSelected = true
            }
            in arrayListOf("12", "01", "02") -> { // 12월 ~ 2월 (겨울)
                winter()
                winterButton.isSelected = true
            }
        }

        springButton.setOnClickListener {
            seasonPositon=0
            spring()
            topNavHandler(springButton)
        }
        summerButton.setOnClickListener {
            seasonPositon=1
            summer()
            topNavHandler(summerButton)
        }
        fallButton.setOnClickListener {
            seasonPositon=2
            fall()
            topNavHandler(fallButton)
        }
        winterButton.setOnClickListener {
            seasonPositon=3
            winter()
            topNavHandler(winterButton)
        }
        return view
    }

    private fun saveStore(season:String, text:String){
        val data = mapOf(
            "title" to text,
            "date" to com.google.firebase.Timestamp.now()
        )

        MyApplication.db.collection(season).whereEqualTo("title", text)
        .get()
        .addOnSuccessListener{
            if(it.size() != 0)
                Toast.makeText(getActivity(), "이미 존재하는 버캣입니다.",
                    Toast.LENGTH_SHORT).show()
            else {
                MyApplication.db.collection(season).add(data)

                when (seasonPositon) {
                    0 -> {
                        spring()
                    }
                    1 -> {
                        summer()
                    }
                    2 -> {
                        fall()
                    }
                    3 -> {
                        winter()
                    }
                }

            }
        }
        .addOnFailureListener { exception ->
            Log.d("firebase", "(recommendBucket)Error getting documents: ", exception)
        }

    }

    private fun spring(){
        childFragmentManager.beginTransaction()
            .replace(R.id.myBucketRecommend_frameLayout,RecommendBucketSpring(), "spring" )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    private fun summer(){
        childFragmentManager.beginTransaction()
            .replace(R.id.myBucketRecommend_frameLayout, RecommendBucketSummer())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
    private fun fall(){
        childFragmentManager.beginTransaction()
            .replace(R.id.myBucketRecommend_frameLayout, RecommendBucketFall())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
    private fun winter(){
        childFragmentManager.beginTransaction()
            .replace(R.id.myBucketRecommend_frameLayout, RecommendBucketWinter())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
    private fun topNavHandler(button: Button) {
        val btn =
            listOf<android.widget.Button>(springButton, summerButton, fallButton, winterButton)
        for (i in btn) {
            if (button == i) {
                i.isSelected = true
            } else {
                i.isSelected = false
            }
        }
    }
}