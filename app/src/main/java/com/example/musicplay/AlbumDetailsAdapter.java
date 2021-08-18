package com.example.musicplay;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<MusicFiles> albumFiles;
    View view;

    public AlbumDetailsAdapter(Context mContext, ArrayList<MusicFiles> albumFiles) {
        this.mContext = mContext;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.music_items, parent, false);
        return  new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.aName.setText(albumFiles.get(position).getTitle());
        byte[] image = getAlbumArt(albumFiles.get(position).getPath());

        if(image != null)
        {
            Glide.with(mContext).asBitmap()
                    .load(image)
                    .into(holder.aCover);
        }
        else
        {
            Glide.with(mContext)
                    .load(R.drawable.m_icon)
                    .into(holder.aCover);
        }

    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView aCover;
        TextView aName;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            aCover = itemView.findViewById(R.id.music_img);
            aName = itemView.findViewById(R.id.music_file_name);
        }
    }

    private byte[] getAlbumArt(String uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
