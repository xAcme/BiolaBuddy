package com.example.derekjames.uiproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Restaurants_Food_Menus extends AppCompatActivity {


    List<String> menuCategory = new ArrayList<String>();
    String restaurantName = "";
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants__food__menus);

        menuCategory.clear();
        /////Switch between tabs
        BottomNavigationView tabBar = (BottomNavigationView) findViewById(R.id.tabBar);
        disableTabBarShift(tabBar);

        tabBar.setSelectedItemId(R.id.restaurantsicon);
        tabBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.chimesicon : ///////Go to chimes page
                        Intent intent1 = new Intent(Restaurants_Food_Menus.this,
                                Chime_Main.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        return true;
                    case R.id.mapicon :
                        Intent intent2 = new Intent(Restaurants_Food_Menus.this,
                                com.example.derekjames.uiproject.Map.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
                        return true;
                    case R.id.buildinghoursicon :
                        Intent intent3 = new Intent(Restaurants_Food_Menus.this,
                                com.example.derekjames.uiproject.Hours.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent3);
                        return true;
                    case R.id.restaurantsicon :
                        Intent intent4 = new Intent(Restaurants_Food_Menus.this,
                                com.example.derekjames.uiproject.Restaurants_Main.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent4);
                        return true;
                }
                return true;
            }
        });
        ////////////////////////////////////////

        Button fmbtn = findViewById(R.id.fmbutn);

        fmbtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Restaurants_Food_Menus.this,
                        com.example.derekjames.uiproject.Restaurants_Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


        Intent startIntent = getIntent();

        restaurantName = startIntent.getStringExtra("RestarauntName");
        if (restaurantName == "") {
            restaurantName = "No Name Found";
        }
        final int ItemClickedFirstPage = startIntent.getIntExtra("position", 0);

        //set url
        url = "";
        switch (ItemClickedFirstPage) {
            case 0 : //caf
                url = "Caf";

                break;
            case 1 : // eagles
                url = "https://www.tapingo.com/order/restaurant/eagles-nest-biola/";
                //return true;
                break;
            case 2 : //commons
                url = "https://www.tapingo.com/order/restaurant/common-grounds-biola/";
                break;
            case 3 : //coffee cart
                url = "Coffee Cart";
                break;
            case 4 : //talon
                url = "https://www.tapingo.com/order/restaurant/talon-biola/";
                break;
            case 5 :
                url = "https://www.tapingo.com/order/restaurant/blackstone-cafe-biola/";
                break;
            case 6 :
                url = "https://www.tapingo.com/order/restaurant/heritage-cafe/";
                break;
        }

        TextView restaurantNameTextView = (TextView) findViewById(R.id.RestaurantTitle);
        restaurantNameTextView.setText(restaurantName);

        ////////////////adds array into the listview
       /*String[] restaurantsArray = new String[] {
                "Menu Item 1: jskldfjskldajkiojs",
                "Menu Item 3: jskldfjskldajfksds",
                "Menu Item 4: jskldfjskldajfeiojs",
                "Menu Item 5: jskldfjskiojs",
                "Menu Item 6: jskldfjskojs",
                "Menu Item 7: jskldfjskldajfksiojs",
        };*/
        final ListView restaurantListView = (ListView) findViewById(R.id.RestaurantsMenuListView);
        //final List<String> myList = new ArrayList<String>(Arrays.asList(restaurantsArray));
       /* final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, myList) {
            @Override public View getView(int pos, View convertView, ViewGroup parent) {
                View view = super.getView(pos, convertView,parent);
                ViewGroup.LayoutParams parameters = view.getLayoutParams();
                parameters.height = 300;
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                view.setLayoutParams(parameters);
                text.setTextSize(20);
                return  view;
            }
        };*/
        //restaurantListView.setAdapter(arrayAdapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Position", String.valueOf(position));
                String type = menuCategory.get(position);
                Intent intent = new Intent(Restaurants_Food_Menus.this,
                        com.example.derekjames.uiproject.Restaurants_Food_Menus_2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("type", type);
                intent.putExtra("positionSecondpage", position);
                intent.putExtra("position", ItemClickedFirstPage);
                intent.putExtra("RestarauntName", restaurantName);
                intent.putExtra("url", url);

                startActivity(intent);
            }
        };
        restaurantListView.setOnItemClickListener(itemClickListener);

        /////////////////////////////////////////////////////////////////////////////////////////////

        if (url == "Caf"  || url == "Coffee Cart") {
           if (url == "Caf") {

               menuCategory.add("$6.15 - Cafe Breakfast");
               menuCategory.add("$7.79 - Cafe Lunch");
               menuCategory.add("$8.93 - Cafe Dinner");
               restaurantNameTextView.setText(restaurantName);
               restaurantListView.setEnabled(false);
           }
           else if (url == "Coffee Cart") {
               restaurantNameTextView.setText(restaurantName + "(No Menu)");
           }
        }
        else {
            try {
                Void ab = new jsoupconnect().execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            /*for (int i = 0; i < menuCategory.size(); i++) {
                Log.d("", menuCategory.get(i));
            }*/
            if (menuCategory.isEmpty() == true && url != "Coffee Cart") {
                restaurantNameTextView.setText("Sorry were closed.");

            } else {
                final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuCategory) {
                    @Override
                    public View getView(int pos, View convertView, ViewGroup parent) {
                        View view = super.getView(pos, convertView, parent);
                        ViewGroup.LayoutParams parameters = view.getLayoutParams();
                        parameters.height = 400;
                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                        view.setLayoutParams(parameters);
                        text.setTextSize(30);
                        return view;
                    }
                };
                restaurantListView.setAdapter(arrayAdapter2);
            }



    }




    @SuppressLint("RestrictedApi")
    private void  disableTabBarShift(BottomNavigationView view) {
        BottomNavigationMenuView tabBar = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = tabBar.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(tabBar, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < tabBar.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) tabBar.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        }catch (NoSuchFieldException e)
        {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        }catch (IllegalAccessException e)
        {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }



    public class jsoupconnect extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            org.jsoup.nodes.Document doc = null;
            //String url = "https://www.tapingo.com/order/restaurant/eagles-nest-biola/";

            String x = "", y;

            int count = 0;
            try {
                doc = Jsoup.connect(url).get();
                for (org.jsoup.nodes.Element row: doc.select("div.category_holder"))
                {
                    x = row.select("> div").text();
                    y = x.replaceAll("\\(.*\\)", "");
                    menuCategory.add(y);

                }
            }catch (IOException e) {

            }
           // Log.d("menuCategory.size()", String.valueOf(menuCategory.size()));





            return null;
        }
    }















































}
