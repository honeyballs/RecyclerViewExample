package de.thm.recyclerviewexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Yannick Bals on 19.04.2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<String> items;
    private RecyclerView recyclerView;

    public Adapter(ArrayList<String> items) {
        this.items = items;
    }

    // Use the onAttachedToRecyclerView Method to get a reference to the RecyclerView
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    // Create the ViewHolder, called by the LayoutManager
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        // Assign the Listeners
        v.setOnClickListener(new ItemListener());
        MyViewHolder holder = new MyViewHolder(v);
        holder.itemButton.setOnClickListener(new ItemButtonListener());
        return holder;

    }

    // Bind the String item to the TextView of the ViewHolder, called by the LayoutManager
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemTextView.setText(items.get(position));
    }

    // Calculate the size of the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Our ViewHolder which contains a TextView and a Button.
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTextView;
        public Button itemButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemTextView = itemView.findViewById(R.id.itemTextView);
            this.itemButton = itemView.findViewById(R.id.itemButton);
        }
    }

    /**
     * Listener for the list items.
     */
    class ItemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int position = recyclerView.getChildLayoutPosition(v);
            Toast.makeText(v.getContext(), items.get(position) + " item clicked", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Listener for the Buttons within the list items.
     */
    class ItemButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            View view = v;
            View parent = (View) view.getParent();

            // To use the RecyclerView to find the position of the items we need to pass the direct child of the RecyclerView.
            // To get to the direct child we navigate up the View tree until we find the last element which isn't the RecyclerView itself.
            while (!(parent instanceof RecyclerView)) {
                view = parent;
                parent = (View) view.getParent();
            }

            int position = recyclerView.getChildAdapterPosition(view);
            Toast.makeText(view.getContext(), items.get(position) + " Button clicked", Toast.LENGTH_SHORT).show();

        }
    }
}
