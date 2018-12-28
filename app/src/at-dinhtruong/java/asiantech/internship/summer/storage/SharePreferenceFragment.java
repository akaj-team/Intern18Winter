package asiantech.internship.summer.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import asiantech.internship.summer.R;

public class SharePreferenceFragment extends Fragment implements View.OnClickListener {
    private static final String USER_NAME = "userName";
    private static final String PASS_WORD = "passWord";
    private static final String DATA_USER = "dataUser";
    private EditText mEdtUserName;
    private EditText mEdtPassWord;
    private String mUserName;
    private String mPassWord;
    private String mUserNameShare;
    private String mPassWordShare;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_preference, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mEdtUserName = view.findViewById(R.id.edtUserName);
        mEdtPassWord = view.findViewById(R.id.edtPassWord);
        Button btnLogin = view.findViewById(R.id.btnLogin);
        readData();
        if (!mUserNameShare.isEmpty() && !mPassWordShare.isEmpty()) {
            mEdtUserName.setText(mUserNameShare);
            mEdtPassWord.setText(mPassWordShare);
        }
        btnLogin.setOnClickListener(this);
    }

    private void saveData() {
        SharedPreferences preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(DATA_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(USER_NAME, mUserName);
        edit.putString(PASS_WORD, mPassWord);
        edit.apply();
    }

    private void readData() {
        if (getActivity() instanceof FileStoreActivity) {
            SharedPreferences preferences = (getActivity()).getSharedPreferences(DATA_USER, Context.MODE_PRIVATE);
            mUserNameShare = preferences.getString(USER_NAME, "");
            mPassWordShare = preferences.getString(PASS_WORD, "");
        }
    }

    @Override
    public void onClick(View view) {
        mUserName = mEdtUserName.getText().toString();
        mPassWord = mEdtPassWord.getText().toString();
        saveData();
        Toast.makeText(getContext(), R.string.loginSuccessful, Toast.LENGTH_LONG).show();
    }
}
