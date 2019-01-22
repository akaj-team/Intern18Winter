package asiantech.internship.summer.kotlin.retrofit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-phutran`.fragment_item_image.view.*

class RetrofitAdapter (val mListImage : ArrayList<Image>) : RecyclerView.Adapter<RetrofitAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(mListImage[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetrofitAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_item_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListImage.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val imgContent = view.imgContent
        val tvContent = view.tvContent
        fun onBind(image: Image) {
            assert(image != null)
            if (!image.image_id.isEmpty()) {
                tvContent.text = "this is picture"
                Glide.with(itemView.context).load(image.url).into(imgContent)
            }
        }

    }

}