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

import java.util.Objects;

public class AddWishlistFragment extends DialogFragment {
    private EditText title;
    private EditText author;
    private EditText genre;
    private EditText publicationYear;
    private EditText status;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(Wishlist newWishlist);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener) {
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

        // Create the dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Set the view and buttons of the dialog builder
        AlertDialog dialog = builder
                .setView(view)
                .setTitle("Add Book to Wishlist")
                .setNegativeButton("Cancel", null) // Dismisses the dialog
                .setPositiveButton("OK", null) // We'll override this later
                .create();

        // Override the positive button to prevent automatic dismissal on validation failure
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrieve and trim input from EditTexts
                        String titleText = title.getText().toString().trim();
                        String authorText = author.getText().toString().trim();
                        String genreText = genre.getText().toString().trim();
                        String publicationYearText = publicationYear.getText().toString().trim();
                        String statusText = status.getText().toString().trim();

                        boolean isValid = true;

                        // Validate title length
                        if (!titleText.isEmpty() && titleText.length() > 50) {
                            title.setError("Title must be less than 50 characters");
                            isValid = false;
                        }

                        // Validate author length
                        if (!authorText.isEmpty() && authorText.length() > 30) {
                            author.setError("Author must be less than 30 characters");
                            isValid = false;
                        }

                        // Validate publication year
                        if (!publicationYearText.isEmpty()) {
                            if (publicationYearText.length() != 4 || !publicationYearText.matches("\\d{4}")) {
                                publicationYear.setError("Publication year must be exactly 4 digits");
                                isValid = false;
                            }
                        }


                        if (!statusText.equalsIgnoreCase("Unread") && !statusText.equalsIgnoreCase("Read")) {
                            status.setError("Status must be either 'Unread' or 'Read'");
                            isValid = false;
                        }


                        // If all validations pass, proceed and notify listener
                        if (isValid) {
                            Wishlist newWishlist = new Wishlist(titleText, authorText, genreText, publicationYearText, statusText);
                            listener.onOkPressed(newWishlist);
                            dialog.dismiss(); // Dismiss the dialog after successful validation
                        }
                        // If validation fails, the dialog remains open and errors are shown
                    }
                });
            }
        });

        return dialog;
    }
}