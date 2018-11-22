package asiantech.internship.summer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

public class ViewActivity extends AppCompatActivity {

    GridView gridView;

    String[] values = {
            "A City Living Under The Shawdow",
            "One Problem for Democratic Leaders",
            "The Golden Secret to Better Breakfast",
            "How to Plan Your First Ski Vaction",
            "How Social Isolation Is Killing Us",
            "Use Labels to Sort Messages in Facebook"
    };


    int[] images ={
            R.drawable.ic_avt,
            R.drawable.ic_avt1,
            R.drawable.ic_avt2,
            R.drawable.ic_avt3,
            R.drawable.ic_avt,
            R.drawable.ic_avt2
    };
    String[] values1 = {
            "RBC News",
            "NY Times",
            "BBC World",
            "NBC Nightly",
            "RBC News",
            "BBC World"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view);

        gridView = findViewById(R.id.gv1);
        GridAdapter gridAdapter = new GridAdapter(this, values, images, values1);
        gridView.setAdapter(gridAdapter);
    }
}

