package asiantech.internship.summer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogActivity extends AppCompatActivity {
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new LoginFragment());
        fragmentTransaction.commit();
        btn = findViewById(R.id.btnBack);
        btn.setVisibility(View.GONE);
        backFragment();
    }

    void replaceFragmemnt() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_right_to_left,
                R.anim.slide_out_left, R.anim.anim_left_to_right,
                R.anim.slide_out_right);
        setTextForToolBar("SIGN UP");
        btn.setVisibility(View.VISIBLE);
        fragmentTransaction.replace(R.id.fragment, new SignUpFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void onButton() {
        btn.setVisibility(View.VISIBLE);
    }

    public void setTextForToolBar(String mText) {
        tv = findViewById(R.id.tvToolBar);
        tv.setText(mText);
    }

    public void backFragment() {
        btn.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment instanceof LoginFragment) {
            setTextForToolBar("LOGIN");
            btn.setVisibility(View.GONE);
        } else {
            setTextForToolBar("SIGN UP");
            btn.setVisibility(View.VISIBLE);
        }
    }
}
