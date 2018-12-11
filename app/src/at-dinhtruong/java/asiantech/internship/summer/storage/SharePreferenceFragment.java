package asiantech.internship.summer.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import asiantech.internship.summer.R;

public class SharePreferenceFragment extends Fragment {
    private EditText mEdtUserName;
    private EditText mEdtPassWord;
    private Button mBtnLogin;
    private String mUserName;
    private String mPassWord;
    private String mUserNameShare;
    private String mPassWordShare;
    private final String USER_NAME = "userName";
    private final String PASS_WORD = "passWord";
    private final String DATA_USER = "dataUser";

    public SharePreferenceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_preference, container, false);
        initViewLogin(view);
        return view;
    }

    private void initViewLogin(View view) {
        mEdtUserName = view.findViewById(R.id.edtUserName);
        mEdtPassWord = view.findViewById(R.id.edtPassWord);
        mBtnLogin = view.findViewById(R.id.btnLogin);
        readData();
        if (mUserNameShare.equals("") == false && mPassWordShare.equals("") == false) {
            mEdtUserName.setText(mUserNameShare);
            mEdtPassWord.setText(mPassWordShare);
        }
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserName = mEdtUserName.getText().toString();
                mPassWord = mEdtPassWord.getText().toString();
                saveData();
            }
        });

    }


    private void saveData() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences(DATA_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(USER_NAME, mUserName);
        edit.putString(PASS_WORD, mPassWord);
        edit.commit();
    }

    private void readData() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences(DATA_USER, Context.MODE_PRIVATE);
        mUserNameShare = preferences.getString(USER_NAME, "");
        mPassWordShare = preferences.getString(PASS_WORD, "");
    }
}
