package asiantech.internship.summer.viewandviewgroup;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.News;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends BaseAdapter {

    private List<News> mListNews;

    NewsAdapter(List<News> listNews) {
        this.mListNews = listNews;
    }

    @Override
    public int getCount() {
        return mListNews.size();
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
        News mDataNews = mListNews.get(position);
        View view = convertView;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
            CircleImageView circleImageView = view.findViewById(R.id.circleImage);
            TextView tvContent = view.findViewById(R.id.tvContent);
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            LinearLayout llContent = view.findViewById(R.id.llItem);
            tvContent.setText(mDataNews.getContent());
            circleImageView.setImageResource(mDataNews.getImage());
            tvTitle.setText(mDataNews.getTitle());
            llContent.setBackgroundColor(Color.parseColor(mDataNews.getColor()));
        }
        return view;
    }
}
