package asiantech.internship.summer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextViewTitleToolBar;
    private ImageView mImageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFragment();
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LoginFragment());
        fragmentTransaction.commit();

    }

    public void setTitleToolBar(String mTitleToolBar) {
        mTextViewTitleToolBar = findViewById(R.id.toolBarTitle);
        mTextViewTitleToolBar.setText(mTitleToolBar);
    }

    public void setButtonBack(int n) {
        mImageViewBack = findViewById(R.id.imgBack);
        mImageViewBack.setOnClickListener(this);
        mImageViewBack.setImageResource(n);
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
