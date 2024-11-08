package com.example.richard9_mybookwishlist;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        AddWishlistFragment.OnFragmentInteractionListener,
        EditWishlistFragment.OnFragmentInteractionListener {

    private ListView wishlist;
    private ArrayAdapter<Wishlist> wishlistAdapter;
    private ArrayList<Wishlist> wishlistData;
    private TextView totalBooksTextView;
    private TextView readBooksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        wishlist = findViewById(R.id.wishlist);
        totalBooksTextView = findViewById(R.id.total_books);
        readBooksTextView = findViewById(R.id.read_books);


        wishlistData = new ArrayList<>();


        // Set up the adapter
        wishlistAdapter = new CustomList(this, wishlistData);
        wishlist.setAdapter(wishlistAdapter);

        // Update book counts initially
        updateBookCounts();

        // Set up Floating Action Button to add new wishlist items
        final FloatingActionButton addWishlist = findViewById(R.id.add_wishlist);
        addWishlist.setOnClickListener(v -> {
            AddWishlistFragment addWishlistFragment = new AddWishlistFragment();
            addWishlistFragment.show(getSupportFragmentManager(), "addWishlist");
        });

        // Set up ListView item click listener to edit wishlist items
        wishlist.setOnItemClickListener((parent, view, position, id) -> {
            EditWishlistFragment editWishlistFragment = EditWishlistFragment.newInstance(wishlistData.get(position), position);
            editWishlistFragment.show(getSupportFragmentManager(), "editWishlist");
        });
    }

    // Method to update the total number of books and read books
    private void updateBookCounts() {
        int totalBooks = wishlistData.size();
        int readBooks = 0;

        for (Wishlist book : wishlistData) {
            if ("Read".equalsIgnoreCase(book.getStatus())) {
                readBooks++;
            }
        }

        // Update the TextViews
        totalBooksTextView.setText("Total Books: " + totalBooks);
        readBooksTextView.setText("Read Books: " + readBooks);
    }

    // Handle adding a new wishlist item
    @Override
    public void onOkPressed(Wishlist newWishlist) {
        wishlistData.add(newWishlist);
        wishlistAdapter.notifyDataSetChanged();
        updateBookCounts(); // Update the counts after adding
    }

    // Handle editing a wishlist item
    @Override
    public void onEditPressed(Wishlist updatedWishlist, int position) {
        wishlistData.set(position, updatedWishlist);
        wishlistAdapter.notifyDataSetChanged();
        updateBookCounts(); // Update the counts after editing
    }

    // Handle deleting a wishlist item
    @Override
    public void onDeletePressed(int position) {
        wishlistData.remove(position);
        wishlistAdapter.notifyDataSetChanged();
        updateBookCounts(); // Update the counts after deletion
    }
}