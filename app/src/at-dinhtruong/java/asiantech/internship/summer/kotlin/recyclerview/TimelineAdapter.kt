package asiantech.internship.summer.kotlin.recyclerview

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.model.TimelineItem
import kotlinx.android.synthetic.`at-dinhtruong`.fragment_recycler_view_pager.view.*
import kotlinx.android.synthetic.`at-dinhtruong`.item_progressbar.view.*


class TimelineAdapter(private var timelineItems: ArrayList<TimelineItem>,
                      private val context: Context?,
                      private val onItemClickListener: OnItemClickListener) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val viewTypeItem = 0
    private val viewTypeloading = 1
    var isLoading: Boolean = false

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(viewGroup.context)
        if (viewType == viewTypeItem) {
            val itView: View = layoutInflater.inflate(R.layout.fragment_recycler_view_pager, viewGroup, false)
            return TimelineViewHolder(itView)
        }
        val itView: View = layoutInflater.inflate(R.layout.item_progressbar, viewGroup, false)
        return ViewHolderLoading(itView)
    }

    override fun getItemCount(): Int {
        if (isLoading) {
            return timelineItems.size + 1
        }
        return timelineItems.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == timelineItems.size) {
            return viewTypeloading
        }
        return viewTypeItem
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is TimelineViewHolder) {
            viewHolder.onBind()
        } else if (viewHolder is ViewHolderLoading) {
            viewHolder.onBind()
        }
    }

    fun setLoaded(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    open inner class TimelineViewHolder(itemView: View) : ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            onItemClickListener.onFavouriteClicked(adapterPosition)
        }

        fun onBind() {
            val timelineItem = timelineItems[adapterPosition]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.imgAvatar.setImageDrawable(context?.getDrawable(getResourceID(timelineItem.avatar, "drawable", context)))
                itemView.imgImage.setImageDrawable(context?.getDrawable(getResourceID(timelineItem.image, "drawable", context)))
            }
            itemView.tvNumerLike.text = (timelineItem.numberOfLike.toString().trim() + " like")
            itemView.tvName.text = timelineItem.name.trim()
            itemView.tvDescription.text = ("<b>" + timelineItem.name + "</b>" + "  " + timelineItem.desription).toSpanned()
            itemView.imgFavourite.setOnClickListener(this)
        }

        private fun getResourceID(resName: String, resType: String, context: Context): Int {
            val resourceID = context.resources.getIdentifier(resName, resType, context.applicationInfo.packageName)
            return if (resourceID == 0) {
                throw IllegalArgumentException("No resource string found with name $resName")
            } else {
                resourceID
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

    fun String.toSpanned(): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(this)
        }
    }
}
