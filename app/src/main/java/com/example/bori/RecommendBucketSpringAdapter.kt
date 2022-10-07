package com.example.bori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecommendBucketSpringAdapter (val bucketList: ArrayList<BucketListForm>):
    RecyclerView.Adapter<RecommendBucketSpringAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): RecommendBucketSpringAdapter.CustomViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_recommend_bucket_component,parent, false)
        return CustomViewHolder(view)
    }
    override fun getItemCount(): Int{
        return bucketList.size
    }
    override fun onBindViewHolder(holder: RecommendBucketSpringAdapter.CustomViewHolder, position: Int){
        holder.title.text = bucketList.get(position).title.toString()
        holder.challenger.text = bucketList.get(position).challenger.toString()

    }

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.recommend_bucket_component_titleTextView)
        val challenger = itemView.findViewById<TextView>(R.id.recommend_bucket_component_challengeTextView)
        val heart = itemView.findViewById<ImageView>(R.id.recommend_bucket_component_heartImage)
    }

}