package com.example.android.googlebookfinder;

public class Book {

    private String title;
    private String authors;
    private String publishedDate;
    private int mImageResourceId;

    //Constructor as each book on the list will show title, author , and pub date
    public Book(String queryTitle, String queryAuthors, String queryPublishedDate, int imageResourceId){
        title = queryTitle.substring(0, Math.min(queryTitle.length(), 50));
        authors = queryAuthors;
        publishedDate = queryPublishedDate;
        mImageResourceId = imageResourceId;
    }

    public Book(String queryTitile, String queryPublishedDate){
        title = queryTitile;
        publishedDate= queryPublishedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }
}
