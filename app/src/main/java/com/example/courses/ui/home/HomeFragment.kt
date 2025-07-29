package com.example.courses.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.util.CoursesLogger
import com.example.courses.R
import com.example.courses.databinding.FragmentHomeBinding
import com.example.courses.ui.home.adapter.CourseItemAdapter
import com.example.courses.ui.home.adapter.CourseItemWithImage
import com.example.courses.utils.collectWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: CourseItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setAdapter()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        binding.tvFilter.setOnClickListener {
            viewModel.filterByDate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        viewModel.uiState.collectWhenStarted(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Initial -> {}
                is HomeUiState.CoursesSuccess -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.setData(viewModel.courses.value.map { course ->
                        CourseItemWithImage(course)
                    })

                    CoursesLogger.d("Courses loaded successfully: ${viewModel.courses.value.size} courses")
                }

                is HomeUiState.CoursesError -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this.context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is HomeUiState.CoursesLoading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
        viewModel.courses.collectWhenStarted(viewLifecycleOwner) { courses ->
            CoursesLogger.d("Courses updated: ${courses.size} courses")
            adapter.setData(courses.map { course ->
                CourseItemWithImage(course)
            })
        }
    }

    private fun setAdapter() {
        adapter = CourseItemAdapter(
            onClick = { course ->
                findNavController().navigate(
                    R.id.action_homeFragment_to_courseDetailsFragment,
                    bundleOf("courseId" to "id")
                )
            },
            onFavoriteClick = { course ->
                CoursesLogger.d("HomeFragment - onFavoriteClick: ${course.title}")
                viewModel.onFavoriteClick(course)
            },
        )

        binding.rvCourses.layoutManager = LinearLayoutManager(this.context)
        binding.rvCourses.adapter = adapter
    }
}