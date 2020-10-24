package com.cs330.pz_katarina_stojkovic;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartPageKupacActivity extends AppCompatActivity implements NavigationFragment.OnFragmentInteractionListener {

    private Button poruciBtn;
    private RequestQueue mQueue;
    private GoogleMap mMap;
    private Map<String,String> restorana = new HashMap<>();
    private Map<String,String> jelaPoRestoranu = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_kupac_page);

        mQueue = Volley.newRequestQueue(this);

        getRestorani();

        poruciBtn = findViewById(R.id.cratePorudzbinaBtn);
//        poruciBtn.setOnClickListener(new View.OnClickListener() {
//
//            TextView username = (TextView)findViewById(R.id.usernameFld);
//            TextView password = (TextView)findViewById(R.id.passwordFld);
//
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


    }




    private void getJelaPoRestoranu(String restoranId) {

        System.out.println("restoranID: " + restoranId);
        String url = "http://192.168.1.102:8080/rest/jela/getJelaByRestoranId/"+restoranId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONArray jsonArray = response;
                            List<String> listaJela = new ArrayList<>();

                            for(int i = 0;i<jsonArray.length();i++) {
                                JSONObject jelo = jsonArray.getJSONObject(i);
                                jelaPoRestoranu.put(jelo.getString("nazivjela"), jelo.getString("idjela"));
                                listaJela.add(jelo.getString("nazivjela"));
                            }

                            final String[] jela = listaJela.toArray(new String[listaJela.size()]);

                            final Spinner sp1 = (Spinner) findViewById(R.id.spinnerJeloPoRestoranu);
                            ArrayAdapter<String> adp1 = new ArrayAdapter<String>(StartPageKupacActivity.this,
                                    android.R.layout.simple_list_item_1, jela);
                            adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp1.setAdapter(adp1);

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


    private void getRestorani() {

        String url = "http://192.168.1.102:8080/rest/restorani/getAllRestorans";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONArray jsonArray = response;
                            List<String> listaRestorana = new ArrayList<>();

                            for(int i = 0;i<jsonArray.length();i++) {
                                JSONObject restoran = jsonArray.getJSONObject(i);
                                restorana.put(restoran.getString("naziv"), restoran.getString("idrestorana"));
                                listaRestorana.add(restoran.getString("naziv"));
                            }

                            final String[] restoran2 = listaRestorana.toArray(new String[listaRestorana.size()]);
                            ArrayAdapter<String> adp1 = new ArrayAdapter<String>(StartPageKupacActivity.this,
                                    android.R.layout.simple_list_item_1, restoran2);
                            adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            final Spinner sp1 = (Spinner) findViewById(R.id.spinnerRestoranStart);
                            sp1.setAdapter(adp1);
                            AdapterView.OnItemSelectedListener restoranSelectedListener = new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> spinner, View container,
                                                           int position, long id) {
                                    String jeloPoRestoranu ="";
                                     jeloPoRestoranu = restorana.get(restoran2[position]);
                                    getJelaPoRestoranu(jeloPoRestoranu);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            };
                            sp1.setOnItemSelectedListener(restoranSelectedListener);
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



    private void createPorudbina(String userId, String jeloByIdjela,String datumisporuke, String vremeisporuke,String adresaisporuke,String kolicina) {

        try {
            String URL = "http://192.168.1.102:8080/rest/porudzbine/porudzbina/";
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("userId", userId);
            jsonBody.put("jeloByIdjela", jeloByIdjela);
            jsonBody.put("datumisporuke", datumisporuke);
            jsonBody.put("vremeisporuke", vremeisporuke);
            jsonBody.put("adresaisporuke", adresaisporuke);
            jsonBody.put("kolicina", kolicina);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Toast.makeText(getApplicationContext(), "Successfully given job", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(StartPageKupacActivity.this,ViewPorudzbineActivity.class);
                        startActivity(intent);
                    } catch(Exception e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

            });

            mQueue.add(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

