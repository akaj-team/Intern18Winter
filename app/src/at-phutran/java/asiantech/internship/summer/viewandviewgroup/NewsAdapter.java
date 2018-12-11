package asiantech.internship.summer.viewandviewgroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.News;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends BaseAdapter {

    private List<News> mListNews;
    private Context mContext;
    private View mView;

    NewsAdapter(Context mContext, List<News> listNews) {
        this.mContext = mContext;
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
        LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            mView = mLayoutInflater.inflate(R.layout.single_item, null);
            CircleImageView circleImageView = mView.findViewById(R.id.circleImage);
            TextView tvContent = mView.findViewById(R.id.tvContent);
            TextView tvTitle = mView.findViewById(R.id.tvTitle);
            tvContent.setText(mDataNews.getContent());
            circleImageView.setImageResource(mDataNews.getImage());
            tvTitle.setText(mDataNews.getTitle());
        }
        return mView;
    }
}
