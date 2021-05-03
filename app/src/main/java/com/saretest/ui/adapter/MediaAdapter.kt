package com.saretest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saretest.R
import com.saretest.data.model.FragmentImage

class MediaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val image = 0
        const val story = 1
        const val audio = 2
    }
    private var fragmentImages: List<FragmentImage> = emptyList()

    fun setFragmentImages(fragmentImages: List<FragmentImage>) {
        this.fragmentImages = fragmentImages
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (fragmentImages[position].type) {
            "image" -> image
            "story" -> story
            "audio" -> audio
            else -> image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            story -> {
                StoryViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                    R.layout.stroy_item, parent,
                    false
                ))
            }
            audio -> {
                AudioViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.audio_item, parent,
                        false
                    ))
            }

            else -> {
                ImageViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.image_item, parent,
                        false
                    ))
                }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            story -> { (holder as StoryViewHolder).bind(fragmentImages[position]) }
            audio -> { (holder as AudioViewHolder).bind(fragmentImages[position]) }
            else -> { (holder as ImageViewHolder).bind(fragmentImages[position]) }
        }
    }

    override fun getItemCount(): Int {
       return fragmentImages.size
    }


    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FragmentImage) {
            itemView.findViewById<TextView>(R.id.txtStoryName).text = item.name
            itemView.findViewById<TextView>(R.id.txtStoryDesc).text = item.description
            val imageView = itemView.findViewById<AppCompatImageView>(R.id.storyImg)
            Glide.with(itemView.context)
                .load(item.image)
                .into(imageView)
        }
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FragmentImage) {
            val imageView = itemView.findViewById<AppCompatImageView>(R.id.storyImg)
            Glide.with(itemView.context)
                .load(item.image)
                .into(imageView)
        }
    }

    class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FragmentImage) {
            itemView.findViewById<TextView>(R.id.txtStoryName).text = item.name
            val imageView = itemView.findViewById<AppCompatImageView>(R.id.storyImg)
            Glide.with(itemView.context)
                .load(item.image)
                .into(imageView)
        }
    }
}