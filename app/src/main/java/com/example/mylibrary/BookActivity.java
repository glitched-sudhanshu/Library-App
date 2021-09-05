package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";
    private TextView bookNameTxt, txtAuthorName, noOfPages, longDescTxt;
    private Button btnReading, btnWantTo, btnAddToFav, btnAddToRead;
    private ImageView bookImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (null != intent)
        {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1)
            {
                Books incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (incomingBook != null)
                {
                    getData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToRead(incomingBook);
                    handleAddToFavourites(incomingBook);
                    handleCurrReading(incomingBook);
                }
            }
        }



    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return onOptionsItemSelected(item);
    }

    private void handleCurrReading(Books book) {
        ArrayList<Books> currReading = Utils.getInstance(this).getCurrReading();
        boolean existInCurrReading = false;
        for (Books b: currReading)
        {
            if (book.getId() == b.getId())
            {
                existInCurrReading = true;
                break;
            }
        }
        if (existInCurrReading)
        {
            btnReading.setEnabled(false);
        }
        else{
            btnReading.setOnClickListener(v -> {
                if (Utils.getInstance(BookActivity.this).addToCurrReading(book))
                {
                    Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, CurrReadingActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleAddToFavourites(Books book) {
        ArrayList<Books> favouriteBooks = Utils.getInstance(this).getFavouriteBooks();
        boolean existInFavourites = false;
        for (Books b: favouriteBooks) {
            if (book.getId() == b.getId()) {
                existInFavourites = true;
                break;
            }
        }
            if (existInFavourites)
            {
                btnAddToFav.setEnabled(false);
            }
            else
            {
                btnAddToFav.setOnClickListener(v -> {
                    if (Utils.getInstance(BookActivity.this).addToFavourite(book))
                    {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, FavouriteBookActivities.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }

    private void handleWantToRead(Books book) {
        ArrayList<Books> wantToReadBooks = Utils.getInstance(this).getWantToRead();

        boolean existInWantToRead = false;
        for (Books b: wantToReadBooks)
        {
            if (b.getId() == book.getId())
            {
                existInWantToRead = true;
                break;
            }
        }
        if (existInWantToRead)
        {
            btnWantTo.setEnabled(false);
        }
        else
        {
            btnWantTo.setOnClickListener(v -> {
                if (Utils.getInstance(BookActivity.this).addToWantToRead(book))
                {
                    Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(BookActivity.this, "Something wrong happened. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void handleAlreadyRead(Books book) {
        ArrayList<Books> alreadyReadBooks = Utils.getInstance(this).getAlreadyRead();

        boolean existInAlreadyRead = false;
        for (Books b: alreadyReadBooks)
        {
            if (b.getId() == book.getId())
            {
                existInAlreadyRead = true;
                break;
            }
        }
        if (existInAlreadyRead)
        {
            btnAddToRead.setEnabled(false);
        }
        else
        {
            btnAddToRead.setOnClickListener(v -> {
                if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book))
                {
                    Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(BookActivity.this, "Something wrong happened. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getData(@NonNull Books book){
        bookNameTxt.setText(book.getName());
        txtAuthorName.setText(book.getAuthor());
        noOfPages.setText(String.valueOf(book.getPages()));
        longDescTxt.setText(book.getLongDesc());

        Glide.with(this)
                .asBitmap()
                .load(book.getImageUrl())
                .into(bookImageView);
    }

    private void initViews(){
        bookNameTxt = findViewById(R.id.bookNameTxt);
        txtAuthorName = findViewById(R.id.txtAuthorName);
        noOfPages = findViewById(R.id.noOfPages);
        longDescTxt = findViewById(R.id.longDescTxt);

        btnReading = findViewById(R.id.btnReading);
        btnWantTo = findViewById(R.id.btnWantTo);
        btnAddToRead = findViewById(R.id.btnAddToRead);
        btnAddToFav = findViewById(R.id.btnAddToFav);

        bookImageView = findViewById(R.id.bookImageView);
    }
}