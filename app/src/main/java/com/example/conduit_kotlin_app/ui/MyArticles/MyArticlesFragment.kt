package com.example.conduit_kotlin_app.ui.MyArticles

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.conduit_kotlin_app.R
import com.example.conduit_kotlin_app.adapter.ArticleAdapter
import com.example.conduit_kotlin_app.data.Article
import com.example.conduit_kotlin_app.databinding.FragmentMyArticlesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyArticlesFragment : Fragment(R.layout.fragment_my_articles) , ArticleAdapter.onItemClickListner {
    private val viewModel by viewModels<MyArticlesViewModel>()
    private var _binding: FragmentMyArticlesBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMyArticlesBinding.bind(view)
        //recycler view adapter
        val articleAdapter = ArticleAdapter(this)

        //set observer for recycler view
        viewModel.listArticleMutableList.observe(viewLifecycleOwner) {
            articleAdapter.differ.submitList(it)
        }

        //recycler view setup
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = articleAdapter
        }

        //set visibility
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnEdit.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnEdit.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.VISIBLE

            }

        }


        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_myArticlesFragment_to_postArticleFragment)
        }


    }

    override fun onItemClick(article: Article) {
        val action = MyArticlesFragmentDirections.actionMyArticlesFragmentToDetailArticleFragment(article)
        Log.d("articles", "$article")
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMyArticles()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}