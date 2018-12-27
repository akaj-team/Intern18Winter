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

    public static final int CHOOSE_CAMERA = 2;
    public static final int CHOOSE_GALLERY = 1;
    private OnItemClickListener mOnItemClickListener;

    public static ChoosePhotoDialogFragment newInstance() {
        return new ChoosePhotoDialogFragment();
    }

    public void setOnHeadlineSelectedListener(DrawerLayoutActivity activity) {
        mOnItemClickListener = activity;
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
        btnGallery.setOnClickListener(v -> mOnItemClickListener.onChooseImage(CHOOSE_GALLERY));
        Button btnCamera = view.findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(v -> mOnItemClickListener.onChooseImage(CHOOSE_CAMERA));
        return view;
    }

    public interface OnItemClickListener {
        void onChooseImage(int type);
    }
}
