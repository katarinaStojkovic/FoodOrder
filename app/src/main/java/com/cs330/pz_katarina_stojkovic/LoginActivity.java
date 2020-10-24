package com.cs330.pz_katarina_stojkovic;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView registerButton;
    private RequestQueue mQueue;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        String[] userData = databaseAccess.getUser();
            System.out.println(userData[0] + " " + userData[1]+ " " + userData[2]);
            if(!userData[0].equals("0")) {
            GlobalUser.setIdRole(userData[1]);
            GlobalUser.setIdUser(userData[0]);
            GlobalUser.setToken(userData[2]);
            GlobalUser.setUsername(userData[3]);
                if(GlobalUser.getIdRole().equals("1")){
                    Intent intent = new Intent(LoginActivity.this, ViewPorudzbineVlasnikActivity.class);
                    startActivity(intent);
                }

                if(GlobalUser.getIdRole().equals("3")){
                    Intent intent = new Intent(LoginActivity.this, StartPageKupacActivity.class);
                    startActivity(intent);
                }
        }

        databaseAccess.close();

        loginButton = findViewById(R.id.loginBtn);
        registerButton = findViewById(R.id.registerKupacBtn);
        mQueue = Volley.newRequestQueue(this);

        loginButton.setOnClickListener(new View.OnClickListener() {

            TextView username = (TextView)findViewById(R.id.usernameFld);
            TextView password = (TextView)findViewById(R.id.passwordFld);

            @Override
            public void onClick(View view) {
                login(username.getText().toString(), password.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationKupacActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login(String username, String password) {
        final String usernameForGlobal = username;
        try {
            String URL = "http://192.168.1.102:8080/rest/users/getUserByUsernameAndPassword/";
            final JSONObject jsonBody = new JSONObject();
            byte[] bytesEncoded = password.getBytes();
            jsonBody.put("username", username);
            System.out.println(new String(bytesEncoded));
            String s = new String(bytesEncoded);

            jsonBody.put("password", new String(s.substring(0, s.length()-1)));
            System.out.println("jsonBody: " + jsonBody);
            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        System.out.println("ima li odgovora");
                        JSONObject jsonObject = new JSONObject(response.toString());
                        System.out.println("usao sam: " + jsonObject);
                        System.out.println(jsonObject.getString("code"));
                        if(jsonObject.getString("code").equals("1")) {
                            GlobalUser.setIdRole(jsonObject.getString("roleId"));
                            GlobalUser.setIdUser(jsonObject.getString("userId"));
                            GlobalUser.setToken(jsonObject.getString("token"));
                            GlobalUser.setUsername(usernameForGlobal);
                            databaseAccess.open();
                            databaseAccess.addUser(jsonObject.getString("userId"), jsonObject.getString("roleId"), jsonObject.getString("token"), usernameForGlobal);
                            databaseAccess.close();
                            if(GlobalUser.getIdRole().equals("1")){
                                Intent intent = new Intent(LoginActivity.this, ViewPorudzbineVlasnikActivity.class);
                                startActivity(intent);
                            }

                            if(GlobalUser.getIdRole().equals("3")){
                                Intent intent = new Intent(LoginActivity.this, StartPageKupacActivity.class);
                                startActivity(intent);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid login data", Toast.LENGTH_SHORT).show();
                        }
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
