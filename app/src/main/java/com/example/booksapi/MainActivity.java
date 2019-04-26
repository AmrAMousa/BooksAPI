package com.example.booksapi;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.booksapi.adapters.ItemsAdapter;
import com.example.booksapi.models.BookModel;

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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ItemsAdapter itemsAdapter;
    EditText editText;
    Button button;
    String searchItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        editText = findViewById(R.id.search);
        button = findViewById(R.id.search_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchItem = editText.getText().toString();

                setupRecyceler();
                viewBooks();
            }
        });

    }


    private void setupRecyceler() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        itemsAdapter = new ItemsAdapter(this);
        recyclerView.setAdapter(itemsAdapter);

    }

    private void viewBooks() {
        new FetchMovies().execute();
    }

    class FetchMovies extends AsyncTask<Void, Void, List<BookModel>> {

        URL url;
        ProgressBar progressBar = findViewById(R.id.progress_bar);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);

        }

        @Override
        protected List<BookModel> doInBackground(Void... voids) {

            StringBuilder stringBuilder = new StringBuilder();
            try {

                url = new URL("https://www.googleapis.com/books/v1/volumes?q=" + searchItem);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = "";
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuilder.append(s);
                }
                Log.i("do in background", stringBuilder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return parseJson(stringBuilder.toString());
        }

        private List<BookModel> parseJson(String jsonString) {

            List<BookModel> books = new ArrayList<BookModel>();
            try {
                JSONObject mainObj = new JSONObject(jsonString);
                JSONArray items = mainObj.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    String title;
                    String description;
                    String publishedDate;
                    String publisher;
                    JSONArray authors;
                    ArrayList<String> authorsArray = new ArrayList<String>();
                    JSONObject jsonObject = items.getJSONObject(i);

                    JSONObject volumeInfo = jsonObject.getJSONObject("volumeInfo");

                    if (volumeInfo.has("title")) {
                        title = volumeInfo.getString("title");
                    } else {
                        title = "title not found";
                    }

                    if (volumeInfo.has("publishedDate")) {
                        publishedDate = volumeInfo.getString("publishedDate");
                    } else {
                        publishedDate = "published Date not found";
                    }

                    if (volumeInfo.has("description")) {
                        description = volumeInfo.getString("description");
                    } else {
                        description = "description not found";
                    }

                    if (volumeInfo.has("publisher")) {
                        publisher = volumeInfo.getString("publisher");
                    } else {
                        publisher = "publisher name not found";
                    }


                    if (volumeInfo.has("authors")) {
                        authors = volumeInfo.getJSONArray("authors");
                        for (int j = 0; j < authors.length(); j++) {
                            authorsArray.add(authors.get(j).toString());
                        }
                    } else {
                        authorsArray.add("auther name not found");
                    }


                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    String thumbnail = imageLinks.getString("thumbnail");
                    books.add(new BookModel(title, authorsArray, description, thumbnail, publishedDate, publisher));

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("parseJson", "parseJson");
            }

            return books;
        }

        @Override
        protected void onPostExecute(List<BookModel> BookModels) {
            super.onPostExecute(BookModels);
            if (BookModels != null && BookModels.size() != 0) {
                itemsAdapter.setData(BookModels);
                itemsAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, url.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


}

