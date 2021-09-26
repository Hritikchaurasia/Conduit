package com.example.conduit_kotlin_app.ui.DetailArticle

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.conduit_kotlin_app.BaseApplication

import com.example.conduit_kotlin_app.R
import com.example.conduit_kotlin_app.databinding.FragmentDetailArticleBinding
import com.example.conduit_kotlin_app.di.ApplicationModule
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail_article.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailArticleFragment : Fragment(R.layout.fragment_detail_article) {
    private val viewModel by viewModels<DetailArticleViewModel>()
    private val args by navArgs<DetailArticleFragmentArgs>()
    private var _binding: FragmentDetailArticleBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var app: BaseApplication
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailArticleBinding.bind(view)


        // applying values
        binding.apply {
            tvAuthorName.text = args.Article.author?.username ?: " "
            tvTitle.text = args.Article.title
            tvDescription.text = args.Article.description
            tvBody.text = args.Article.body
            Glide.with(view)
                .load(args.Article.author?.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(ivAuthorImage)
        }

        // changing view on the bases of loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.apply {
                    progressBar.visibility = View.VISIBLE
                    ivAuthorImage.visibility = View.GONE
                    tvAuthorName.visibility = View.GONE
                    tvTitle.visibility = View.GONE
                    tvDescription.visibility = View.GONE
                    tvBody.visibility = View.GONE
                    btnFollow.visibility = View.GONE
                    btnDelete.visibility = View.GONE
                }


            } else {
                binding.apply {
                    progressBar.visibility = View.GONE
                    ivAuthorImage.visibility = View.VISIBLE
                    tvAuthorName.visibility = View.VISIBLE
                    tvTitle.visibility = View.VISIBLE
                    tvDescription.visibility = View.VISIBLE
                    tvBody.visibility = View.VISIBLE

                }
                // checking if my article then show delete button else show follow button
                if (viewModel.username == args.Article.author?.username) {
                    binding.btnDelete.visibility = View.VISIBLE
                    binding.btnFollow.visibility = View.GONE
                } else {
                    binding.btnDelete.visibility = View.GONE
                    binding.btnFollow.visibility = View.VISIBLE
                }
            }

        }

        //follow other-user
        binding.btnFollow.setOnClickListener {
            //if already follow don't follow
            if (!args.Article.author.following) {
                viewModel.followOtherUser(args.Article.author?.username ?: " ")
                //change follow button appearance
                binding.apply {
                    btnFollow.setTextColor(
                        app.getResources().getColor(R.color.black, Resources.getSystem().newTheme())
                    )
                    btnFollow.setBackgroundColor(
                        app.getResources()
                            .getColor(R.color.offwhite, Resources.getSystem().newTheme())
                    )
                    btnFollow.setText("Followed")
                    btnFollow.setIconResource(R.drawable.ic_check)
                }
            }
        }

        // delete article
        binding.btnDelete.setOnClickListener {
            viewModel.deleteArticle(args.Article.slug ?: "")
        }

        //check if article is deleted and pop back
        viewModel.isdeleteArticle.observe(viewLifecycleOwner, Observer { delete ->
            if (delete) {
                findNavController().popBackStack()
            }
        })

        //add article to favorite
        binding.btnFavorite.setOnClickListener {
          if(args.Article.favorited ){
//              binding.btnFavorite.setBackgroundColor( app.getResources().getColor(R.color.pink, Resources.getSystem().newTheme()))
//            //  isLiked = !isLiked
          }else{
             viewModel.addArticleToFavorite(args.Article.slug ?: "")
             // binding.btnFavorite.setBackgroundColor( app.getResources().getColor(R.color.pink, Resources.getSystem().newTheme()))

          }
        }
    }
//    fun changeFavoriteButton(like: Boolean){
//        if(like){
//            binding.btnFavorite.setBackgroundColor( app.getResources().getColor(R.color.pink, Resources.getSystem().newTheme()))
//
//        }else{
//            binding.btnFavorite.setBackgroundColor( app.getResources().getColor(R.color.black, Resources.getSystem().newTheme()))
//            //icon color
//            //binding.btnFavorite.setColorFilter(R.color.white)
//        }
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}