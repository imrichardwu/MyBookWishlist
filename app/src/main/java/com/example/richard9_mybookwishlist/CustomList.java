package com.example.richard9_mybookwishlist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// CustomList is a custom ArrayAdapter that will be used to populate the ListView in the MainActivity
public class CustomList extends ArrayAdapter<Wishlist> {
    private Context context;
    private ArrayList<Wishlist> list;

    // Constructor for the CustomList class that takes a context and a list of Wishlist items
    public CustomList(Context context, ArrayList<Wishlist> list) {
        super(context, 0, list);  // 0 is the resource id for row layout, which will be set in getView()
        this.context = context;
        this.list = list;
    }

    // getView() is called for each item in the list and returns the view for that item
    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }

        Wishlist currentItem = list.get(position);

        // Find and set the views
        TextView title = convertView.findViewById(R.id.book_title);
        TextView author = convertView.findViewById(R.id.author_name);
        TextView genre = convertView.findViewById(R.id.genre);
        TextView publicationYear = convertView.findViewById(R.id.publication_year);
        TextView status = convertView.findViewById(R.id.status);

        // Populate the views with data from the Wishlist item
        title.setText(currentItem.getTitle());
        author.setText(currentItem.getAuthor());
        genre.setText(currentItem.getGenre());
        publicationYear.setText(String.valueOf(currentItem.getPublicationYear())); // Assuming it's an integer
        status.setText(currentItem.getStatus());

        return convertView;
    }
}