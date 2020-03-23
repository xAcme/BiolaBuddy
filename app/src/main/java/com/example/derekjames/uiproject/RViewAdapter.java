package com.example.derekjames.uiproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.DataObjectHolder> {

    private Context context;
    private ArrayList<Chime_cardview> ListaCard;
    public int cardPosition;

    public RViewAdapter(Context context, ArrayList<Chime_cardview> listaCard) {
        this.context = context;
        ListaCard = listaCard;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_view,parent,false);
        return  new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, final int position) {

        holder.title.setText(ListaCard.get(position).getTitle());

        if(!ListaCard.get(position).getImg().isEmpty())
        Picasso.get().load(ListaCard.get(position).getImg()).into(holder.img);

        cardPosition = position;

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),ListaCard.get(position).getHref(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,Chime_Full_Article.class);
                intent.putExtra("url",ListaCard.get(position).getHref());
                context.startActivity(intent);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),ListaCard.get(position).getHref(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,Chime_Full_Article.class);
                intent.putExtra("url",ListaCard.get(position).getHref());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ListaCard.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
    {
        private ImageView img;
        private TextView title;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.cardImg);
            this.title = itemView.findViewById(R.id.cardTitle);
        }
    }
}
