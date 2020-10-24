package com.cs330.pz_katarina_stojkovic;

import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class ViewPorudzbineVlasnikActivity extends AppCompatActivity implements NavigationFragment.OnFragmentInteractionListener {

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_porudzbine_vlasnik);

        mQueue = Volley.newRequestQueue(this);


        this.getPorudzbinePoVlasniku(GlobalUser.getIdUser());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private void getPorudzbinePoVlasniku(String userId) {
        System.out.println(userId);
        String url = "http://192.168.1.102:8080/rest/porudzbine/getPoruzbinePoUseru/"+userId;

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


                                TextView t = new TextView(ViewPorudzbineVlasnikActivity.this);
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


                                LinearLayout row = new LinearLayout(ViewPorudzbineVlasnikActivity.this);
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
