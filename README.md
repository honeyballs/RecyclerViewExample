# RecyclerViewExample

This is a small app to showcase how to use the RecyclerView in Android.

## Gradle

To use the RecyclerView it has to be included in the `build.gradle` dependencies at app level:

````groovy
implementation "com.android.support:recyclerview-v7:<VERSION>"
````

The `<VERSION>` has to match the `targetSdkVersion` defined in the `build.gradle` file. Here is the complete file:

````groovy
apply plugin: 'com.android.application'

android {
    compileSdkVersion 27
    defaultConfig {
        applicationId "de.thm.recyclerviewexample"
        minSdkVersion 15
        targetSdkVersion 27
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            postprocessing {
                removeUnusedCode false
                removeUnusedResources false
                obfuscate false
                optimizeCode false
                proguardFile 'proguard-rules.pro'
            }
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:27.1.1'
    implementation "com.android.support:recyclerview-v7:27.1.1"
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.1'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.1'
}
````

You can find a list of available versions [here](https://developer.android.com/topic/libraries/support-library/revisions.html).

## Layout

Next we have to define the RecyclerView in the activity layout:

````xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <android.support.v7.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/recylcerView"
        android:scrollbars="vertical">

    </android.support.v7.widget.RecyclerView>

</LinearLayout>
````

`activity_layout.xml`

Additionally we define how the individual list items will look:

````xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <TextView
        android:id="@+id/itemTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp" />

    <Button
        android:id="@+id/itemButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="end"
        android:layout_marginBottom="10dp"
        android:layout_marginEnd="10dp"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:layout_marginStart="10dp"
        android:text="Click me!" />

</LinearLayout>
````

`list_item.xml`

## Adapter

The adapter defines how the data is set into the list item views.

````java
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
````

A ViewHolder `MyViewHolder` is defined as an inner class. This is used to hold a reference to views so they can be reused. The adapter uses it as a type.

In the constructor the list of items we want to displayed is passed to the adapter.

`onAttachedToRecyclerView` is used to receive a reference to the RecyclerView for the listeners.

`onCreateViewHolder` inflates the list item layout and creates a ViewHolder to cache the created view.

`onBindViewHolder` binds the data from the ArrayList to our ViewHolder.

The `ItemListener` listens for clicks on a list item. The position of the clicked item is calculated using the RecyclerView.

The `ItemButtonListener` listens for clicks on buttons contained in a list item. Since the RecyclerView can only calculate the position of direct children we have to navigate the view tree recursively upwards.

## Configuration

The RecyclerView is configured in the `MainActivity.java`.

````java
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<String> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the Layout of the Activity
        setContentView(R.layout.activity_layout);

        recyclerView = findViewById(R.id.recylcerView);

        // Set fixed size if the list won`t have to resize which increases performance
        recyclerView.setHasFixedSize(true);

        // Set a LayoutManager for the RecyclerView.
        // The LayoutManager will call the corresponding Adapter Methods
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Add a Seperator line between the list items
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initItems();

        // After the items have been initialized we create an Adapter and set the items
        Adapter adapter = new Adapter(items);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Initialize 200 String items
     */
    private void initItems() {
        items = new ArrayList<>();
        for (int i = 1; i <= 200; i++) {
            items.add("This is item no. " + i);
        }
    }
}
````



