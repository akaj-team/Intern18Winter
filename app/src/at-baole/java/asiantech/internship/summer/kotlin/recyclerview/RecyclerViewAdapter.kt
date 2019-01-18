package asiantech.internship.summer.kotlin.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.recyclerview.model.TimelineItem1
import kotlinx.android.synthetic.`at-baole`.kotlin_list_item.view.*

class RecyclerViewAdapter(private val listUsers: List<TimelineItem1>) : RecyclerView.Adapter<RecyclerViewAdapter.TimelineViewHolder>() {

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

    class TimelineViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private val TEXT_LIKE_SINGULAR: String = "like"
        private val TEXT_LIKE_PLURAL: String = "likes"
        private val mListener: OnItemListener? = null

        fun bindView(timelineItem: TimelineItem1) {

            itemView.imgAvatar.setImageResource(timelineItem.avatar)
            itemView.tvUsername.text = timelineItem.username
            itemView.imgPicture.setImageResource(timelineItem.picture)
            itemView.tvCommenter.text = timelineItem.commenter
            itemView.tvComment.text = timelineItem.comment

            itemView.imgBtnLike.setOnClickListener { view ->
                mListener?.onClickLike(layoutPosition)
            }

            if (timelineItem.countLike < 2) {
                //itemView.tvCountLike.text = "${timelineItem.countLike} $TEXT_LIKE_SINGULAR"
                itemView.context.getString(R.string.title, itemView.tvCountLike.text)
            } else {
//                itemView.tvCountLike.text = "${timelineItem.countLike} $TEXT_LIKE_PLURAL"
                itemView.context.getString(R.string.title, itemView.tvCountLike.text)
            }
        }
    }
}
