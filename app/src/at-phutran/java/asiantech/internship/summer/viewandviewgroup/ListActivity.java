package asiantech.internship.summer.viewandviewgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import asiantech.internship.summer.R;
import asiantech.internship.summer.canvas.CanvasActivity;
import asiantech.internship.summer.drawerlayout.DrawerLayoutActivity;
import asiantech.internship.summer.eventlistener.SignUpActivity;
import asiantech.internship.summer.fragment.LoginActivity;
import asiantech.internship.summer.recyclerview.RecyclerViewActivity;
import asiantech.internship.summer.viewpager.ViewPagerActivity;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnView;
    private Button mBtnListener;
    private Button mBtnRecyclerView;
    private Button mBtnFragment;
    private Button mBtnViewPager;
    private Button mBtnDrawerLayout;
    private Button mBtnCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mBtnView = findViewById(R.id.btnExercise1);
        mBtnListener = findViewById(R.id.btnExercise2);
        mBtnFragment = findViewById(R.id.btnExercise3);
        mBtnRecyclerView = findViewById(R.id.btnExercise4);
        mBtnViewPager = findViewById(R.id.btnExercise5);
        mBtnDrawerLayout = findViewById(R.id.btnExercise7);
        mBtnCanvas = findViewById(R.id.btnExercise9);
        mBtnView.setOnClickListener(this);
        mBtnListener.setOnClickListener(this);
        mBtnFragment.setOnClickListener(this);
        mBtnRecyclerView.setOnClickListener(this);
        mBtnViewPager.setOnClickListener(this);
        mBtnDrawerLayout.setOnClickListener(this);
        mBtnCanvas.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExercise1: {
                Intent intent = new Intent(ListActivity.this, ViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnExercise2: {
                Intent intent = new Intent(ListActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnExercise3: {
                Intent intent = new Intent(ListActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnExercise4: {
                Intent intent = new Intent(ListActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnExercise5: {
                Intent intent = new Intent(ListActivity.this, ViewPagerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnExercise9: {
                Intent intent = new Intent(ListActivity.this, CanvasActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnExercise7: {
                Intent intent = new Intent(ListActivity.this, DrawerLayoutActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
