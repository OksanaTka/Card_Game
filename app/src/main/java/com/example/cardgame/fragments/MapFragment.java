package com.example.cardgame.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cardgame.R;
import com.example.cardgame.objects.Record;
import com.example.cardgame.objects.TopTen;
import com.example.cardgame.utils.MySPV;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment{

    private TopTen scoreBoard;
    public static GoogleMap mMap;
    private List<Marker> marker_list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        Intent i = getActivity().getIntent();
        scoreBoard = (TopTen) i.getSerializableExtra("SCORE_BOARD");
        //create array of markers
        marker_list = new ArrayList<Marker>();


        SupportMapFragment SupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        //add marker to map
        SupportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap= googleMap;
                for (Record record : scoreBoard.getRecords()) {
                    if (record.getLat() != 0 || record.getLng() != 0) {
                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(record.getLat(), record.getLng()))
                                .title(record.getName()));
                        //add marker to array
                        marker_list.add(marker);
                    }
                }
            }
        });
        return view;
    }

    /**
     * @param id- the id of item that was clicked on the view list
     */
    public void showMarker(int id){
        Marker marker = marker_list.get(id);
        marker.showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15f));
    }
}