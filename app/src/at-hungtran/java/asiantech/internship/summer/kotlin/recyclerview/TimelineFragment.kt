package asiantech.internship.summer.kotlin.recyclerview

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hungtran`.fragment_timeline.*
import java.util.*
import kotlin.collections.ArrayList

class TimelineFragment : Fragment(), TimelineAdapter.OnItemListener {
    private var mTimelineItems: List<asiantech.internship.summer.kotlin.model.TimelineItem> = ArrayList()
    private val comment = "チャン ヴァン フン "
    private val nameOfPeople = "Le Thi Quynh Chau "
    private lateinit var mTimelineAdapter: TimelineAdapter
    private var mIsScroll = true
    private var mProgressBarLoading: ProgressBar? = null
    private var mCurrentItem: Int = 0
    private var mTotalItem: Int = 0
    private var mScrollOutItems: Int = 0

    override fun onClickLike(position: Int) {
        mTimelineItems[position].like += 1
        mTimelineAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    fun initView() {
        recycler_view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recycler_view.layoutManager = layoutManager
        createTimelineItem()
        mTimelineAdapter = TimelineAdapter(mTimelineItems)
        recycler_view.adapter = mTimelineAdapter
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mCurrentItem = layoutManager.childCount
                mTotalItem = layoutManager.itemCount
                mScrollOutItems = layoutManager.findFirstVisibleItemPosition()
                if (mIsScroll && mScrollOutItems + mCurrentItem == mTotalItem) {
                    mIsScroll = false
                    loadMoreData()
                }
            }
        })
    }

    private fun createTimelineItem() {
        val size = mTimelineItems.size
        val timelineItems = ArrayList<asiantech.internship.summer.kotlin.model.TimelineItem>()
        for (i in size until size + 10) {
            val mLike = 0
            timelineItems.add(asiantech.internship.summer.kotlin.model.TimelineItem(inputRandomImgAvt(), nameOfPeople + (i + 1), getRandomImageId(), comment + (i + 1), mLike, nameOfPeople + (i + 1)))
        }
    }

    private fun getRandomImageId(): Int {
        val random = Random()
        val rand = random.nextInt(10) + 1
        return when (rand) {
            1 -> R.drawable.img_nature_11
            2 -> R.drawable.img_itachi
            3 -> R.drawable.img_violet_evergarden
            4 -> R.drawable.img_nature_4
            5 -> R.drawable.img_violet
            6 -> R.drawable.img_nature_7
            7 -> R.drawable.img_nature_8
            8 -> R.drawable.img_nature_6
            9 -> R.drawable.img_nature_1
            else -> R.drawable.img_nature_4
        }
    }

    private fun inputRandomImgAvt(): Int {
        val random = Random()
        val rand = random.nextInt(10) + 1
        return when (rand) {
            1 -> R.drawable.img_nature_11
            2 -> R.drawable.img_itachi
            3 -> R.drawable.img_violet_evergarden
            4 -> R.drawable.img_nature_4
            5 -> R.drawable.img_violet
            6 -> R.drawable.img_nature_7
            7 -> R.drawable.img_nature_8
            8 -> R.drawable.img_nature_6
            9 -> R.drawable.img_nature_1
            else -> R.drawable.img_nature_4
        }
    }

    private fun loadMoreData() {
        mProgressBarLoading?.visibility = View.VISIBLE
        Thread {
            try {
                Thread.sleep(2000)
                createTimelineItem()
                Handler(Looper.getMainLooper()).post {
                    mIsScroll = true
                    mProgressBarLoading?.visibility = View.GONE
                    recycler_view.adapter.notifyDataSetChanged()
                }
            } catch (ignored: InterruptedException) {
            }
        }.start()
    }
}
