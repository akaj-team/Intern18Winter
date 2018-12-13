package asiantech.internship.summer.viewandviewgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.News;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        List<News> listNews = new ArrayList<>();
        listNews.add(new News("A City Living Under The Shawdow", R.drawable.ic_avt, "RBC News"));
        listNews.add(new News("One Problem for Democratic Leaders", R.drawable.ic_avt1, "NY Times"));
        listNews.add(new News("The Golden Secret to Better Breakfast", R.drawable.ic_avt2, "BBC World"));
        listNews.add(new News("How to Plan Your First Ski Vaction", R.drawable.ic_avt3, "NBC Nightly"));
        listNews.add(new News("How Social Isolation Is Killing Us", R.drawable.ic_avt, "RBC News"));
        listNews.add(new News("Use Labels to Sort Messages in Facebook", R.drawable.ic_avt2, "BBC World"));
        GridView gridView = findViewById(R.id.gvContent);
        NewsAdapter newsAdapter = new NewsAdapter(listNews);
        gridView.setAdapter(newsAdapter);
    }
}
