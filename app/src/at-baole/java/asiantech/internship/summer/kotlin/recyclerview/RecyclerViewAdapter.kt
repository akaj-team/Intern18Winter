package asiantech.internship.summer.kotlin.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.recyclerview.model.TimelineItem

import kotlinx.android.synthetic.`at-baole`.kotlin_list_item.view.*

class RecyclerViewAdapter(private val listUsers: List<TimelineItem>, private val onItemListener: OnItemListener) : RecyclerView.Adapter<RecyclerViewAdapter.TimelineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.kotlin_list_item, parent, false)
        return TimelineViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.bindView(listUsers[position])
    }

    interface OnItemListener {
        fun onClickLike(position: Int)
    }

    inner class TimelineViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val likeSingular : String = "like"
        val likesPlural : String = "likes"

        fun bindView(timelineItem: TimelineItem) {

            itemView.imgAvatar.setImageResource(timelineItem.avatar)
            itemView.tvUsername.text = timelineItem.username
            itemView.imgPicture.setImageResource(timelineItem.picture)
            itemView.tvCommenter.text = timelineItem.commenter
            itemView.tvComment.text = timelineItem.comment

            itemView.imgBtnLike.setOnClickListener {
                onItemListener.onClickLike(adapterPosition)
                if (timelineItem.countLike < 2) {
                    itemView.tvCountLike.text = "${timelineItem.countLike} $likeSingular"
                } else {
                    itemView.tvCountLike.text = "${timelineItem.countLike} $likesPlural"
                }
            }
        }
    }
}