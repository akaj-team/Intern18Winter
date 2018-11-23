package asiantech.internship.summer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private final String [] mValues;
    private final int [] mImages;
    private final String [] mValues1;
    private View mView;
    private LayoutInflater mLayoutInflater;

    public GridAdapter(Context mContext, String[] mValues, int[] mImages, String[] mValues1) {
        this.mContext = mContext;
        this.mValues = mValues;
        this.mImages = mImages;
        this.mValues1 = mValues1;
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
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            mView = mLayoutInflater.inflate(R.layout.single_item, null);
            CircleImageView circleImageView = mView.findViewById(R.id.image);
            TextView textView = mView.findViewById(R.id.tv);
            TextView textView1 = mView.findViewById(R.id.tv1);

            textView.setText(mValues[position]);
            circleImageView.setImageResource(mImages[position]);
            textView1.setText(mValues1[position]);
        }
        return mView;
    }
}
