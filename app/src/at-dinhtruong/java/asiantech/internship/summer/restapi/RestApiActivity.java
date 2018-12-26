package asiantech.internship.summer.restapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Image;

public class RestApiActivity extends AppCompatActivity {
    private ImageAdapter mImageAdapter;
    private List<Image> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);
        initViews();
    }

    private void initViews() {
        mImages = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewItem);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mImageAdapter = new ImageAdapter(mImages);
        recyclerView.setAdapter(mImageAdapter);
    }
}
