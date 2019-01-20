package asiantech.internship.summer.kotlin.recyclerview
import android.os.Bundle
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

    private lateinit var recyclerView: RecyclerView
    private lateinit var mTimelineItems: List<TimelineItem>
    private lateinit var timelineAdapter: TimelineAdapter
    private lateinit var viewManager: LinearLayoutManager

    //test
    private val NUM_OF_ITEM_ON_PAGE = 10
    private var mIsLoadmore = true
    private var mTotalItemCount: Int = 0
    private var mChildCount: Int = 0
    private var mFirstVisible: Int = 0

    companion object {
        fun newInstance(): PagerFragment {
            return PagerFragment()
        }
    }
    override fun onFavouriteClicked(position: Int) {
        val timelineItem = mTimelineItems[position]
        timelineItem.numberOfLike = timelineItem.numberOfLike + 1
        timelineAdapter.notifyItemChanged(position)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pager, container, false)
        initTimeline(view)
        return view
    }

    private fun initTimeline(view: View) {
        mTimelineItems = mockTimelines()
        viewManager = LinearLayoutManager(view.context)

        timelineAdapter = TimelineAdapter(mTimelineItems as ArrayList<TimelineItem>, context, this)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPager).apply {
            setHasFixedSize(true)
           layoutManager = viewManager
            adapter = timelineAdapter
        }
        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mTotalItemCount = viewManager.itemCount
                mChildCount = viewManager.childCount
                mFirstVisible = viewManager.findFirstVisibleItemPosition()
                if (mFirstVisible + mChildCount == mTotalItemCount && mIsLoadmore) {
                    mIsLoadmore = false;
                    //timelineAdapter.setLoaded(true);
                    timelineAdapter.mIsLoading
                    timelineAdapter.notifyDataSetChanged();
                    addItemLoadmore(timelineAdapter);
                }
            }
        } )
    }

    private fun addItemLoadmore(timelineAdapter: RecyclerView.Adapter<*>) {

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
