package com.fionicholas.adsbookcatalogue

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_book.*
import org.jetbrains.anko.startActivity

class DetailBookActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context, book: Book) {
            context.startActivity<DetailBookActivity>(
                "BOOK" to book
            )
        }
    }

    private var book: Book? = null
    private var titleBook: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_book)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        setupToolbar(toolbar)


        book = intent.getParcelableExtra("BOOK") as Book?
        book?.let { showData(it) }


    }

    private fun showData(data: Book) {
        this.book = data

        this.book?.let {
            titleBook = it.title
            tv_title_detail.text = it.title
            tvDetailRating.text = "(${it.rating})"
            tv_publisher_detail.text = it.publisher
            ratingBar.rating = it.rating.toFloat()
            tv_description_detail.text = it.description

            val idImage = it.id

            Glide.with(this)
                .load("https://books.google.com/books/content/images/frontcover/$idImage?fife=w800-h800")
                .into(img_poster_detail)

        }
    }

    private fun setupToolbar(toolbar: androidx.appcompat.widget.Toolbar) {

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = titleBook
        toolbar_layout.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.colorWhite)
        )
        toolbar_layout.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.transparent)
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
