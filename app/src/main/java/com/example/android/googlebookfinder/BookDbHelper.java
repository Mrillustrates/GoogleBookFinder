package com.example.android.googlebookfinder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDbHelper extends SQLiteOpenHelper{

    /**
     * Name of the database file
     *
     */
    private static final String DATABASE_NAME = "books.db";

    /**
     * Version of the database
     */
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instatnce of Database
     */
    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Create a String table that contains the SQL statement to create it
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE "  +  BookContract.BookEntry.TABLE_NAME + " ( "
                + BookContract.BookEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookContract.BookEntry.COLUMN_BOOK_TITLE + "TEXT NOT NULL, "
                + BookContract.BookEntry.COLUMNN_BOOK_AUTHOR + "TEXT, "
                + BookContract.BookEntry.COLUMN_BOOK_PUBLICATION_YEAR + "INTEGER NOT NULL);";
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}


