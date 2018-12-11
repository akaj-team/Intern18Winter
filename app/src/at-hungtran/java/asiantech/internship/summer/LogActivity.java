package asiantech.internship.summer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogActivity extends AppCompatActivity {
    private Button mBtnBack;
    private TextView mTvToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frContainer, new LoginFragment());
        fragmentTransaction.commit();
        mTvToolBar = findViewById(R.id.tvToolBar);
        mBtnBack = findViewById(R.id.btnBack);
        mBtnBack.setVisibility(View.GONE);
        backFragment();
    }

    void replaceFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_right_to_left,
                R.anim.slide_out_left, R.anim.anim_left_to_right,
                R.anim.slide_out_right);
        setTextForToolBar(getString(R.string.sign_up));
        mBtnBack.setVisibility(View.VISIBLE);
        fragmentTransaction.replace(R.id.frContainer, new SignUpFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void onButton() {
        mBtnBack.setVisibility(View.VISIBLE);
    }

    public void setTextForToolBar(String title) {
        mTvToolBar.setText(title);
    }

    public void backFragment() {
        mBtnBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frContainer);
        if (fragment instanceof LoginFragment) {
            setTextForToolBar("LOGIN");
            mBtnBack.setVisibility(View.GONE);
        } else {
            setTextForToolBar("SIGN UP");
            mBtnBack.setVisibility(View.VISIBLE);
        }
    }
}
