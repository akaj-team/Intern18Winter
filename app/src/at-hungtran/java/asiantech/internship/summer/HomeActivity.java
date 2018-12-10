package asiantech.internship.summer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    List<Data> data = new ArrayList<>();
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutfb);
        initView();
    }

    public void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        data.add(new Data("A city Living Under the Shadow", R.mipmap.ic_avt, "RBC News", "#e1dfdf"));
        data.add(new Data("The Problem for Democratic Leaders", R.mipmap.ic_avt1, "NY Times", "#f9f9f9"));
        data.add(new Data("The Golden Secret to Better Beakfast", R.mipmap.ic_avt2, "BBC World", "#f9f9f9"));
        data.add(new Data("How to Plan Your First Ski Vacation", R.mipmap.ic_avt3, "NBC Nightly", "#e1dfdf"));
        data.add(new Data("How Social Isolation Is Killing Us", R.mipmap.ic_avt4, "RBC News", "#e1dfdf"));
        data.add(new Data("The Labels to Sort Messages in Facebook", R.mipmap.ic_avt5, "BBC World", "#f9f9f9"));
        newsAdapter = new NewsAdapter(data);
        recyclerView.setAdapter(newsAdapter);
    }
}
