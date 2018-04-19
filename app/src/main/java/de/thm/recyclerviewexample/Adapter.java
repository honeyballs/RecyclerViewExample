package de.thm.recyclerviewexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yannick Bals on 19.04.2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<String> items;

    public Adapter(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemTextView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTextView;
        public Button itemButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemTextView = itemView.findViewById(R.id.itemTextView);
            this.itemButton = itemView.findViewById(R.id.itemButton);
        }
    }
}
