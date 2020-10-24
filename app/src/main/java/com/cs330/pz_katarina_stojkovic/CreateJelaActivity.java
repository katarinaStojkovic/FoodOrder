package com.cs330.pz_katarina_stojkovic;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class CreateJelaActivity extends AppCompatActivity {
    private Button createButton;
    private RequestQueue mQueue;
    DatabaseAccess databaseAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_jela);

        createButton = findViewById(R.id.createBtn);


        createButton.setOnClickListener(new View.OnClickListener() {

            TextView nazivJela = (TextView)findViewById(R.id.nazivJelaFld);
            TextView cena = (TextView)findViewById(R.id.cenaFld);
            TextView opis = (TextView)findViewById(R.id.opisFld);

            @Override
            public void onClick(View view) {
                create(GlobalUser.getIdUser(),nazivJela.getText().toString(),cena.getText().toString(),opis.getText().toString());
            }
        });

    }


    private void create(String userId, String nazivJela,String cena,String opis) {
        final String GlonaluserId = userId;
        try {
            String URL = "http://192.168.1.102:8080/rest/jela/jelo/";
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("userId", userId);
            jsonBody.put("nazivjela", nazivJela);
            jsonBody.put("cena", cena);
            jsonBody.put("opis", opis);
            System.out.println("jsonBody: " + jsonBody);
            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                            Intent intent = new Intent(CreateJelaActivity.this, ViewJelaActivity.class);
                                startActivity(intent);
                    } catch(Exception e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

            }) {

            };

           mQueue.add(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }


}
