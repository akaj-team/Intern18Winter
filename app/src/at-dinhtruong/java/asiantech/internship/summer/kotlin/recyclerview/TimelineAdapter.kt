package asiantech.internship.summer.kotlin.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.model.TimelineItem
import kotlinx.android.synthetic.`at-dinhtruong`.fragment_recycler_view_pager.view.*
import kotlinx.android.synthetic.`at-dinhtruong`.item_progressbar.view.*


class TimelineAdapter(
        private val timelineItems: ArrayList<TimelineItem>,
        private val context: Context?,
        private val onItemClickListener: OnItemClickListener
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEWTYPEITEM = 0
    private val VIEW_TYPE_LOADING = 1
    var mIsLoading: Boolean = false

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(viewGroup.context)
        if (viewType == VIEWTYPEITEM) {
            val itView = layoutInflater.inflate(R.layout.fragment_recycler_view_pager, viewGroup, false)
            return TimelineViewHolder(itView)
        }
        val itView = layoutInflater.inflate(R.layout.item_progressbar, viewGroup, false)
        return ViewHolderLoading(itView)
    }

    override fun getItemCount(): Int {
        if (mIsLoading) {
            return timelineItems.size + 1
        }
        return timelineItems.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == timelineItems.size) {
            return VIEW_TYPE_LOADING
        }
        return VIEWTYPEITEM
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is TimelineViewHolder) {
            viewHolder.onBind()
        } else if (viewHolder is ViewHolderLoading) {
            viewHolder.onBind()
        }
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class TimelineViewHolder(itemView: View) : ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            onItemClickListener.onFavouriteClicked(adapterPosition)
        }

        fun onBind() {
            val timelineItem = timelineItems.get(adapterPosition)
            itemView.imgAvatar.setImageDrawable(
                    context?.resources?.getDrawable(
                            getResourceID(timelineItem.avatar, "drawable", context)
                    )
            )
            itemView.imgImage.setImageDrawable(
                    context?.resources?.getDrawable(
                            getResourceID(
                                    timelineItem.image,
                                    "drawable",
                                    context
                            )
                    )
            )
            itemView.tvNumerLike.text = (timelineItem.numberOfLike.toString().trim() + " like")
            itemView.tvName.text = timelineItem.name.trim()
            itemView.tvDescription.text =
                    Html.fromHtml("<b>" + timelineItem.name + "</b>" + "  " + timelineItem.desription)
            itemView.imgFavourite.setOnClickListener(this)
        }

        protected fun getResourceID(resName: String, resType: String, context: Context): Int {
            val ResourceID = context.resources.getIdentifier(
                    resName, resType,
                    context.applicationInfo.packageName
            )
            return if (ResourceID == 0) {
                throw IllegalArgumentException(
                        "No resource string found with name $resName"
                )
            } else {
                ResourceID
            }
        }

    }

    inner class ViewHolderLoading(itemView: View) : ViewHolder(itemView) {
        fun onBind() {
            itemView.itemProgressbar.isIndeterminate = true
        }
    }

    interface OnItemClickListener {
        fun onFavouriteClicked(position: Int)
    }

}
