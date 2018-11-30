package asiantech.internship.summer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_content, new LoginFragment());
        fragmentTransaction.commit();
        button = findViewById(R.id.btnBack);
        button.setVisibility(View.GONE);
        backFragment();
    }
    public void onButton(){
        button.setVisibility(View.VISIBLE);
    }
    public void setTextForToolBar(String mText){
        textView = findViewById(R.id.tvToolBar);
        textView.setText(mText);
    }
    public void backFragment(){
        button.setOnClickListener(v -> {
            onBackPressed();
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_content);
        if(fragment instanceof LoginFragment){
            setTextForToolBar("LOGIN");
            button.setVisibility(View.GONE);
        }else{
            setTextForToolBar("SIGN UP");
            button.setVisibility(View.VISIBLE);
        }
    }
}
