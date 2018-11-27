package asiantech.internship.summer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.Model.Paper;
import asiantech.internship.summer.ViewHolder.PaperAdapter;

public class ViewAndViewGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_viewgroup);
        initView();
    }

    public void initView() {
        RecyclerView recyclerView = findViewById(R.id.rcvFirm);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        List<Paper> listPaper = new ArrayList<>();
        listPaper.add(new Paper("A City Living Under the Shadow", R.drawable.ic_nbc, "RBC News"));
        listPaper.add(new Paper("One Problem for Democaratic Leaders", R.drawable.ic_newyork, "NY Times"));
        listPaper.add(new Paper("The Golden Secret to Better Breakfast", R.drawable.ic_bbc, "BBC World"));
        listPaper.add(new Paper("How to Plan Your First Ski Vacation", R.drawable.ic_nbc, "NBC Nightly"));
        listPaper.add(new Paper("How Social Isolation Is Killing Us", R.drawable.ic_nbc, "RBC News"));
        listPaper.add(new Paper("Use Labels to Sort Messages in Facebook", R.drawable.ic_bbc, "BBC World"));
        PaperAdapter myViewAdapter = new PaperAdapter(listPaper);
        recyclerView.setAdapter(myViewAdapter);
    }
}
