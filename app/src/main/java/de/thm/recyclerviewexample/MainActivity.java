package de.thm.recyclerviewexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Yannick Bals on 19.04.2018.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout);
        recyclerView = findViewById(R.id.recylcerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initItems();
    }

    private void initItems() {
        items = new ArrayList<>();
        for (int i = 1; i <= 200; i++) {
            items.add("This is item no. " + i);
        }
    }
}
