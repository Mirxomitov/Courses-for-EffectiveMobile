package com.example.courses.ui.home.adapter

import com.bumptech.glide.Glide
import com.example.courses.databinding.ItemCourseBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class CourseItemAdapter(
    onClick: (CourseItemModel) -> Unit
) : com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter<List<CourseItemModel>>() {

    init {
        delegatesManager.addDelegate(courseAdapterDelegate(onClick))
//        delegatesManager.addDelegate(otherAdapterDelegatesHere)
    }

    fun setData(data: List<CourseItemModel>) {
        items = data
        notifyDataSetChanged()
    }
}


fun courseAdapterDelegate(onClick: (CourseItemWithImage) -> Unit) =
    adapterDelegateViewBinding<CourseItemWithImage, CourseItemModel, ItemCourseBinding>(
        { inflater, parent -> ItemCourseBinding.inflate(inflater, parent, false) }) {
        binding.btnShowCourse.setOnClickListener {
            onClick(item)
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
        }
    }
