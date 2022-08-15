package com.example.android.googlebookfinder;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.io.StringBufferInputStream;

public final class BookContract {

    private BookContract(){}

    //Content Authority - provides a constant to be used as part of a URI
    public static final String CONTENT_AUTHORITY = "com.example.android.googlebookfinder";

    //Base content URI - concatenates the content:// with the CONTENT_AUTHORITY to provide BASE_CONTENT
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Pathing for "Reading list"
    public static final String PATH_BOOKS = "books";

    //Creation of BookEntry object that implements Basecolumns
    public static final class BookEntry implements BaseColumns{


        //When a book entry is made append the BASE_CONTENT_URI with PATH_BOOKS
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        /**
         * List type for content_uri
         */
        public static final String CONTENT_LIST_TYPE=
                ContentResolver.CURSOR_DIR_BASE_TYPE+ "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single book.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /**
         * Establishing constants for the table
         */
        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BOOK_TITLE = "title";
        public static final String COLUMNN_BOOK_AUTHOR = "author";
        public static final String COLUMN_BOOK_PUBLICATION_YEAR = "publication date";





    }

}
