package com.example.richard9_mybookwishlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class EditWishlistFragment extends DialogFragment {
    private EditText title;
    private EditText author;
    private EditText genre;
    private EditText publicationYear;
    private EditText status;
    private OnFragmentInteractionListener listener;
    private int position;

    private boolean isEditing = false; // Flag to track editing state

    public interface OnFragmentInteractionListener {
        void onEditPressed(Wishlist updatedWishlist, int position); // Handle saving edits
        void onDeletePressed(int position); // Handle deletion
    }

    // newInstance() method to create a new instance of EditWishlistFragment with arguments
    public static EditWishlistFragment newInstance(Wishlist wishlist, int position) {
        EditWishlistFragment fragment = new EditWishlistFragment();
        Bundle args = new Bundle();
        args.putString("title", wishlist.getTitle());
        args.putString("author", wishlist.getAuthor());
        args.putString("genre", wishlist.getGenre());
        args.putString("publicationYear", wishlist.getPublicationYear());
        args.putString("status", wishlist.getStatus());
        args.putInt("position", position); // Pass the position of the item
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = requireActivity().getLayoutInflater().inflate(R.layout.add_wishlist_fragment, null);
        title = view.findViewById(R.id.title_editText);
        author = view.findViewById(R.id.author_editText);
        genre = view.findViewById(R.id.genre_editText);
        publicationYear = view.findViewById(R.id.publication_year_editText);
        status = view.findViewById(R.id.status_editText);

        // Retrieve data from arguments if available
        if (getArguments() != null) {
            title.setText(getArguments().getString("title"));
            author.setText(getArguments().getString("author"));
            genre.setText(getArguments().getString("genre"));
            publicationYear.setText(getArguments().getString("publicationYear"));
            status.setText(getArguments().getString("status"));
            position = getArguments().getInt("position"); // Get the position from arguments
        }

        // Initially set fields to non-editable
        setFieldsEditable(false);

        // Create the dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Set up the dialog
        AlertDialog dialog = builder
                .setView(view)
                .setTitle("Book Details")
                .setNegativeButton("Close", null)
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        listener.onDeletePressed(position);
                        dialogInterface.dismiss(); // Dismiss the dialog after deletion
                    }
                })
                .setPositiveButton("Edit", null) // We'll override this later
                .create();

        // Override the positive button to handle "Edit" and "Save" actions
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isEditing) {
                            // User clicked "Edit"; enable editing
                            isEditing = true;
                            setFieldsEditable(true);
                            positiveButton.setText("Save");
                        } else {
                            // User clicked "Save"; validate and save changes
                            // Retrieve and trim input from EditTexts
                            String updatedTitle = title.getText().toString().trim();
                            String updatedAuthor = author.getText().toString().trim();
                            String updatedGenre = genre.getText().toString().trim();
                            String updatedPublicationYear = publicationYear.getText().toString().trim();
                            String updatedStatus = status.getText().toString().trim();

                            boolean isValid = true;

                            // Validate title length
                            if (!updatedTitle.isEmpty() && updatedTitle.length() > 50) {
                                title.setError("Title must be less than 50 characters");
                                isValid = false;
                            }

                            // Validate author length
                            if (!updatedAuthor.isEmpty() && updatedAuthor.length() > 30) {
                                author.setError("Author must be less than 30 characters");
                                isValid = false;
                            }

                            // Validate publication year
                            if (!updatedPublicationYear.isEmpty()) {
                                if (updatedPublicationYear.length() != 4 || !updatedPublicationYear.matches("\\d{4}")) {
                                    publicationYear.setError("Publication year must be exactly 4 digits");
                                    isValid = false;
                                }
                            }

                            // Validate status
                            if (!updatedStatus.equalsIgnoreCase("Unread") && !updatedStatus.equalsIgnoreCase("Read")) {
                                status.setError("Status must be either 'Unread' or 'Read'");
                                isValid = false;
                            }

                            // If all validations pass, proceed and notify listener
                            if (isValid) {
                                Wishlist updatedWishlist = new Wishlist(updatedTitle, updatedAuthor, updatedGenre, updatedPublicationYear, updatedStatus);
                                listener.onEditPressed(updatedWishlist, position);
                                dialog.dismiss(); // Dismiss the dialog after successful validation
                            }
                            // If validation fails, the dialog remains open and errors are shown
                        }
                    }
                });
            }
        });

        return dialog;
    }

    // Helper method to set fields editable or non-editable
    private void setFieldsEditable(boolean editable) {
        title.setFocusable(editable);
        title.setFocusableInTouchMode(editable);
        title.setClickable(editable);

        author.setFocusable(editable);
        author.setFocusableInTouchMode(editable);
        author.setClickable(editable);

        genre.setFocusable(editable);
        genre.setFocusableInTouchMode(editable);
        genre.setClickable(editable);

        publicationYear.setFocusable(editable);
        publicationYear.setFocusableInTouchMode(editable);
        publicationYear.setClickable(editable);

        status.setFocusable(editable);
        status.setFocusableInTouchMode(editable);
        status.setClickable(editable);
    }
}
