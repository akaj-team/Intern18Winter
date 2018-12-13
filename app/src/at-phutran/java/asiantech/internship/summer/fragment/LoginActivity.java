package asiantech.internship.summer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class LoginActivity extends AppCompatActivity {
    private Button mBackButton;
    private TextView mTextViewToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frContent, new LoginFragment());
        fragmentTransaction.commit();
        mBackButton = findViewById(R.id.btnBack);
        mBackButton.setVisibility(View.GONE);
        backFragment();
    }

    public void onButton() {
        mBackButton.setVisibility(View.VISIBLE);
    }

    public void setTextForToolBar(String title) {
        mTextViewToolbar = findViewById(R.id.tvToolBar);
        mTextViewToolbar.setText(title);
    }

    public void backFragment() {
        mBackButton.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frContent);
        if (fragment instanceof LoginFragment) {
            setTextForToolBar(getString(R.string.login));
            mBackButton.setVisibility(View.GONE);
        } else {
            setTextForToolBar(getString(R.string.signUp));
            mBackButton.setVisibility(View.VISIBLE);
        }
    }
}
