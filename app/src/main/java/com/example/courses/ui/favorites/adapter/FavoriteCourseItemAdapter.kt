package com.example.courses.ui.favorites.adapter

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.core.util.toReadableDate
import com.example.courses.R
import com.example.courses.databinding.ItemCourseBinding
import com.example.domain.model.CourseData
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class FavoriteCourseItemAdapter(
    onClick: (CourseData) -> Unit,
    onFavoriteClick: (CourseData) -> Unit,
) : com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter<List<FavoriteCourseItemModel>>() {

    init {
        delegatesManager.addDelegate(courseAdapterDelegate(onClick, onFavoriteClick))
//        delegatesManager.addDelegate(otherAdapterDelegatesHere)
    }

    fun setData(data: List<FavoriteCourseItemModel>) {
        items = data
        // TODO : change to DiffUtil
        notifyDataSetChanged()
    }
}


fun courseAdapterDelegate(onClick: (CourseData) -> Unit, onFavoriteClick: (CourseData) -> Unit) =
    adapterDelegateViewBinding<FavoriteCourseItemWithImage, FavoriteCourseItemModel, ItemCourseBinding>(
        { inflater, parent -> ItemCourseBinding.inflate(inflater, parent, false) }) {
        binding.btnShowCourse.setOnClickListener {
            onClick(item.data)
        }
        binding.btnFavorite.setOnClickListener {
            onFavoriteClick(item.data)
        }

        bind {
            val price = item.data.price + "₽"

            val assetPath = "file:///android_asset/${item.data.imageAsset}"
            Glide.with(binding.root.context)
                .load(assetPath)
                .into(binding.ivCourseImage)

            binding.tvCourseTitle.text = item.data.title
            binding.tvCourseDescription.text = item.data.text
            binding.tvCoursePrice.text = price
            binding.tvRate.text = item.data.rate
            binding.tvDate.text = item.data.startDate.toReadableDate()
            binding.btnFavorite.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.green
                )
            )
        }
    }
