package com.example.weatherapp_juanarizaonecha.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_juanarizaonecha.R
import com.example.weatherapp_juanarizaonecha.chat.Comment

class CommentAdapter(val commentsList: MutableList<Comment>, private val onDeleteClickListener: (Comment) -> Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_OUTGOING = 1
    private val VIEW_TYPE_INCOMING = 2

    class OutgoingCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCommentUsername: TextView = itemView.findViewById(R.id.tvCommentUsername)
        val tvCommentTimestamp: TextView = itemView.findViewById(R.id.tvCommentTimestamp)
        val tvCommentContent: TextView = itemView.findViewById(R.id.tvCommentContent)
        val btnBin: ImageButton = itemView.findViewById(R.id.btnBin)
    }

    class IncomingCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCommentUsername: TextView = itemView.findViewById(R.id.tvCommentUsername)
        val tvCommentTimestamp: TextView = itemView.findViewById(R.id.tvCommentTimestamp)
        val tvCommentContent: TextView = itemView.findViewById(R.id.tvCommentContent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (commentsList[position].isCurrentUser) {
            VIEW_TYPE_OUTGOING
        } else {
            VIEW_TYPE_INCOMING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_OUTGOING) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_outgoing, parent, false)
            OutgoingCommentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_incoming, parent, false)
            IncomingCommentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentComment = commentsList[position]
        if (holder is OutgoingCommentViewHolder) {
            holder.tvCommentUsername.text = currentComment.username
            holder.tvCommentTimestamp.text = currentComment.hour.substring(0, currentComment.hour.length - 3) //Pick up only the date and the hh:mm
            holder.tvCommentContent.text = currentComment.message
            holder.btnBin.setOnClickListener {
                onDeleteClickListener(currentComment)
            }
        } else if (holder is IncomingCommentViewHolder) {
            holder.tvCommentUsername.text = currentComment.username
            holder.tvCommentTimestamp.text = currentComment.hour.substring(0, currentComment.hour.length - 3)
            holder.tvCommentContent.text = currentComment.message
        }
    }

    override fun getItemCount() = commentsList.size

    fun insertNewComment(comment: Comment) {
        commentsList.add(comment)
        notifyItemInserted(commentsList.size - 1)
    }

    fun removeComment(comment: Comment) {
        val index = commentsList.indexOfFirst { it.id == comment.id }
        if (index != -1) {
            commentsList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

}