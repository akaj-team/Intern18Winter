package asiantech.internship.summer.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Data;

public class HomeActivity extends AppCompatActivity {
    private static final int COLUMNS = 2;
    List<Data> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutfb);
        initView();
    }

    public void initView() {
        NewsAdapter mNewsAdapter;
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        data.add(new Data("A city Living Under the Shadow", R.drawable.ic_avt, "RBC News", "#e1dfdf"));
        data.add(new Data("The Problem for Democratic Leaders", R.drawable.ic_avt1, "NY Times", "#f9f9f9"));
        data.add(new Data("The Golden Secret to Better Beakfast", R.drawable.ic_avt2, "BBC World", "#f9f9f9"));
        data.add(new Data("How to Plan Your First Ski Vacation", R.drawable.ic_avt3, "NBC Nightly", "#e1dfdf"));
        data.add(new Data("How Social Isolation Is Killing Us", R.drawable.ic_avt4, "RBC News", "#e1dfdf"));
        data.add(new Data("The Labels to Sort Messages in Facebook", R.drawable.ic_avt5, "BBC World", "#f9f9f9"));
        mNewsAdapter = new NewsAdapter(data);
        recyclerView.setAdapter(mNewsAdapter);
    }
}
