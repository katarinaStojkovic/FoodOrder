package com.cs330.pz_katarina_stojkovic;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class ViewJelaActivity extends AppCompatActivity implements NavigationFragment.OnFragmentInteractionListener {

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jela);

        mQueue = Volley.newRequestQueue(this);


        this.getJela(GlobalUser.getIdUser());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void getJela(String userId) {
        System.out.println(userId);
        String url = "http://192.168.1.102:8080/rest/jela/getJeloPoUseru/"+userId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONArray jsonArray = response;


                            LinearLayout jelaLayout = (LinearLayout) findViewById(R.id.linearLayoutVasePorudzbine);

                            for(int i =0;i<jsonArray.length();i++) {
                                JSONObject jelo = jsonArray.getJSONObject(i);
                                System.out.println(jelo);
                                final int idJela = jelo.getInt("idjela");
                                TextView t = new TextView(ViewJelaActivity.this);
                                t.setTextSize(22);
                                t.setText(
                                        "Naziv jela: "+jelo.getString("nazivjela")
                                                + " cena: "+jelo.getString("cena")
                                                + " opis: "+jelo.getString("opis")
                                );


                                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                p.weight = 1;
                                Button deleteJelo = new Button(ViewJelaActivity.this);
                                deleteJelo.setText("Obriši");
                                deleteJelo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                       deleteJelo(idJela+"");
                                    }
                                });
                                Button createJelo = new Button(ViewJelaActivity.this);
                                createJelo.setText("dodaj");
                                createJelo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ViewJelaActivity.this, CreateJelaActivity.class);
                                        startActivity(intent);
                                    }
                                });


                                LinearLayout row = new LinearLayout(ViewJelaActivity.this);
                                deleteJelo.setBackgroundColor(Color.parseColor("#FF9912"));
                                deleteJelo.setTextColor(Color.parseColor("#F0E9E9"));

                                createJelo.setBackgroundColor(Color.parseColor("#FF9912"));
                                createJelo.setTextColor(Color.parseColor("#F0E9E9"));


                                row.setOrientation(LinearLayout.VERTICAL);
                                row.addView(t);
                                jelaLayout.addView(row);
                                row.addView(deleteJelo);
                                row.addView(createJelo);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    private void deleteJelo(String jeloId) {

        String url = "http://192.168.1.102:8080/rest/jela/deleteJelo/"+jeloId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                                Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

}
