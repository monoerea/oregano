package com.mono.oregano.presentation.home;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mono.oregano.R;
import com.mono.oregano.data.model.Posts;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private ArrayList<Posts> posts = new ArrayList<>();
    @NonNull
    @Override
    public PostAdapter.PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expandable_card_view, parent, false);
        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostHolder holder, int position) {
        Posts current = posts.get(position);
        holder.title.setText(current.getObjectName());
        holder.name.setText(current.getAuthor().getFullName());
        //TODO: make relative
        holder.time.setText(current.getDateCreated().toString());
        holder.paragraph.setText(current.getTextContent());
        //TODO: img loader
        holder.profilePic.setImageURI(Uri.parse(current.getAuthor().getProfileImg()));
        holder.media.setImageURI(Uri.parse(current.getImg()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(ArrayList<Posts> posts){
        this.posts = posts;
    }
    class PostHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView name;
        TextView time;
        TextView paragraph;
        ImageButton profilePic;
        ImageView media;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.post_title);
            name = itemView.findViewById(R.id.author_name);
            time = itemView.findViewById(R.id.date);
            paragraph = itemView.findViewById(R.id.para);
            profilePic = itemView.findViewById(R.id.profile_picture);
            media = itemView.findViewById(R.id.postImage);

        }
    }
}
