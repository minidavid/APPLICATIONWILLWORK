package com.example.applicationwillwork

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationwillwork.Adapters.MyAdapter

class ArticlesMain : AppCompatActivity() {
    private  lateinit var newRecyclerview : RecyclerView

    private  lateinit var newArrayList : ArrayList<Articles>

    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>

    lateinit var articles : Array<String>
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
            "Mental Health Problems",
            "Getting to an institution"
        )

        articles = arrayOf(
            getString(R.string.article_one),
            getString(R.string.article_two),
            getString(R.string.article_three)
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
        var adapter = MyAdapter(newArrayList)
        newRecyclerview.adapter = adapter
        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@ArticlesMain,"You clicked on: $position",Toast.LENGTH_SHORT).show()

                //intent
                val intent = Intent(this@ArticlesMain,ArticleActivity::class.java)
                intent.putExtra("heading",newArrayList[position].heading)
                intent.putExtra("imageId",newArrayList[position].heading)
                intent.putExtra("articlesBody",articles[position])

                startActivity(intent)


            }

        })
    }
}