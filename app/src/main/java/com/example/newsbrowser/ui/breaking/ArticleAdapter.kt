package com.example.newsbrowser.ui.breaking

import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.newsbrowser.model.ArticleAppModel
import com.squareup.picasso.Picasso

@BindingAdapter("articleThumbnail")
fun bindArticleThumbnail(imageView: ImageView, article: ArticleAppModel?){
    if (article != null){
        if (article.urlToImage.isNotEmpty()){
            Picasso.get().load(article.urlToImage).fit().centerCrop().into(imageView)
        }else{
            imageView.visibility = View.GONE
        }
    }else{
        imageView.visibility = View.GONE
    }
}