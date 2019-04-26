package com.example.booksapi.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.booksapi.models.BookModel;

import com.example.booksapi.R;
import com.example.booksapi.models.BookModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    Context context;
    List<BookModel> BookModels;

    public ItemsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemsAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_item, viewGroup, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int i) {
        BookModel BookModel = BookModels.get(i);
        itemsViewHolder.bookName.setText(BookModel.getTitle());
        String authors = "";
        for (int j = 0; j < BookModel.getAuthorsArray().size(); j++) {
            authors += BookModel.getAuthorsArray().get(j)+"\n";
        }
        itemsViewHolder.bookAuthors.setText(authors);
        Picasso.with(context)
                .load(BookModel.getThumbnail())
                .placeholder(R.drawable.book_icon)
                .into(itemsViewHolder.bookImg);
    }

    @Override
    public int getItemCount() {
        return BookModels != null ? BookModels.size() : 0;
    }

    public void setData(List<BookModel> BookModels) {
        this.BookModels = BookModels;
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImg;
        TextView bookName, bookAuthors;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImg = itemView.findViewById(R.id.book_img);
            bookName = itemView.findViewById(R.id.book_name);
            bookAuthors = itemView.findViewById(R.id.book_description);

        }
    }

    {

    }
}
