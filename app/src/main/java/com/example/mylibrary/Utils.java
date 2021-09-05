package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static Utils instance;

    private SharedPreferences sharedPreferences;

    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS_KEY = "already_read_books";
    private static final String WANT_TO_READ_BOOKS_KEY = "want_to_read_books";
    private static final String CURRENTLY_READING_BOOKS_KEY = "currently_reading_books";
    private static final String FAVOURITE_BOOKS_KEY = "favourite_books";

//    private static ArrayList<Books> allBooks;
//    private static ArrayList<Books> alreadyRead;
//    private static ArrayList<Books> wantToRead;
//    private static ArrayList<Books> currReading;
//    private static ArrayList<Books> favouriteBooks;

    private Utils(Context context){

        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if (null == getAllBooks())
        {
            initData();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyRead())
        {
            editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(new ArrayList<Books>()));
            editor.commit();
        }
        if (null == getWantToRead())
        {
            editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(new ArrayList<Books>()));
            editor.commit();
        }
        if (null == getFavouriteBooks())
        {
            editor.putString(FAVOURITE_BOOKS_KEY, gson.toJson(new ArrayList<Books>()));
            editor.commit();
        }
        if (null == getCurrReading())
        {
            editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(new ArrayList<Books>()));
            editor.commit();
        }
    }

    private void initData() {

        ArrayList<Books> books = new ArrayList<>();

        books.add(new Books(1, "1q84", "Haruki Murakami", 928, "https://static.tvtropes.org/pmwiki/pub/images/1q84.png", "short desc", " long desc" ));
        books.add(new Books(2, "The Fault In Our Stars", "John Green", 313, "https://pictures.abebooks.com/SETANTABOOKS/16360987587.jpg", "short hui hui hui desc", " long desc" ));
        books.add(new Books(3, "The Kite Runner", "Khaled Hosseini", 324, "https://i.ebayimg.com/images/g/avUAAOSwkHFb6QG5/s-l640.jpg", "short hui hui hui desc", " long desc" ));
        books.add(new Books(4, "A Thousand Splendid Suns", "John Green", 384, "https://www.theoldglobe.org/globalassets/images/2017-2018/a-thousand-splendid-suns/publicity-photos/suns-rgb-print.jpg", "short hui hui hui desc", " long desc" ));

        /**
         * serializing data
         */
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit();
    }

    public static Utils getInstance(Context context){
        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    /**
     * deserializing data
     */
    public ArrayList<Books> getAllBooks() {

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Books>>(){}.getType();
        ArrayList<Books> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Books> getAlreadyRead() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Books>>(){}.getType();
        ArrayList<Books> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Books> getWantToRead() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Books>>(){}.getType();
        ArrayList<Books> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Books> getCurrReading() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Books>>(){}.getType();
        ArrayList<Books> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Books> getFavouriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Books>>(){}.getType();
        ArrayList<Books> books = gson.fromJson(sharedPreferences.getString(FAVOURITE_BOOKS_KEY, null), type);
        return books;
    }

    public Books getBookById(int id) {
        ArrayList<Books> book = getAllBooks();
        if (book != null)
        {
            for (Books b: book)
            {
                if (b.getId() == id)
                {
                    return b;
                }
            }
        }
        return null;
    }

    public boolean addToAlreadyRead (Books book)
    {
        ArrayList<Books> books = getAlreadyRead();
        if (books != null)
        {
            if (books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS_KEY);
                editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToWantToRead (Books book)
    {
        ArrayList<Books> books = getWantToRead();
        if (books != null)
        {
            if (books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS_KEY);
                editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavourite (Books book){
        ArrayList<Books> books = getFavouriteBooks();
        if (books != null)
        {
            if (books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVOURITE_BOOKS_KEY);
                editor.putString(FAVOURITE_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrReading (Books book){
        ArrayList<Books> books = getCurrReading();
        if (books != null)
        {
            if (books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS_KEY);
                editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    /**
     * in remove methods we cant do this
     * if(books.remove(book)
     * because the book we are receiving and the book in the array list are different their values are
     * but reference is different.
     * So we will apply for each loop.
     */

    public boolean removeFromAlreadyRead(Books book){
        ArrayList<Books> books = getAlreadyRead();
        if (null != books){
            for (Books b: books){
                if (b.getId() == book.getId())
                {
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS_KEY);
                        editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromWantToRead(Books book){
        ArrayList<Books> books = getWantToRead();
        if (null != books){
            for (Books b: books){
                if (b.getId() == book.getId())
                {
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOKS_KEY);
                        editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromCurrReading(Books book){
        ArrayList<Books> books = getCurrReading();
        if (null != books){
            for (Books b: books){
                if (b.getId() == book.getId())
                {
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS_KEY);
                        editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromFavourites(Books book){
        ArrayList<Books> books = getFavouriteBooks();
        if (null != books){
            for (Books b: books){
                if (b.getId() == book.getId())
                {
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVOURITE_BOOKS_KEY);
                        editor.putString(FAVOURITE_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }



}
