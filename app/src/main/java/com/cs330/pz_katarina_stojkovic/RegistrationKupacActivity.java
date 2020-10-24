package com.cs330.pz_katarina_stojkovic;




import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import org.json.JSONObject;

import java.util.ArrayList;

public class RegistrationKupacActivity extends AppCompatActivity {

    private Button registerButton;
    private TextView loginButton;
    private RequestQueue mQueue;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_kupac);

        loginButton = findViewById(R.id.loginBtn);
        registerButton = findViewById(R.id.registerKupacBtn);
        mQueue = Volley.newRequestQueue(this);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationKupacActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            TextView ime = (TextView)findViewById(R.id.imeFld);
            TextView prezime = (TextView)findViewById(R.id.prezimeFld);
            TextView username = (TextView)findViewById(R.id.usernameFld);
            TextView datumRodjena = (TextView)findViewById(R.id.datumRodjenaFld);
            TextView telefon = (TextView)findViewById(R.id.telefonFld);
            TextView mail = (TextView)findViewById(R.id.mailFld);
            TextView password = (TextView)findViewById(R.id.passwordFld);

            @Override
            public void onClick(View view) {
                registerUser(ime.getText().toString(),prezime.getText().toString(),
                        username.getText().toString(),datumRodjena.getText().toString(),
                        telefon.getText().toString(),mail.getText().toString(),password.getText().toString());
               }
        });

    }

    private void registerUser(String ime,String prezime,String username,String datumRodjena,String telefon,String email, String password) {
        try {
            String URL = "http://192.168.1.102:8080/rest/users/createOnlyUser/";
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("ime", ime);
            jsonBody.put("prezime", prezime);
            jsonBody.put("username", username);
            jsonBody.put("datumRodjenja", datumRodjena);
            jsonBody.put("telefon", telefon);
            jsonBody.put("email", email);
            jsonBody.put("password",password);
            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                            Intent intent = new Intent(RegistrationKupacActivity.this, LoginActivity.class);
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
