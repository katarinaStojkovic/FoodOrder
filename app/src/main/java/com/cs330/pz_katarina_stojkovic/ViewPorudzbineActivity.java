package com.cs330.pz_katarina_stojkovic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPorudzbineActivity extends AppCompatActivity implements NavigationFragment.OnFragmentInteractionListener {

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_porudzbine);

        mQueue = Volley.newRequestQueue(this);


        this.getPorudzbinePoUseru(GlobalUser.getIdUser());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void getPorudzbinePoUseru(String userId) {
        System.out.println(userId);
        String url = "http://192.168.1.102:8080/rest/porudzbine/getPoruzbinePoUseruKupac/"+userId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONArray jsonArray = response;


                            LinearLayout porudzbineLayout = (LinearLayout) findViewById(R.id.linearLayoutVasePorudzbine);

                            for(int i =0;i<jsonArray.length();i++) {
                                JSONObject porudzbina = jsonArray.getJSONObject(i);
                                System.out.println(porudzbina);
                                JSONObject jelo = porudzbina.getJSONObject("jeloByIdjela");
                                JSONObject restoran = jelo.getJSONObject("restoranByIdrestorana");


                                TextView t = new TextView(ViewPorudzbineActivity.this);
                                t.setTextSize(22);
                                        t.setText(
                                                "Naziv jela: "+jelo.getString("nazivjela")
                                                + " cena: "+jelo.getString("cena")
                                                + " restoran: "+restoran.getString("naziv")
                                                +" datum isporuke: "+porudzbina.getString("datumisporuke")
                                                + " vreme isporuke: "+porudzbina.getString("vremeisporuke")
                                                + " adresa: "+porudzbina.getString("adresaisporuke")
                                        );


                                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                p.weight = 1;


                                LinearLayout row = new LinearLayout(ViewPorudzbineActivity.this);
                                row.setOrientation(LinearLayout.HORIZONTAL);
                                row.addView(t);
                                porudzbineLayout.addView(row);
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



}
