package com.example.conduit_kotlin_app.ui.postArticle

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.conduit_kotlin_app.R
import com.example.conduit_kotlin_app.data.Article
import com.example.conduit_kotlin_app.databinding.FragmentPostArticleBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostArticleFragment : Fragment(R.layout.fragment_post_article) {
    private val viewModel by viewModels<PostArticleViewModel>()
    private var _binding: FragmentPostArticleBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostArticleBinding.bind(view)

        binding.btnPost.setOnClickListener {
            viewModel.postArticle(
                Article(
                    title = binding.tfTitle.text.toString(),
                    description = binding.tfDescription.text.toString(),
                    body = binding.tfBody.text.toString(),
                    tagList = binding.tfTitle.text.toString().trim().splitToSequence(',')
                        .filter { it.isNotEmpty() }.toList()
                )
            )
            binding.apply {
                tfTitle.setText("")
                tfDescription.setText("")
                tfBody.setText("")
                tfTags.setText("")

            }
        }

        //set visibility
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.apply {
                    progressBar.visibility = View.VISIBLE
                    binding.tfTags.visibility = View.GONE
                    binding.tfDescription.visibility = View.GONE
                    binding.tfBody.visibility = View.GONE
                    binding.tfTitle.visibility = View.GONE
                }

            } else {
                binding.apply {
                    progressBar.visibility = View.GONE
                    binding.tfTags.visibility = View.VISIBLE
                    binding.tfDescription.visibility = View.VISIBLE
                    binding.tfBody.visibility = View.VISIBLE
                    binding.tfTitle.visibility = View.VISIBLE
                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}