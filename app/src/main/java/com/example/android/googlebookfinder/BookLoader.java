package com.example.android.googlebookfinder;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>>{

    private static final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public BookLoader(Context context,String url){
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground(){


        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        List<Book> result = QueryUtils.fetchBookData(mUrl);
        return result;
    }
}
