package asiantech.internship.summer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView mTvToolBar;
    private Button mBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentInit();
        mTvToolBar = findViewById(R.id.tvToolBar);
        mBtnBack = findViewById(R.id.btnBack);
        backFragment();
    }

    public void fragmentInit(){
        Fragment loginFragment = new LoginFragment();
        FragmentManager loginFragmentManager = getSupportFragmentManager();
        FragmentTransaction loginFragmentTransaction = loginFragmentManager.beginTransaction();
        loginFragmentTransaction.replace(R.id.llLogin, loginFragment);
        loginFragmentTransaction.addToBackStack(null);
        loginFragmentTransaction.commit();
    }

    public void isInstanceOf(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.llLogin);
        if (fragment instanceof LoginFragment) {
            setTextToolBar(R.string.logIn);
            mBtnBack.setVisibility(View.VISIBLE);
        } else {
            setTextToolBar(R.string.backToScreen);
            mBtnBack.setVisibility(View.VISIBLE);
        }
    }

    public void onButton() {
        mBtnBack.setVisibility(View.VISIBLE);
    }

    public void setTextToolBar(int title) {
        mTvToolBar.setText(title);
    }

    public void backFragment() {
        mBtnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isInstanceOf();
    }
}
