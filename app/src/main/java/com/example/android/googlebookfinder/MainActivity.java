package com.example.android.googlebookfinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {


    private static final int BOOK_LOADER_ID = 1;

    private TextView emptyTextView;
    private ProgressBar loadingProgressView;
    private TextView noConnectionTextView;

    private BookAdapter adapter;

    private static String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);




        SearchView bookSearch = (SearchView) findViewById(R.id.search_book);
        final CharSequence queryBook =  bookSearch.getQuery();


        ListView bookListView = (ListView) findViewById(R.id.list);



        adapter = new BookAdapter(this, new ArrayList<Book>());

        //Listview set up book listings
        bookListView.setAdapter(adapter);

        emptyTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyTextView);

        loadingProgressView = (ProgressBar) findViewById(R.id.loading_spinner);

        noConnectionTextView = (TextView) findViewById(R.id.no_internet_textview);
        bookListView.setEmptyView(noConnectionTextView);

        /*
        if(isNetworkConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);

        }
        else{
            loadingProgressView.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet);

        }
          */

        bookSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                boolean isConnected = isNetworkConnected();
                if(isConnected && query != null){
                    if(query.contains(" ")){
                        query = query.replace(" ", "");
                    }
                      GOOGLE_BOOKS_REQUEST_URL= (GOOGLE_BOOKS_REQUEST_URL + query+ "&maxResults=10");
                      // + "&maxResults=10&"+ "&callback=handleResponse"//);
                      Toast.makeText(MainActivity.this, GOOGLE_BOOKS_REQUEST_URL, Toast.LENGTH_LONG).show();

                    getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                }

                 else{
                     loadingProgressView.setVisibility(View.GONE);
                     emptyTextView.setText(R.string.no_internet);
                     GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
                     emptyTextView.setText(R.string.no_books);

                 }
                    return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
    }

    public String googleURL(String url){
        return "https://www.googleapis.com/books/v1/volumes?q="+ (url);
    }
    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {

        return new BookLoader(this, GOOGLE_BOOKS_REQUEST_URL);

    }
    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List <Book> data){
        loadingProgressView.setVisibility(View.GONE);


        adapter.clear();

        if(data != null && !data.isEmpty()){
            adapter.addAll(data);
            GOOGLE_BOOKS_REQUEST_URL =  "https://www.googleapis.com/books/v1/volumes?q=";
        }
        else {
            emptyTextView.setText(R.string.no_books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader){
        adapter.clear();
        
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() !=null && cm.getActiveNetworkInfo().isConnected();
    }


}
