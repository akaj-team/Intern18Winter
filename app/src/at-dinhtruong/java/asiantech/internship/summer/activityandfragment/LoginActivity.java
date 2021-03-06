package asiantech.internship.summer.activityandfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitleToolBar;
    private ImageView mImgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFragment();
        initToolBar();
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LoginFragment());
        fragmentTransaction.commit();
    }

    public void initToolBar() {
        mTvTitleToolBar = findViewById(R.id.tvToolBarTitle);
        mImgBack = findViewById(R.id.imgBack);
        mImgBack.setOnClickListener(this);
    }

    public void setTitleToolBar(String titleToolBar) {
        mTvTitleToolBar.setText(titleToolBar);
    }

    public void setButtonBack(int resId) {
        mImgBack.setImageResource(resId);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof LoginFragment) {
            setTitleToolBar(getString(R.string.login));
            setButtonBack(0);
        } else if (fragment instanceof SignUpFragment) {
            setTitleToolBar(getString(R.string.signup));
        }
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }
}
