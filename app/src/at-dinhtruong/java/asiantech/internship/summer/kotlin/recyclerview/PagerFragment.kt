package asiantech.internship.summer.kotlin.recyclerview
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.kotlin.model.TimelineItem
import java.util.*
import kotlin.collections.ArrayList


class PagerFragment : Fragment(), TimelineAdapter.OnItemClickListener {
    override fun onFavouriteClicked(position: Int) {
        val timelineItem = mTimelineItems[position]
        timelineItem.numberOfLike = timelineItem.numberOfLike + 1
        viewAdapter.notifyItemChanged(position)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var mTimelineItems: List<TimelineItem>
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        fun newInstance(): PagerFragment {
            return PagerFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pager, container, false)
        initTimeline(view)
        return view
    }

    private fun initTimeline(view: View) {
        mTimelineItems = mockTimelines()
        viewManager = LinearLayoutManager(view.context)
        viewAdapter = TimelineAdapter(mTimelineItems as ArrayList<TimelineItem>, context, this)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPager).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun mockTimelines(): List<TimelineItem> {
        val random = Random()
        val timelineItems = ArrayList<TimelineItem>()
        for (i in 1..10) {
            val randomAvatar = random.nextInt(10) + 1
            val randomImage = random.nextInt(10) + 1
            timelineItems.add(
                TimelineItem(
                    0, "img_avatar$randomAvatar", "Nguyen Van $i", "img_image$randomImage",
                    " Đây là tất cả phần mô tả cho nội dung thứ $i"
                )
            )
        }
        return timelineItems
    }
}
