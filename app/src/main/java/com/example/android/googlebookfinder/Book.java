package com.example.android.googlebookfinder;

public class Book {

    private String title;
    private String authors;
    private String publishedDate;

    //Constructor as each book on the list will show title, author , and pub date
    public Book(String queryTitle, String queryAuthors, String queryPublishedDate){
        title = queryTitle;
        authors = queryAuthors;
        publishedDate = queryPublishedDate;
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
}
