package asiantech.internship.summer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    Context context;
    private final String [] values;
    private final String [] values1;
    private final int [] images;
    private View view;

    GridAdapter(Context context, String[] values, String[] values1, int[] images) {
        this.context = context;
        this.values = values;
        this.values1 = values1;
        this.images = images;
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            view = new View(context);
            view = layoutInflater.inflate(R.layout.single_item, parent,false);
            ImageView imageView = view.findViewById(R.id.imageView);
            TextView textView = view.findViewById(R.id.textView);
            TextView textView1 = view.findViewById(R.id.textView1);
            imageView.setImageResource(images[position]);
            textView.setText(values[position]);
            textView1.setText(values1[position]);
        }
        return view;
    }
}
