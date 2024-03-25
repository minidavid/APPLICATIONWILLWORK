package com.example.applicationwillwork

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val headingArticles : TextView = findViewById(R.id.txtHeadingArticle)
        val bodyArticles : TextView= findViewById(R.id.txtBodyArticle)
        val imageArticles : ImageView = findViewById(R.id.imgHeadingArticle)
    
        val bundle: Bundle? = intent.extras
        val heading = bundle!!.getString("heading")
        val imageId = bundle.getInt("articles")
        val txtBodyContent = bundle.getString("articlesBody")
    
        headingArticles.text = heading
        bodyArticles.text = txtBodyContent
        imageArticles.setImageResource(imageId)
        

    }
}