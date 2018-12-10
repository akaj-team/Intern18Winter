package asiantech.internship.summer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class ViewActivity extends AppCompatActivity {
    String[] title = {
            "A City Living Under The Shadow",
            "One Problem for Democratic Leaders",
            "The Golden Secret to Better Breakfast",
            "How to Plan Your First Ski Vacation",
            "How Social Isolation Is Killing Us",
            "Use Labels to Sort Messages in Facebook"
    };
    String[] poster = {
            "RBC News",
            "NY Times",
            "BBC World",
            "NBC Nightly",
            "RBC News",
            "BBC World"
    };
    int[] iconPublisher = {
            R.drawable.ic_rbc_news_1,
            R.drawable.ic_ny_times,
            R.drawable.ic_bbc_world_1,
            R.drawable.ic_nbc_nightly,
            R.drawable.ic_rbc_news_2,
            R.drawable.ic_bbc_world_2
    };

    private GridView mGvArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        mGvArticles = findViewById(R.id.gvArticle);
        GridAdapter gridAdapter = new GridAdapter(this, title, poster, iconPublisher);
        mGvArticles.setAdapter(gridAdapter);
    }
}
