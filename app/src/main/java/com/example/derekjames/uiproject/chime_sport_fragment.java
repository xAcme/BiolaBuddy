package com.example.derekjames.uiproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class chime_sport_fragment extends Fragment {

    public ArrayList<Chime_cardview> arrayListCard = new ArrayList<>();
    private RecyclerView recyclerView;
    private RViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public int pages = 0;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_chime__sport__tab, container, false);

        inti(view);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void inti(View view) {
        this.recyclerView = view.findViewById(R.id.recycler_view);
        this.layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        this.adapter = new RViewAdapter(getActivity().getBaseContext(), getListaCard());

    }

    private ArrayList<Chime_cardview> getListaCard() {


        try {
            Void temp = new Connection().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return arrayListCard;

    }

    public class Connection extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            org.jsoup.nodes.Document doc = null;
            String urlAdd;

           /* while(pages != 20) {

            if(pages == 0)
                urlAdd="";
            else {
                urlAdd= String.format("page%s", pages);
            }
            */


                try {
                    doc = Jsoup.connect("https://chimesnewspaper.com/category/sports").get();
                    pages++;

                    for (org.jsoup.nodes.Element row : doc.select("div.sno-categoryview-6 div")) {

                        if (row.select("h2").text().equals("")) {
                            continue;
                        } else {


                            String img = row.select("img").attr("src");
                            String href = row.select("a").attr("href");
                            String title = row.select("h2").text();
                            String date = row.select("p").text();
                            String description = title + "\n";
                            description += "\n";
                            description += date;

                            int length = title.length();
                            if (length < 300)
                                arrayListCard.add(new Chime_cardview(img, description,href));

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            return null;
        }
    }
}
