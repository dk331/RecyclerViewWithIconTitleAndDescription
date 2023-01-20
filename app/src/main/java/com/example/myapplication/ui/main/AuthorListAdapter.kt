package com.example.myapplication.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Author

class AuthorListAdapter(private var mList: ArrayList<Author>) :
    RecyclerView.Adapter<AuthorListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the list_item view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val author = mList[position]

        // sets the image to the imageview from our itemHolder class
        Glide.with(holder.itemView.context).load(author.downloadUrl).into(holder.img)

        // sets the text to the textview from our itemHolder class
        holder.txtTitle.text = "Author name: ${author.author}"
        holder.txtDescription.text = "Description: ${author.url}"

        holder.itemView.setOnClickListener(View.OnClickListener {
            showAlert(holder.itemView.context, author)
        })

    }

    fun showAlert(context: Context, author: Author) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Author")
        val description =
            "Author name: ${author.author}\nURL: ${author.url}\nDownload URL: ${author.downloadUrl}"
        builder.setMessage(description)
        builder.setNeutralButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


    fun updateUserList(authorList: ArrayList<Author>) {
        mList = ArrayList<Author>()
        mList = authorList
        notifyDataSetChanged()
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val img: ImageView = itemView.findViewById(R.id.img)
        val txtTitle: TextView = itemView.findViewById(R.id.txt_title)
        val txtDescription: TextView = itemView.findViewById(R.id.txt_description)
    }
}