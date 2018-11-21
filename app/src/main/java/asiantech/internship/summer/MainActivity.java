package asiantech.internship.summer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;


import java.util.ArrayList;

import asiantech.internship.summer.Model.ModelPaper;
import asiantech.internship.summer.ViewHolder.MyViewAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();

    }

    public void initView() {
        RecyclerView recyclerView = findViewById(R.id.rcvFirm);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        ArrayList<ModelPaper> listPaper = new ArrayList<>();
        listPaper.add(new ModelPaper("A City Living Under the Shadow", R.drawable.avatarnbc, "RBC News"));
        listPaper.add(new ModelPaper("One Problem for Democaratic Leaders", R.drawable.avatarnewyork, "NY Times"));
        listPaper.add(new ModelPaper("The Golden Secret to Better Breakfast", R.drawable.avatarbbc, "BBC World"));
        listPaper.add(new ModelPaper("How to Plan Your First Ski Vacation", R.drawable.avatarnbc, "NBC Nightly"));
        listPaper.add(new ModelPaper("How Social Isolation Is Killing Us", R.drawable.avatarnbc, "RBC News"));
        listPaper.add(new ModelPaper("Use Labels to Sort Messages in Facebook", R.drawable.avatarbbc, "BBC World"));
        MyViewAdapter myViewAdapter = new MyViewAdapter(listPaper, getApplicationContext());
        recyclerView.setAdapter(myViewAdapter);

    }

}
