package com.cs330.pz_katarina_stojkovic;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavigationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationFragment extends Fragment implements View.OnClickListener {

    private ImageButton hamburger;
    private Button button;
    DatabaseAccess databaseAccess;
    private static final String[] paths = {"Home", "Contact", "About", "Search Jobs", "Notifications", "", "Edit profile", "Control Panel", "", "Logout"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NavigationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavigationFragment newInstance(String param1, String param2) {
        NavigationFragment fragment = new NavigationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_navigation, container, false);

        hamburger = (ImageButton) v.findViewById(R.id.btnDrawer);

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationView navView = (NavigationView)v.findViewById(R.id.navView);
                if(navView.getVisibility() == View.VISIBLE) {
                    navView.setVisibility(View.GONE);
                } else {
                    navView.setVisibility(View.VISIBLE);
                }
            }
        });


        databaseAccess = DatabaseAccess.getInstance(getActivity());

        Button viewPorudzbine = (Button)v.findViewById(R.id.viewPorudzbineBtn);
        viewPorudzbine.setOnClickListener(this);

        Button viewPorudzbineVlasnik = (Button)v.findViewById(R.id.viewPorudzbineVlasnikBtn);
        viewPorudzbineVlasnik.setOnClickListener(this);

        Button createPorudzbine = (Button)v.findViewById(R.id.cratePorudzbinaBtn);
        createPorudzbine.setOnClickListener(this);

        Button viewJela= (Button)v.findViewById(R.id.viewJelaBtn);
        viewJela.setOnClickListener(this);

        if(GlobalUser.getIdRole().equals("1")) { // vlasnik
            viewPorudzbine.setVisibility(View.GONE);
            createPorudzbine.setVisibility(View.GONE);
        } else if(GlobalUser.getIdRole().equals("3")) { // kupac
            if(getActivity() instanceof  StartPageKupacActivity){
                createPorudzbine.setVisibility(View.GONE);
            }
            viewPorudzbineVlasnik.setVisibility(View.GONE);
            viewJela.setVisibility(View.GONE);
        }

        Button logoutBtn = (Button)v.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(this);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {

            case R.id.viewPorudzbineBtn:
                intent = new Intent(getActivity(), ViewPorudzbineActivity.class);
                startActivity(intent);
                break;
            case R.id.viewPorudzbineVlasnikBtn:
                intent = new Intent(getActivity(), ViewPorudzbineVlasnikActivity.class);
                startActivity(intent);
                break;
            case R.id.cratePorudzbinaBtn:
                intent = new Intent(getActivity(), StartPageKupacActivity.class);
                startActivity(intent);
                break;

            case R.id.viewJelaBtn:
                intent = new Intent(getActivity(), ViewJelaActivity.class);
                startActivity(intent);
                break;

            case R.id.logoutBtn:
                databaseAccess.open();
                databaseAccess.truncateUserTable();
                databaseAccess.close();
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
