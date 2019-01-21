@file:Suppress("UNREACHABLE_CODE")

package asiantech.internship.summer.kotlin.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.model.TimelineItem
import kotlinx.android.synthetic.`at-hungtran`.list_item.view.*

class TimelineAdapter(private val timelineItems: List<TimelineItem>) : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {
    private val onItemClick: OnItemListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return TimelineViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return timelineItems.size
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.onBind(timelineItems[position])
    }

    inner class TimelineViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun onBind(timelineItem: TimelineItem) {
            itemView.icAvt.setImageResource(timelineItem.imageAvatar)
            itemView.tvName.text = timelineItem.name
            itemView.imgImage.setImageResource(timelineItem.image)
            itemView.tvDescription.text = timelineItem.description
            itemView.tvCommenterName.text = timelineItem.commenterName
            itemView.imgBtnLike.setOnClickListener {
                onItemClick?.onClickLike(adapterPosition)
                if (timelineItem.like == 1) {
                    itemView.context.getString(R.string.like, timelineItem.like)
                } else {
                    itemView.context.getString(R.string.likes, timelineItem.like)
                }
            }
        }
    }

    interface OnItemListener {
        fun onClickLike(position: Int)
    }
}
