package com.example.bori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

class RecommendBucketWinter : Fragment(){
    private lateinit var rv: androidx.recyclerview.widget.RecyclerView;
    val bucketList = arrayListOf(
        BucketListForm("날씨 좋은 날 잔디밭에서 피크닉 즐기기 겨울", "0명이 도전 중!"),
        BucketListForm("날씨 좋은 날 잔디밭에서 피크닉 즐기기1", "1명이 도전 중!"),
        BucketListForm("날씨 좋은 날 잔디밭에서 피크닉 즐기기2", "2명이 도전 중!"),
        BucketListForm("날씨 좋은 날 잔디밭에서 피크닉 즐기기3", "3명이 도전 중!"),
        BucketListForm("날씨 좋은 날 잔디밭에서 피크닉 즐기기4", "4명이 도전 중!"),
        BucketListForm("날씨 좋은 날 잔디밭에서 피크닉 즐기기5", "5명이 도전 중!"),
        BucketListForm("날씨 좋은 날 잔디밭에서 피크닉 즐기기6", "6명이 도전 중!"),
        BucketListForm("날씨 좋은 날 잔디밭에서 피크닉 즐기기7", "7명이 도전 중!")

    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recommend_bucket_winter, container, false)

        rv = view.findViewById(R.id.rv_recommendBucketWinter)
        rv.layoutManager = GridLayoutManager(context,2)
        rv.setHasFixedSize(true)
        rv.adapter = RecommendBucketWinterAdapter(bucketList)

        return view
    }
    fun clicked(text:String){
        bucketList.add(BucketListForm(text,"0명이 도전 중!"))
        rv.adapter?.notifyDataSetChanged()
    }

}