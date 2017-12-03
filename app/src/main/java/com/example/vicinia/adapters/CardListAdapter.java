package com.example.vicinia.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.vicinia.MainActivity;
import com.example.vicinia.R;
import com.example.vicinia.services.GpsServices;

import java.util.List;

import static android.graphics.Color.rgb;
import static com.example.vicinia.services.ChatMessageServices.getDetails;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    static final String TAG = "VICINIA/CardListAdapter";

    List<String[]> places;

    public CardListAdapter(List<String[]> places) {
        this.places = places;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_message, viewGroup, false);
        CardViewHolder cardViewHolder = new CardViewHolder(v);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        Log.v(TAG, "onBind "+i);

        String[] place = places.get(i);
        cardViewHolder.placeName.setText(Html.fromHtml("<b>"+place[0]+"<\\b>"));
        cardViewHolder.placeDistance.setText(place[1]);
        cardViewHolder.placeRating.setText(place[2]);
        cardViewHolder.placeRatingImg.setRating((int) Double.parseDouble(place[2]));

        cardViewHolder.placeID.setText("Get Details");
        cardViewHolder.placeID.setTag(place[3]);

        cardViewHolder.placeID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button btn = (Button) v;
                String placeID = (String) btn.getTag();

                GpsServices gps = MainActivity.getInstance().gpsServices;
                double lat = gps.getLatitude();
                double lng = gps.getLongitude();

                getDetails(placeID, lat, lng);
            }
        });

        Log.v(TAG, place[0]+" "+place[1]+" "+place[2]+" "+place[3]);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView placeName;
        TextView placeDistance;
        TextView placeRating;
        RatingBar placeRatingImg;
        Button placeID;

        CardViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            placeName = itemView.findViewById(R.id.place_name);
            placeDistance = itemView.findViewById(R.id.place_distance);
            placeRating = itemView.findViewById(R.id.place_rating);
            placeRatingImg = itemView.findViewById(R.id.place_rating_img);
            placeID = itemView.findViewById(R.id.place_id);

            //set star color to gold
            LayerDrawable stars = (LayerDrawable) placeRatingImg.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(rgb(218, 163, 22), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(rgb(218, 163, 22), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(rgb(218, 163, 22), PorterDuff.Mode.SRC_ATOP);
        }
    }

}
