package asiantech.internship.summer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private final String [] mValues;
    private final int [] mImages;
    private final String [] mValues1;
    View view;
    private LayoutInflater layoutInflater;


    public GridAdapter(Context context, String[] mValues, int[] mImages, String[] mValues1) {
        this.context = context;
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
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            view = layoutInflater.inflate(R.layout.single_item, null);
            CircleImageView circleImageView = view.findViewById(R.id.image);
            TextView textView = view.findViewById(R.id.tv);
            TextView textView1 = view.findViewById(R.id.tv1);

            textView.setText(mValues[position]);
            circleImageView.setImageResource(mImages[position]);
            textView1.setText(mValues1[position]);
        }
        return view;
    }
}

