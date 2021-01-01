package com.example.newsretro

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity(){
    private lateinit var madapter:NewsListAdapter
    var numpages = 1
    var totalresults = -1
    lateinit var pb:ProgressBar

    val cts = arrayOf("general","entertainment","health","business","science","sports","technology")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prog()
        pb.visibility = View.VISIBLE

        val spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(this,R.array.categories,R.layout.color_spinner_layout)

        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.adapter = adapter
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        getnews("general")
        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                pb.visibility = View.VISIBLE
                val ct = cts[position].toLowerCase()
                getnews(ct)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                getnews("general")
            }
        }



    }

    private fun prog(){

        pb = findViewById(R.id.progressBar)

    }

    private fun getnews(Category:String) {
        val category1 = Category
        val news = NewsService.newsinstance.getHeadlines("in",category1, numpages)
        news.enqueue(object : Callback<News2>, newsitemclicked {
            override fun onResponse(call: Call<News2>, response: Response<News2>) {
                val news = response.body()
                if (news != null) {
                    pb.visibility = View.GONE
                    val recycler_view: RecyclerView = findViewById(R.id.recycler_view)
                    madapter = NewsListAdapter(this@MainActivity, news.articles, this)
                    recycler_view.adapter = madapter
                    recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
                    totalresults = news.totalResults
                }
            }

            override fun onFailure(call: Call<News2>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onitemselected(item: News) {
                val url = item.url
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                val colorInt: Int = Color.parseColor("#FF0000") //red


                customTabsIntent.launchUrl(this@MainActivity, Uri.parse(url))
            }
        })
    }


}