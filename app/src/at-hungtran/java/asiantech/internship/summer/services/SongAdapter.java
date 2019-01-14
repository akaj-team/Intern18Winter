package asiantech.internship.summer.services;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Song;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> mSongs;
    private SongEventListener mListener;

    SongAdapter(List<Song> songs, SongEventListener listener) {
        mSongs = songs;
        mListener = listener;
    }

    @NonNull
    @Override
    public SongAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.SongViewHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.mTvTitle.setText(song.getTitle());
        holder.mTvSinger.setText(song.getSinger());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    interface SongEventListener {
        void songEventListener(int position);
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvTitle;
        private TextView mTvSinger;

        SongViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvSinger = itemView.findViewById(R.id.tvSinger);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.songEventListener(getLayoutPosition());
        }
    }
}
