package com.example.applicationwillwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ArticlesMain : AppCompatActivity() {
    private  lateinit var newRecyclerview : RecyclerView

    private  lateinit var newArrayList : ArrayList<Articles>

    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_main)

        imageId = arrayOf(
            R.drawable.baseline_article_24,
            R.drawable.baseline_person_24,
            R.drawable.baseline_favorite_border_24
        )

        heading = arrayOf(
            "Mental Health is important",
            "Here are 12 facts you did not know about mental health",
            "Mental Health Understood Better"
        )

        newRecyclerview = findViewById(R.id.RecyclerViewerMain)
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)

        newArrayList = arrayListOf<Articles>()
        getUserData()


    }
    private fun getUserData(){

        for(i in imageId.indices){
            val articles = Articles(imageId[i],heading[i])
            newArrayList.add(articles)

        }
        newRecyclerview.adapter = MyAdapter(newArrayList)
    }
}