package com.example.android.googlebookfinder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);
        }


        Book currentBookItem = getItem(position);



        TextView defaultTitle = (TextView) listItemView.findViewById(R.id.book_title);
        defaultTitle.setText(currentBookItem.getTitle());
        defaultTitle.setTextColor(Color.parseColor("#621D1D"));

        TextView defaultAuthor = (TextView) listItemView.findViewById(R.id.book_author);
        defaultAuthor.setText(currentBookItem.getAuthors());
        defaultAuthor.setTextColor(Color.parseColor("#4d0000"));

        TextView defaultPublicationDate = (TextView) listItemView.findViewById(R.id.book_publication_date);
        defaultPublicationDate.setText("Published: " + currentBookItem.getPublishedDate());
        defaultPublicationDate.setTextColor(Color.parseColor("#4d0000"));


        ImageView defaultImageView = (ImageView) listItemView.findViewById(R.id.default_icon);
       defaultImageView.setImageResource(R.drawable.search_books);
       //defaultImageView.set


        return listItemView;
    }
}
