package de.thm.recyclerviewexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Yannick Bals on 19.04.2018.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<String> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout);
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initItems();
        Adapter adapter = new Adapter(items);
        recyclerView.setAdapter(adapter);
    }

    private void initItems() {
        items = new ArrayList<>();
        for (int i = 1; i <= 200; i++) {
            items.add("This is item no. " + i);
        }
    }
}
