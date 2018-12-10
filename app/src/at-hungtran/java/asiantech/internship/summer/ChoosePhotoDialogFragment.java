package asiantech.internship.summer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ChoosePhotoDialogFragment extends DialogFragment {

    onItemClick chooseOne;
    Button mBtnGallery;
    Button mBtnCamera;

    public void setOnHeadlineSelectedListener(DrawerLayoutActivity activity) {
        chooseOne = activity;
    }


    public static ChoosePhotoDialogFragment newInstance() {
        return new ChoosePhotoDialogFragment();
    }

    View view;

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.choose_photo_dialog_fragment_layout, container, false);
        mBtnGallery = view.findViewById(R.id.btnGallery);
        mBtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseOne.itemClick(1);
            }
        });
        mBtnCamera = view.findViewById(R.id.btnCamera);
        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseOne.itemClick(2);
            }
        });
        return view;
    }

    public interface onItemClick {
        void itemClick(int type);
    }
}
