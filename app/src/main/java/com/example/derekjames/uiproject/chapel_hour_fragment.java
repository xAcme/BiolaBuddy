package com.example.derekjames.uiproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class chapel_hour_fragment extends Fragment {

    final int MAX_CHAPEL_NUM = 20;
    static int chapelCount = 0;


    private class chapelInfo
    {
        String date;
        String title;
        String speaker;
        String location;

        chapelInfo(String date, String title, String speaker, String location)
        {
            this.date = date;
            this.title = title;
            this.speaker = speaker;
            this.location = location;
        }
    }chapelInfo [] chapel = new chapelInfo[MAX_CHAPEL_NUM];



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chapel_hour_fragment,container,false);
        chapelCount = 0;


        try {
            Void ab = new Connection().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] restaurantsArray = new String[chapelCount];
        String buf = "";
        for(int i = 0; i<chapelCount; i++)
        {

            buf="Date: ";
            buf+=chapel[i].date+="\n";
            buf+="\n";
            buf+="Title: ";
            buf+=chapel[i].title+="\n";
            buf+="\n";
            buf+="Speaker: ";
            buf+=chapel[i].speaker+="\n";
            buf+="\n";
            buf+="Location: ";
            buf+=chapel[i].location;
            restaurantsArray[i]=buf;

        }

        ListView listView = view.findViewById(R.id.chapelHour_listView);



        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                restaurantsArray
        );

        listView.setAdapter(listViewAdapter);

        return view;
    }


    public class Connection extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            org.jsoup.nodes.Document doc = null;

            try {
                doc = Jsoup.connect("https://www.biola.edu/chapel").get();


                for(org.jsoup.nodes.Element row: doc.select(
                        "ul.chapel-list.active li"))
                {

                    if(row.select("li:nth-of-type(1).item-body.title").text().equals(" ")){
                        continue;
                    }
                    else
                    {

                        chapel[chapelCount] = new chapelInfo
                                (
                                row.select("> .datetime").text(),
                                row.select("> .item-body > .title").text(),
                                row.select("> .item-body > .subtitle").text(),
                                row.select("> .item-body > .location").text()
                                );



                        chapelCount++;

                    }
                            }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}