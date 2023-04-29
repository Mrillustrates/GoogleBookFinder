package com.example.android.googlebookfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
    private ImageView emptyBookView;
    private ProgressBar loadingProgressView;
    private TextView noConnectionTextView;

    private BookAdapter adapter;

    private static String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        SearchView bookSearch = (SearchView) findViewById(R.id.search_book);
        final CharSequence queryBook = bookSearch.getQuery();


        final ListView bookListView = (ListView) findViewById(R.id.list);


        adapter = new BookAdapter(this, new ArrayList<Book>());

        //Listview set up book listings
        bookListView.setAdapter(adapter);


        //Sets up an empty view if no results
        emptyTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyTextView);


        /*emptyBookView = (ImageView) findViewById(R.id.empty_dogBook_image);
        bookListView.setEmptyView(emptyBookView);
        */


        //Loading icon displays while API is searched
        loadingProgressView = (ProgressBar) findViewById(R.id.loading_spinner);
        loadingProgressView.setVisibility(View.GONE);


        //If no connection on device inform the user
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



            /*
            On Item click Listener to route user to a Google book search to get more info on the book
             */
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String urlForBookClick = "http://www.google.com/search?tbm=bks&q=";


                Book currentChoice = (Book) parent.getItemAtPosition(position);

                TextView chosenBook = (TextView) view.findViewById(R.id.book_title);
                chosenBook.setText(currentChoice.getTitle());

                    /*

                     */

                String addedURLText = chosenBook.getText().toString();

                //Intent intent = new Intent(Intent.ACTION_VIEW);
                // intent.setData(Uri.parse("www.google.com"));
                //added order newest by 4/10
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlForBookClick + addedURLText )));
                //orderBy=newest not applicable
            }
        });


        /**
         *\Event listener that takes the text that was searched and sends a request to the API
         */
        bookSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                /*
                If query is valid and device is online search API once results are shown give a message to use to click to learn more
                 */
                boolean isConnected = isNetworkConnected();
                if (isConnected && query != null) {
                    if (query.contains(" ")) {
                        query = query.replace(" ", "");
                    }
                    GOOGLE_BOOKS_REQUEST_URL = (GOOGLE_BOOKS_REQUEST_URL + query + "&maxResults=5");
                    // + "&maxResults=10&"+ "&callback=handleResponse"//);
                    //  Toast.makeText(MainActivity.this, GOOGLE_BOOKS_REQUEST_URL, Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Click a selection to learn more!", Toast.LENGTH_LONG).show();

                    getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                    loadingProgressView.setVisibility(View.VISIBLE);
                } else if (!isConnected) {
                    loadingProgressView.setVisibility(View.GONE);
                    emptyTextView.setText(R.string.no_internet);

                    if (isConnected && query == null) {
                        loadingProgressView.setVisibility(View.GONE);
                        GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
                        emptyTextView.setText(R.string.no_books);
                        /*emptyBookView.setImageResource(R.drawable.mr_petey);
                        emptyBookView.setVisibility(View.VISIBLE);
                        */

                     /*
                     TODO: Add in a cute image for no books found -- research!!!!!!
                      */


                    }
                }
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
    }

    public String googleURL(String url) {
        return "https://www.googleapis.com/books/v1/volumes?q=" + (url);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {

        return new BookLoader(this, GOOGLE_BOOKS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        loadingProgressView.setVisibility(View.GONE);


        adapter.clear();

        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
            GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
        } else {
            emptyTextView.setText(R.string.no_books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;


    }
}

