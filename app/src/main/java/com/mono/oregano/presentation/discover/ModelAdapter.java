package com.mono.oregano.presentation.discover;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mono.oregano.R;
import com.mono.oregano.data.model.Model;

import java.util.ArrayList;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelHolder> {

    private ArrayList<? extends Model> model = new ArrayList<>();
    @NonNull
    @Override
    public ModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_view_posts, parent, false);
        return  new ModelHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelHolder holder, int position) {

        //TODO: replace with proper variables
        Model current = model.get(position);
        holder.name.setText(current.getObjectName());
        holder.desc.setText(current.getModelName());
        holder.profileImg.setImageURI(Uri.parse(current.getObjectID()));
        holder.subtitle.setText(current.getObjectName());
        holder.status.setText(current.getModelName());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public void setPosts(ArrayList<? extends Model> posts) {
        this.model = model;
    }

    class ModelHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView desc;
        TextView status;
        TextView subtitle;
        TextView count;
        ImageButton profileImg;

        public ModelHolder(@NonNull View itemView){
            super(itemView);
            this.name = itemView.findViewById(R.id.author_name);
        }
    }

}
