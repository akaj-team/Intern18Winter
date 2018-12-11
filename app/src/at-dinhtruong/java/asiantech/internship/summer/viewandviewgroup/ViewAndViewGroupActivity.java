package asiantech.internship.summer.viewandviewgroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Paper;

public class ViewAndViewGroupActivity extends AppCompatActivity {
    public static final int NUMBER_OF_COLUMNS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_viewgroup);
        initView();
    }

    public void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycleViewFirm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));
        List<Paper> listPaper = new ArrayList<>();
        listPaper.add(new Paper("A City Living Under the Shadow", R.drawable.ic_nbc, "RBC News", "#f4f2f2"));
        listPaper.add(new Paper("One Problem for Democaratic Leaders", R.drawable.ic_newyork, "NY Times", "#ffffff"));
        listPaper.add(new Paper("The Golden Secret to Better Breakfast", R.drawable.ic_bbc, "BBC World", "#ffffff"));
        listPaper.add(new Paper("How to Plan Your First Ski Vacation", R.drawable.ic_nbc, "NBC Nightly", "#f4f2f2"));
        listPaper.add(new Paper("How Social Isolation Is Killing Us", R.drawable.ic_nbc, "RBC News", "#f4f2f2"));
        listPaper.add(new Paper("Use Labels to Sort Messages in Facebook", R.drawable.ic_bbc, "BBC World", "#ffffff"));
        recyclerView.setAdapter(new PaperAdapter(listPaper));
    }
}
