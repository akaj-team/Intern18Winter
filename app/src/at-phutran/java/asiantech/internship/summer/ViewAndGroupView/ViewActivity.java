package asiantech.internship.summer.ViewAndGroupView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.model.News;
import asiantech.internship.summer.R;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view);
        List<News> listNews = new ArrayList<>();
        listNews.add(new News("A City Living Under The Shawdow", R.drawable.ic_avt, "RBC News"));
        listNews.add(new News("One Problem for Democratic Leaders", R.drawable.ic_avt1, "NY Times"));
        listNews.add(new News("The Golden Secret to Better Breakfast", R.drawable.ic_avt2, "BBC World"));
        listNews.add(new News("How to Plan Your First Ski Vaction", R.drawable.ic_avt3, "NBC Nightly"));
        listNews.add(new News("How Social Isolation Is Killing Us", R.drawable.ic_avt, "RBC News"));
        listNews.add(new News("Use Labels to Sort Messages in Facebook", R.drawable.ic_avt2, "BBC World"));
        GridView mGridView = findViewById(R.id.gvContent);
        NewsAdapter newsAdapter = new NewsAdapter(this, listNews);
        mGridView.setAdapter(newsAdapter);
    }
}
