package com.example.courses.ui.home.adapter

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.core.util.CoursesLogger
import com.example.core.util.toReadableDate
import com.example.courses.R
import com.example.courses.databinding.ItemCourseBinding
import com.example.domain.model.CourseData
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class CourseItemAdapter(
    onClick: (CourseData) -> Unit,
    onFavoriteClick: (CourseData) -> Unit
) : com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter<List<CourseItemModel>>() {

    init {
        delegatesManager.addDelegate(courseAdapterDelegate(onClick, onFavoriteClick))
//        delegatesManager.addDelegate(otherAdapterDelegatesHere)
    }

    fun setData(data: List<CourseItemModel>) {
        items = data
        // TODO : change to DiffUtil
        notifyDataSetChanged()
    }
}


fun courseAdapterDelegate(onClick: (CourseData) -> Unit, onFavoriteClick: (CourseData) -> Unit) =
    adapterDelegateViewBinding<CourseItemWithImage, CourseItemModel, ItemCourseBinding>(
        { inflater, parent -> ItemCourseBinding.inflate(inflater, parent, false) }) {
        binding.btnShowCourse.setOnClickListener {
            onClick(item.data)
        }
        binding.btnFavorite.setOnClickListener {
            CoursesLogger.d("CourseItemAdapter - onFavoriteClick: ${item.data.title}")
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
                    if (!item.data.hasLike) R.color.textWhite else R.color.green
                )
            )
        }
    }
