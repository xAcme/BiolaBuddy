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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Restaurants_Main extends AppCompatActivity {

    List<String> Restaraunts = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants__main);

    ////////////creates and formats the listView
        final String[] restaurantsArray = new String[] {
              /*  "Eagles Nest",
                "Common Grounds",
                "Heritage Cafe",
                "Talon",
                "Caf",
                "Blackstone Cafe",
                "Coffee Cart"*/
        };
        Integer[] imageId = {
                R.drawable.eaglesnest,
                R.drawable.commongrounds,
                R.drawable.heritagecafe,
                R.drawable.talon,
                R.drawable.caf,
                R.drawable.blackstonecafe,
                R.drawable.coffeecart
        };
        final ListView restaurantListView = (ListView) findViewById(R.id.RestaurantsListView);
        final List<String> myList = new ArrayList<String>(Arrays.asList(restaurantsArray));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, myList) {
            @Override public View getView(int pos, View convertView, ViewGroup parent) {
                View view = super.getView(pos, convertView,parent);
                ViewGroup.LayoutParams parameters = view.getLayoutParams();
                parameters.height = 400;
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                view.setLayoutParams(parameters);
                text.setTextSize(30);
                return  view;
            }
        };
        restaurantListView.setAdapter(arrayAdapter);
////////////////////////////////////////////////////////////////


        /////Switch between tabs

        BottomNavigationView tabBar = (BottomNavigationView) findViewById(R.id.tabBar);
        disableTabBarShift(tabBar);
        tabBar.setSelectedItemId(R.id.restaurantsicon);
        tabBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.chimesicon : ///////Go to chimes page
                       Intent intent1 = new Intent(Restaurants_Main.this,
                            Chime_Main.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        return true;
                    case R.id.mapicon :
                        Intent intent2 = new Intent(Restaurants_Main.this,
                                com.example.derekjames.uiproject.Map.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
                        return true;
                    case R.id.buildinghoursicon :
                        Intent intent3 = new Intent(Restaurants_Main.this,
                                com.example.derekjames.uiproject.Hours.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent3);
                        return true;
                    case R.id.restaurantsicon :
                        Intent intent4 = new Intent(Restaurants_Main.this,
                                com.example.derekjames.uiproject.Restaurants_Main.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent4);
                        return true;
                        
                }
                return true;
            }
        });
        ////////////////////////////////////////


        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Log.d("Position", String.valueOf(position));
              String restaurant = Restaraunts.get(position);
                Intent intent = new Intent(Restaurants_Main.this,
                        com.example.derekjames.uiproject.Restaurants_Food_Menus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("RestarauntName", restaurant);
                intent.putExtra("position", position);
                startActivity(intent);
          }
        };
        restaurantListView.setOnItemClickListener(itemClickListener);

        try {
            Void ab = new jsoupconnect().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < Restaraunts.size(); i++) {
            Log.d("", Restaraunts.get(i));
        }
        ///setting the list view
        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, Restaraunts) {
            @Override public View getView(int pos, View convertView, ViewGroup parent) {
                View view = super.getView(pos, convertView,parent);
                ViewGroup.LayoutParams parameters = view.getLayoutParams();
                parameters.height = 400;
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                view.setLayoutParams(parameters);
                text.setTextSize(30);
                return  view;
            }
        };
        restaurantListView.setAdapter(arrayAdapter2);
    }

    public class jsoupconnect extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            org.jsoup.nodes.Document doc = null;
            String url = "https://www.biola.edu/dining-services/locations";

            //String x = "";
            int count = 0;
            try {
                doc = Jsoup.connect(url).get();
                for (org.jsoup.nodes.Element row: doc.select("div.col-sm-12 div"))
                {
                       //x = String.valueOf(row.select("> h3").text());
                       if (row.select("> h3").text().equals("")) {
                           continue;
                       } else {
                           //Restaraunts.add(row.select("> .col-md-4:nth-of-type(1) > h3").text());
                           //x = row.select(" > h3").text();
                           //Log.d("h3: ", x);
                           Restaraunts.add(row.select("> h3").text());
                           count++;
                       }
                }

            }catch (IOException e) {

            }
            Log.d("size", String.valueOf(Restaraunts.size()));





            return null;
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


}
