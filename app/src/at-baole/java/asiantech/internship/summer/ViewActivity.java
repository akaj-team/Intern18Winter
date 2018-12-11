package asiantech.internship.summer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class ViewActivity extends AppCompatActivity {
    String[] title = {
            String.valueOf(R.string.title1),
            String.valueOf(R.string.title2),
            String.valueOf(R.string.title3),
            String.valueOf(R.string.title4),
            String.valueOf(R.string.title5),
            String.valueOf(R.string.title6),
    };
    String[] poster = {
            String.valueOf(R.string.publisher1),
            String.valueOf(R.string.publisher2),
            String.valueOf(R.string.publisher3),
            String.valueOf(R.string.publisher4),
            String.valueOf(R.string.publisher1),
            String.valueOf(R.string.publisher3),
    };
    int[] iconPublisher = {
            R.drawable.ic_rbc_news_1,
            R.drawable.ic_ny_times,
            R.drawable.ic_bbc_world_1,
            R.drawable.ic_nbc_nightly,
            R.drawable.ic_rbc_news_2,
            R.drawable.ic_bbc_world_2
    };

    private GridView mGvArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        mGvArticles = findViewById(R.id.gvArticle);
        GridAdapter gridAdapter = new GridAdapter(this, title, poster, iconPublisher);
        mGvArticles.setAdapter(gridAdapter);
    }
}
