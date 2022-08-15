package com.example.android.googlebookfinder;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }


    /**
     *  Create  URL object first with null value to protect from nullpointer exception then create
     *  a new URL that takes String input.
     */

    public static URL createURL(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }
        catch (MalformedURLException exception){
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make HttpRequest based on documentation to the API
     */

    private static  String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if(urlConnection.getResponseCode() == urlConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            }
            else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                // newly added /
                return null;
            }


        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e(LOG_TAG, "Problem retrieving the Book JSON results", e);


        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Book> fetchBookData(String requestUrl){
        URL url = createURL(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Book> book = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return book;
    }

    private static List<Book> extractFeatureFromJson(String bookJSON){
        if(TextUtils.isEmpty(bookJSON)){
            return null;
        }

        List<Book> books = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(bookJSON);
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");

            for(int i = 0; i < bookArray.length(); i++ ){
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");

                //Exploring removing duplicates
               /* for(String title = volumeInfo.getString("title"); title.equals(title);) {
                    title = " ";
                })

               */

                String authors = volumeInfo.getString("authors");

                if(authors.equals("")){
                    authors = "No author provided ";
                }
                else{
                    authors = volumeInfo.getString("authors");
                }
                String publishedDate = volumeInfo.getString("publishedDate");

                Book addedBook = new Book (title, authors, publishedDate,R.drawable.book_icon);
                books.add(addedBook);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the book results", e);
        }
        //Return the list of earthquakes
        return books;

        }
    }

