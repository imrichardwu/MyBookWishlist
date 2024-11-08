package com.example.richard9_mybookwishlist;


public class Wishlist {
    private String title;
    private String author;
    private String genre;
    private String publicationYear;
    private String status;

    // Constructor for the Wishlist class that takes all the fields as parameters and initializes them
    public Wishlist(String title, String author, String genre, String publicationYear, String status) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}