package asiantech.internship.summer.kotlin.recyclerview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.model.TimelineItem
import java.util.*
import kotlin.collections.ArrayList

class PagerFragment : Fragment(), TimelineAdapter.OnItemClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var timelineItems: ArrayList<TimelineItem>
    lateinit var timelineAdapter: TimelineAdapter
    lateinit var viewManager: LinearLayoutManager
    val numOfItemOnPage = 10
    var totalItemCount = 0
    var childCount = 0
    var firstVisible = 0
    var isLoadmore = true

    companion object {
        fun newInstance(): PagerFragment {
            return PagerFragment()
        }
    }

    override fun onFavouriteClicked(position: Int) {
        val timelineItem = timelineItems[position]
        timelineItem.numberOfLike = timelineItem.numberOfLike + 1
        timelineAdapter.notifyItemChanged(position)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pager, container, false)
        initTimeline(view)
        return view
    }

    private fun initTimeline(view: View) {
        timelineItems = ArrayList(mockTimelines())
        viewManager = LinearLayoutManager(view.context)
        timelineAdapter = TimelineAdapter(timelineItems, context, this)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPager).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = timelineAdapter
        }
        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = viewManager.itemCount
                childCount = viewManager.childCount
                firstVisible = viewManager.findFirstVisibleItemPosition()
                if (firstVisible + childCount == totalItemCount && isLoadmore) {
                    isLoadmore = false
                    timelineAdapter.setLoaded(true)
                    timelineAdapter.isLoading
                    timelineAdapter.notifyDataSetChanged()
                    addItemLoadmore(timelineAdapter)
                }
            }
        })
    }

    private fun addItemLoadmore(timelineAdapter: TimelineAdapter) {
        object : Thread({
            try {
                Thread.sleep(3000)
                val fromIndex = timelineItems.size
                val toIndex = fromIndex + numOfItemOnPage - 1
                val random = Random()
                (fromIndex..toIndex).forEach {
                    val randomAvatar = random.nextInt(10) + 1
                    val randomImage = random.nextInt(10) + 1
                    timelineItems.add(TimelineItem(0, "img_avatar$randomAvatar", "Nguyen Van " + (it + 1), "img_image$randomImage", "Noi dung thu " + (it + 1)))
                }
                Handler(Looper.getMainLooper()).post {
                    isLoadmore = true
                    timelineAdapter.setLoaded(false)
                    timelineAdapter.notifyDataSetChanged()
                }
            } catch (ignored: InterruptedException) {

            }
        }) {

        }.start()
    }

    private fun mockTimelines(): List<TimelineItem> {
        val random = Random()
        val timelineItems = ArrayList<TimelineItem>()
        (1..10).forEach {
            val randomAvatar = random.nextInt(10) + 1
            val randomImage = random.nextInt(10) + 1
            timelineItems.add(TimelineItem(0, "img_avatar$randomAvatar", "Nguyen Van $it", "img_image$randomImage", " Đây là tất cả phần mô tả cho nội dung thứ $it"))
        }
        return timelineItems
    }
}
