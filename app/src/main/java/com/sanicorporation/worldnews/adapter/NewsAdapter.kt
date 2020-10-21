package com.sanicorporation.worldnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanicorporation.worldnews.R
import com.sanicorporation.worldnews.entity.Article
import kotlinx.android.synthetic.main.new_preview_item.view.*

class NewsAdapter(private val myDataset: List<Article>?) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    private  var dataset: List<Article>? = myDataset
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_preview_item, parent, false) as View
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = dataset?.get(position)
        holder.itemView.new_title.text = article?.title ?: ""
        holder.itemView.new_description.text = article?.description ?: ""
        article?.urlToImage?.apply {
            Glide.with(holder.itemView.context).load(this).into(holder.itemView.new_image)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() =  dataset?.size ?: 0

    fun setData(articles: List<Article>){
        dataset = articles
        notifyDataSetChanged()
    }
}