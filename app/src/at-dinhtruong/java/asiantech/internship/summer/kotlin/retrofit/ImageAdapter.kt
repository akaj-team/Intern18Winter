package asiantech.internship.summer.kotlin.retrofit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.model.Image
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-dinhtruong`.restapi_item.view.*


class ImageAdapter(private val listImage: ArrayList<Image>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ImageAdapter.ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.restapi_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount() = listImage.size

    inner class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun onBind() {
            val image = listImage.get(index = adapterPosition)
            if (!image.image_id.isEmpty()) {
                Glide.with(itemView.context).load(image.url).into(itemView.imgItem)
                view.tvItem.text = image.image_id
            }
        }
    }
}
