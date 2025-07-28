package com.example.courses.ui.favorites

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
import com.example.courses.databinding.FragmentFavoritesBinding
import com.example.courses.ui.favorites.adapter.FavoriteCourseItemAdapter
import com.example.courses.ui.favorites.adapter.FavoriteCourseItemWithImage
import com.example.courses.utils.collectWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoriteCourseItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setAdapter()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        viewModel.uiState.collectWhenStarted(viewLifecycleOwner) {
            when (it) {
                is FavoritesUiState.Initial -> {}
                is FavoritesUiState.CoursesSuccess -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.setData(viewModel.favoriteCourses.value.map { course ->
                        FavoriteCourseItemWithImage(course)
                    })

                    CoursesLogger.d("Courses loaded successfully: ${viewModel.favoriteCourses.value.size} courses")
                }

                is FavoritesUiState.CoursesError -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this.context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is FavoritesUiState.CoursesLoading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
        viewModel.favoriteCourses.collectWhenStarted(viewLifecycleOwner) {favoriteCourses ->
            CoursesLogger.d("Courses updated: ${favoriteCourses.size} courses")
            adapter.setData(favoriteCourses.map { course ->
                FavoriteCourseItemWithImage(course)
            })

            if(favoriteCourses.isEmpty()) {
                binding.tvEmptyFavorites.visibility = View.VISIBLE
            } else {
                binding.tvEmptyFavorites.visibility = View.GONE
            }
        }
    }

    private fun setAdapter() {
        adapter = FavoriteCourseItemAdapter(
            onClick = { course ->
                findNavController().navigate(
                    R.id.action_favoritesFragment_to_courseDetailsFragment,
                    bundleOf("courseId" to "id")
                )
            },
            onFavoriteClick = { course ->
                CoursesLogger.d("FavoritesFragment - onFavoriteClick: ${course.title}")
                viewModel.onFavoriteClick(course)
            },
        )

        binding.rvCourses.layoutManager = LinearLayoutManager(this.context)
        binding.rvCourses.adapter = adapter
    }
}