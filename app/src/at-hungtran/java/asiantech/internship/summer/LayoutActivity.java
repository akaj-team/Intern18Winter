package asiantech.internship.summer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class LayoutActivity extends AppCompatActivity {
    GridView gridView;
    String[] values = {
            "A city Living Under the Shadow",
            "The Problem for Democratic Leaders",
            "The Golden Secret to Better Beakfast",
            "How to Plan Your First Ski Vacation",
            "How Social Isolation Is Killing Us",
            "The Labels to Sort Messages in Facebook"
    };
    int[] images = {
            R.mipmap.ic_avt,
            R.mipmap.ic_avt1,
            R.mipmap.ic_avt2,
            R.mipmap.ic_avt3,
            R.mipmap.ic_avt4,
            R.mipmap.ic_avt5
    };
    String[] values1 = {
            "RBC News",
            "NY Times",
            "BBC World",
            "NBC Nightly",
            "RBC News",
            "BBC World"
    };
    int[] color = {
            R.color.colorBg,
            R.color.colorBgWhite,
            R.color.colorBg,
            R.color.colorBgWhite,
            R.color.colorBg,
            R.color.colorBgWhite
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutfb);
        gridView = findViewById(R.id.gridView);
        gridView.setNumColumns(2);
        NewsAdapter newsAdapter = new NewsAdapter(this, values, values1, images);
        gridView.setAdapter(newsAdapter);
    }
}
