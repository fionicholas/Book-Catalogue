package com.fionicholas.adsbookcatalogue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_book.view.*

class BookAdapter(private val books: MutableList<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val mContext = holder.itemView.context
        val rating =  books[position].rating
        val id =  books[position].id
        holder.textTitle.text = books[position].title
        holder.textRating.text = "($rating)"
        holder.ratingBar.rating = rating.toFloat()

        Glide
            .with(mContext)
            .load(mContext.getString(R.string.BASE_URL_THUMBNAIL) + id + "?fife=w800-h800")
            .into(holder.imgThumbnail)

        holder.itemView.setOnClickListener {
            DetailBookActivity.start(context = mContext, book = books[position])
        }
    }

    inner class BookViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val textTitle : TextView = itemView.tvTitle
        val imgThumbnail : ImageView = itemView.imgBook
        val ratingBar : RatingBar = itemView.ratingBar
        val textRating : TextView = itemView.tvDetailRating

    }
}