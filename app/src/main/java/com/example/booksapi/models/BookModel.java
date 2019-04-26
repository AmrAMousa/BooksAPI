package com.example.booksapi.models;

import java.util.ArrayList;

public class BookModel{

    String title;
    ArrayList<String> authorsArray;
    String description;
    String thumbnail;
    String publishedDate;
    String publisher;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthorsArray() {
        return authorsArray;
    }

    public void setAuthorsArray(ArrayList<String> authorsArray) {
        this.authorsArray = authorsArray;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public BookModel(String title, ArrayList<String> authorsArray, String description, String thumbnail, String publishedDate, String publisher) {
        this.title = title;
        this.authorsArray = authorsArray;
        this.description = description;
        this.thumbnail = thumbnail;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }
}
