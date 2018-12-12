package asiantech.internship.summer.viewandgroupview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import asiantech.internship.summer.R;

public class GridAdapter extends BaseAdapter {
    private String[] mTitles;
    private String[] mPosters;
    private int[] mIcons;
    private String[] mColors;

    GridAdapter(String[] title, String[] poster, int[] icon, String[] color) {
        this.mTitles = title;
        this.mPosters = poster;
        this.mIcons = icon;
        this.mColors = color;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_grid_adapter, parent, false);
            ImageView imageView = view.findViewById(R.id.imgPublisher);
            TextView mTvTitle = view.findViewById(R.id.tvTitle);
            TextView mTvPoster = view.findViewById(R.id.tvPoster);
            RelativeLayout relativeLayout = view.findViewById(R.id.llRoot);
            imageView.setImageResource(mIcons[position]);
            mTvTitle.setText(mTitles[position]);
            mTvPoster.setText(mPosters[position]);
            relativeLayout.setBackgroundColor(Color.parseColor(mColors[position]));
        }
        return view;
    }
}
