package com.fionicholas.adsbookcatalogue

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private var bookList = ArrayList<Book>()
    private var baseUrl : String = ""
    private var query : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        query = "book"

        tilSearchBooks.setEndIconOnClickListener {
            query = edtSearchBook.text.toString().trim()
            getBook(query)
        }

        setupRecyclerView()

        getBook(query)

    }

    private fun getBook(query : String) {
        bookList.clear()

        baseUrl = getString(R.string.BASE_URL) + query
        AndroidNetworking
            .get(baseUrl)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{

                override fun onResponse(response: JSONObject) {

                    if (response.optInt("totalItems") == 0) {
                        Toast.makeText(this@MainActivity, "Book Not Found!", Toast.LENGTH_LONG)
                            .show()

                        setLayoutDataNotFound()

                    } else {

                        setLayoutDataFound()

                        val jsonArray = response.getJSONArray("items")
                        for (i in 0 until jsonArray.length()) {

                            val jsonObject = jsonArray.getJSONObject(i)

                            val jsonObjectVolumeInfo = jsonObject.getJSONObject("volumeInfo")

                            val idBook = jsonObject.optString("id")
                            val titleBook = jsonObjectVolumeInfo.optString("title")
                            var ratingBook = jsonObjectVolumeInfo.optDouble("averageRating")
                            val publisherBook = jsonObjectVolumeInfo.optString("publisher")
                            val descBook = jsonObjectVolumeInfo.optString("description")

                            val doubleNan = Double.NaN
                            if (ratingBook.equals(doubleNan)) {
                                ratingBook = 0.0
                            }

                            bookList.add(
                                Book(
                                    id = idBook,
                                    title = titleBook,
                                    rating = ratingBook,
                                    publisher = publisherBook,
                                    description = descBook
                                )
                            )
                            Log.d("TAG", bookList.toString())
                        }
                    }

                    rvBooks.adapter = BookAdapter(bookList)

                }

                override fun onError(anError: ANError?) {
                    Log.d("TAG_ERROR", anError?.localizedMessage.toString())
                }

            })
    }

    private fun setupRecyclerView() {
        rvBooks.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvBooks.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    private fun setLayoutDataFound() {
        pbBook.visibility = View.GONE
        rvBooks.visibility = View.VISIBLE
        tvNotFound.visibility = View.INVISIBLE
        imgNotFound.visibility = View.INVISIBLE
    }

    private fun setLayoutDataNotFound() {
        pbBook.visibility = View.GONE
        rvBooks.visibility = View.INVISIBLE
        tvNotFound.visibility = View.VISIBLE
        imgNotFound.visibility = View.VISIBLE
    }
}
