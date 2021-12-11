package com.example.internproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RemoteAdapter extends RecyclerView.Adapter<RemoteAdapter.RemoteViewHolder>{


    List<Data> list;
    Context context;

    public RemoteAdapter(List<Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RemoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.loaditem,parent,false);

        return new RemoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemoteViewHolder holder, int position) {
        holder.email.setText(list.get(holder.getAdapterPosition()).getEmail());
        holder.name.setText(list.get(holder.getAdapterPosition()).getFirst_name()+" "+list.get(holder.getAdapterPosition()).getLast_name());
        holder.id.setText(String.valueOf(list.get(holder.getAdapterPosition()).getId()));
        Glide.with(context).load(list.get(holder.getAdapterPosition()).getAvatar()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RemoteViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView name,id,email;
        public RemoteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.profile_image);
            name=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.email);
            id=itemView.findViewById(R.id.id);
        }
    }
}
