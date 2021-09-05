package com.example.mylibrary;

import static com.example.mylibrary.WebViewActivity.URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAllBooks, btnAlreadyRead, btnWantToRead, btnCurrReading, btnFavourite, btnAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnAllBooks.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllBooksActivity.class);
            startActivity(intent);
        });

        btnAlreadyRead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlreadyReadBookActivity.class);
            startActivity(intent);
        });

        btnWantToRead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WantToReadActivity.class);
            startActivity(intent);
        });

        btnCurrReading.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CurrReadingActivity.class);
            startActivity(intent);
        });

        btnFavourite.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavouriteBookActivities.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("This application is created by Sudhanshu Ranjan" + "\n" +
                    "Visit my website to have a look on other applications!");
            builder.setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                    intent.putExtra(URL, "https://www.google.com/");
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setCancelable(false);
            builder.create().show();

        });

        Utils.getInstance(this);
    }



    private void initViews(){
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnWantToRead = findViewById(R.id.btnWantToRead);
        btnCurrReading = findViewById(R.id.btnCurrReading);
        btnFavourite = findViewById(R.id.btnFavourite);
        btnAbout = findViewById(R.id.btnAbout);
    }
}