package com.example.vkinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.vkinfo.utils.NetworkUtils.generateURL;
import static com.example.vkinfo.utils.NetworkUtils.getResponseFromUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText searchField;
    private Button searchButton;
    private TextView result;
    private TextView errorMessage;
    private ProgressBar loadingIndicator;
    private RecyclerView numbersList;
    private UserAdapter numbersAdapter;

    private void showResultTextView(){
       // result.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
    }

    private void showErrorTextView(){
        //result.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    //TODO remove deprecated AsynTask
    class VKQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromUrl(urls[0]);
            }catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null && !response.equals("")) {
                String firstName = null;
                String lastName = null;
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("response");

                    List<PersonInfo> infos = new ArrayList<PersonInfo>(jsonArray.length());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject userInfo = jsonArray.getJSONObject(i);
                        firstName = userInfo.getString("first_name");
                        lastName = userInfo.getString("last_name");

                        PersonInfo info = new PersonInfo(firstName, lastName);
                        infos.add(info);
                    }
                    numbersAdapter = new UserAdapter(infos);
                    numbersList.setAdapter(numbersAdapter);
                    showResultTextView();
                } catch (JSONException e) {
                    e.printStackTrace();
                    showErrorTextView();
                }
            }
            else {
                showErrorTextView();
            }
            loadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.et_search_field);
        searchButton = findViewById(R.id.b_search_vk);
        result = findViewById(R.id.tv_result);
        errorMessage = findViewById(R.id.tv_error_message);
        loadingIndicator = findViewById(R.id.pb_loading_indicator);

        numbersList = findViewById(R.id.rv_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        numbersList.setLayoutManager(layoutManager);
        //знаем взаранее размер списка, улучщение памяти
        numbersList.setHasFixedSize(true);

       // numbersAdapter = new NumbersAdapter(100);
        //numbersList.setAdapter(numbersAdapter);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL generatedURL = generateURL(searchField.getText().toString());
                new VKQueryTask().execute(generatedURL);
            }
        };

        searchButton.setOnClickListener(onClickListener);
    }
}