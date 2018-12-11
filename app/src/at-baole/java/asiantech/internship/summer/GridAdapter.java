package asiantech.internship.summer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    private String[] mTitles;
    private String[] mPosters;
    private int[] mIcons;

    public GridAdapter(String[] title, String[] poster, int[] icon) {
        this.mTitles = title;
        this.mPosters = poster;
        this.mIcons = icon;
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
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = null;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.activity_grid_adapter, null);
            ImageView imageView = view.findViewById(R.id.imgPublisher);
            TextView mTvTitle = view.findViewById(R.id.tvTitle);
            TextView mTvPoster = view.findViewById(R.id.tvPoster);
            imageView.setImageResource(mIcons[position]);
            mTvTitle.setText(mTitles[position]);
            mTvPoster.setText(mPosters[position]);
        }
        return view;
    }
}
