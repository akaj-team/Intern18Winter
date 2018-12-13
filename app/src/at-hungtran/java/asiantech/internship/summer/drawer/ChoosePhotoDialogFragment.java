package asiantech.internship.summer.drawer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import asiantech.internship.summer.R;

public class ChoosePhotoDialogFragment extends DialogFragment {

    private onItemClick mChooseOne;

    public static ChoosePhotoDialogFragment newInstance() {
        return new ChoosePhotoDialogFragment();
    }

    public void setOnHeadlineSelectedListener(DrawerLayoutActivity activity) {
        mChooseOne = activity;
    }

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
        View view = inflater.inflate(R.layout.choose_photo_dialog_fragment_layout, container, false);
        Button btnGallery = view.findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChooseOne.itemClick(1);
            }
        });
        Button btnCamera = view.findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChooseOne.itemClick(2);
            }
        });
        return view;
    }

    public interface onItemClick {
        void itemClick(int type);
    }
}
