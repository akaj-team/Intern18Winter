package asiantech.internship.summer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mValues;
    private String[] mValues1;
    private int[] mImages;
    private View mView;

    NewsAdapter(Context mContext, String[] mValues, String[] mValues1, int[] mImages) {
        this.mContext = mContext;
        this.mValues = mValues;
        this.mValues1 = mValues1;
        this.mImages = mImages;
    }

    @Override
    public int getCount() {
        return mValues.length;
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
        if (convertView == null) {
            mView = new View(mContext);
            mView = layoutInflater.inflate(R.layout.single_item, parent, false);
            ImageView imgIcon = mView.findViewById(R.id.imgIcon);
            TextView tvDecription = mView.findViewById(R.id.tv);
            TextView tvTitle = mView.findViewById(R.id.tv1);
            imgIcon.setImageResource(mImages[position]);
            tvDecription.setText(mValues[position]);
            tvTitle.setText(mValues1[position]);
        }
        return mView;
    }
}
