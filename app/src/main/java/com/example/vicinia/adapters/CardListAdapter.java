package com.example.vicinia.adapters;

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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.vicinia.MainActivity;
import com.example.vicinia.R;
import com.example.vicinia.models.Place;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.rgb;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    private static final String TAG = "VICINIA/CardListAdapter";

    //list of places
    private MainActivity parent;
    private List<Place> places;

    CardListAdapter(List<Place> places, MainActivity activity) {
        parent = activity; this.places = places;
    }

    /**
     * get count of chat messages
     *
     * @return number of messages in {@link #places}
     */
    @Override
    public int getItemCount() {
        return places.size();
    }

    /**
     * inflates the cards
     *
     * @param viewGroup the left_message.xml
     * @param i         position in {@link #places}
     * @return created ViewHolder
     */
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_message, viewGroup, false);
        return new CardViewHolder(v);
    }

    /**
     * *binds* the data to a card
     *
     * @param cardViewHolder an instance of {@link CardViewHolder}
     * @param i              position in {@link #places}
     */
    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        Log.v(TAG, "onBind " + i);

        Place place = places.get(i);
        String name = place.getName();
        String distance = place.getDistance();
        float rating = place.getRating();
        final String placeID = place.getId();

        cardViewHolder.placeName.setText(Html.fromHtml("<b>" + name + "<\\b>"));
        cardViewHolder.placeDistance.setText(distance);
        cardViewHolder.placeRating.setText(String.valueOf(rating));
        cardViewHolder.placeRatingImg.setRating(rating);

        Picasso.with(cardViewHolder.cardView.getContext())
                .load("https://d1.awsstatic.com/icons/benefit-icons/100x100_benefit_dashboard.7a0b930f2712387591b2d365765d0e49c9074705.png")
                .resize(50, 50)
                .centerCrop()
                .into(cardViewHolder.placeImg);

        //configure button
        cardViewHolder.placeID.setText(R.string.get_details_string);
        cardViewHolder.placeID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                parent.onGetDetailsButton(placeID);
            }
        });

        Log.v(TAG, name + " " + distance + " " + rating + " " + placeID);
    }

    /**
     * callback on RecyclerView.setAdapter(this)
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * the individual card that will be inserted in the recycler view
     */
    static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView placeName;
        TextView placeDistance;
        TextView placeRating;
        RatingBar placeRatingImg;
        Button placeID;

        ImageView placeImg;
        CardViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
            placeName = itemView.findViewById(R.id.place_name);
            placeDistance = itemView.findViewById(R.id.place_distance);
            placeRating = itemView.findViewById(R.id.place_rating);
            placeRatingImg = itemView.findViewById(R.id.place_rating_img);
            placeID = itemView.findViewById(R.id.place_id);
            placeImg = itemView.findViewById(R.id.place_image);

            //set star color to gold
            LayerDrawable stars = (LayerDrawable) placeRatingImg.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(rgb(218, 163, 22), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(rgb(218, 163, 22), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(rgb(218, 163, 22), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
