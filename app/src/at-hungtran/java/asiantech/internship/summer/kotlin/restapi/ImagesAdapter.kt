package asiantech.internship.summer.kotlin.restapi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.model.ImageItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-hungtran`.item_image.view.*

class ImagesAdapter(private val imageItems : List<ImageItem>) : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImagesViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return imageItems.size
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val item : ImageItem = imageItems[position]
        holder.onBind(item)
    }

    class ImagesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item : ImageItem){
            Glide.with(itemView.context).load(item.getUrl()).into(itemView.imgPhoto)
            itemView.tvContent.text = item.getType()
        }
    }

}