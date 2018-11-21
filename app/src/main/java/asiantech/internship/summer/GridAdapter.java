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
    private final String [] values;
    private final int [] images;
    private final String [] values1;
    View view;
    private LayoutInflater layoutInflater;

    public GridAdapter(Context context, String[] values, int[] images, String[] values1) {
        this.context = context;
        this.values = values;
        this.images = images;
        this.values1 = values1;
    }

    @Override
    public int getCount() {
        return values.length;
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

            textView.setText(values[position]);
            circleImageView.setImageResource(images[position]);
            textView1.setText(values1[position]);
        }
        return view;
    }
}

