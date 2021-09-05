package com.example.mylibrary;

import static com.example.mylibrary.BookActivity.BOOK_ID_KEY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BooksRecViewAdapter extends RecyclerView.Adapter<BooksRecViewAdapter.ViewHolder> {

    private static final String TAG = "BooksRecViewAdapter";
    private ArrayList<Books> books = new ArrayList<>();
    private final Context mContext;
    private final String parentActivity;


    public BooksRecViewAdapter(Context mContext, String parentActivity) {
        this.mContext = mContext;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtBookName.setText(books.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(books.get(position).getImageUrl())
                .into(holder.imgBook);
        holder.parent.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BookActivity.class);
            intent.putExtra(BOOK_ID_KEY, books.get(position).getId());
            mContext.startActivity(intent);
        });

        holder.authorName.setText(books.get(position).getAuthor());
        holder.shortDesc.setText(books.get(position).getShortDesc());

        if (books.get(position).isExpanded())
        {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);
/*
  Logic for delete button
 */
            switch (parentActivity) {
                case "allBooks":
                    holder.imgDelete.setVisibility(View.GONE);
                    break;
/*
  for already read
  */
                case "alreadyRead":
                    holder.imgDelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete this book?")
                        .setCancelable(false);
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            if (Utils.getInstance(mContext).removeFromAlreadyRead(books.get(position))) {
                                Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(mContext, "Something wrong happened, Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {

                        });

                        builder.create().show();
                    });
                    break;
/*
  for want to read
  */
                case "wantToRead":
                    holder.imgDelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete this book?")
                                .setCancelable(false);
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            if (Utils.getInstance(mContext).removeFromWantToRead(books.get(position))) {
                                Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(mContext, "Something wrong happened, Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {

                        });

                        builder.create().show();
                    });
                    break;
/*
  for currently reading
  */
                case "currReading":
                    holder.imgDelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete this book?")
                                .setCancelable(false);
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            if (Utils.getInstance(mContext).removeFromCurrReading(books.get(position))) {
                                Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(mContext, "Something wrong happened, Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {

                        });

                        builder.create().show();
                    });
                    break;
/*
  for favourites
  */
                case "Favourites":
                    holder.imgDelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete this book?")
                                .setCancelable(false);
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            if (Utils.getInstance(mContext).removeFromFavourites(books.get(position))) {
                                Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(mContext, "Something wrong happened, Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {

                        });

                        builder.create().show();
                    });
                    break;
            }
        }
        else
        {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(ArrayList<Books> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView parent;
        private final ImageView imgBook, imgDelete;
        private final TextView txtBookName, shortDesc, authorName;
        private final ImageView upArrow, downArrow;
        private final RelativeLayout expandedRelLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            txtBookName = itemView.findViewById(R.id.txtBookName);

            shortDesc = itemView.findViewById(R.id.shortDesc);
            authorName = itemView.findViewById(R.id.authorName);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            downArrow = itemView.findViewById(R.id.btnDownArrow);
            expandedRelLayout = itemView.findViewById(R.id.expandedRelLayout);


            downArrow.setOnClickListener(v -> {
                Books book = books.get(getAdapterPosition());
                book.setExpanded(!book.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });

            upArrow.setOnClickListener(v -> {
                Books book = books.get(getAdapterPosition());
                book.setExpanded(!book.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
        }

    }
}

