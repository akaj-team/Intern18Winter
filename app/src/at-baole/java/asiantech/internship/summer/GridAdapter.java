package asiantech.internship.summer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    private final String[] mTitle;
    private final String[] mPoster;
    private final int[] mIcons;
    private Context mContext;
    private View mView;
    private LayoutInflater mLayoutInflater;

    public GridAdapter(Context context, String[] title, String[] poster, int[] icon) {
        this.mContext = context;
        this.mTitle = title;
        this.mPoster = poster;
        this.mIcons = icon;
    }

    @Override
    public int getCount() {
        return mTitle.length;
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
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            mView = new View(mContext);
            mView = mLayoutInflater.inflate(R.layout.activity_grid_adapter, null);
            ImageView imageView = mView.findViewById(R.id.imgPublisher);
            TextView textView1 = mView.findViewById(R.id.tvTitle);
            TextView textView2 = mView.findViewById(R.id.tvPoster);
            imageView.setImageResource(mIcons[position]);
            textView1.setText(mTitle[position]);
            textView2.setText(mPoster[position]);
        }
        return mView;
    }
}
