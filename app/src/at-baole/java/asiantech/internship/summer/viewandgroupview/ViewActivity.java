package asiantech.internship.summer.viewandgroupview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import asiantech.internship.summer.R;

public class ViewActivity extends AppCompatActivity {
    String[] mTitles = {

            "A City Living Under The Shadow",
            "One Problem for Democratic Leaders",
            "The Golden Secret to Better Breakfast",
            "How to Plan Your First Ski Vacation",
            "How Social Isolation Is Killing Us",
            "Use Labels to Sort Messages in Facebook"
    };
    String[] mPosters = {

            "RBC News",
            "NY Times",
            "BBC World",
            "NBC Nightly",
            "RBC News",
            "BBC World"
    };
    int[] mIconPublishers = {
            R.drawable.ic_rbc_news_1,
            R.drawable.ic_ny_times,
            R.drawable.ic_bbc_world_1,
            R.drawable.ic_nbc_nightly,
            R.drawable.ic_rbc_news_2,
            R.drawable.ic_bbc_world_2
    };

    String[] mColors = {
            "#e1dfdf",
            "#f9f9f9",
            "#f9f9f9",
            "#e1dfdf",
            "#e1dfdf",
            "#f9f9f9"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        GridView gvArticles = findViewById(R.id.gvArticle);
        GridAdapter gridAdapter = new GridAdapter(mTitles, mPosters, mIconPublishers, mColors);
        gvArticles.setAdapter(gridAdapter);
    }
}
