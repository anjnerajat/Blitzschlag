package com.example.dell.blitzschlag.Venue;

import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.example.dell.blitzschlag.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener{

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private LatLng latLng;
    double lat=26.865211,lng=75.808089;

    private final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE };
    private int curMapTypeIndex = 1;

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title( getAddressFromLatLng( latLng ) );

        options.icon( BitmapDescriptorFactory.defaultMarker() );
        getMap().addMarker( options );
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title( getAddressFromLatLng( latLng ) );

        options.icon( BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher)) );

        getMap().addMarker( options );
    }

    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String address = "";
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
        } catch (IOException e ) {
        }

        return address;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        //mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient );
        //Location location=null;
        //location.setLatitude(lat);
        //location.setLongitude(lng);
        initCamera();
    }

    private void initCamera() {
        CameraPosition position = CameraPosition.builder()
                .target(latLng )
                .zoom( 16f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();

        getMap().animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        getMap().setMapType(MAP_TYPES[curMapTypeIndex]);
        getMap().setTrafficEnabled(true);
        getMap().setMyLocationEnabled(true);
        getMap().getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();

        //For the MNIT pyaauu
        latLng = new LatLng(lat,lng);
        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title(getAddressFromLatLng(latLng));
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker(options);

        //For Aurobindo Hostel
        latLng = new LatLng(26.862701, 75.820266);
        options = new MarkerOptions().position( latLng );
        options.title("Aurobindo Hostel");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //For MNIT Dispenary
        latLng = new LatLng(26.862131, 75.812190);
        options = new MarkerOptions().position( latLng );
        options.title("MNIT Dispensary");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //For MNIT Annapurna Canteen
        latLng = new LatLng(26.863242, 75.810715);
        options = new MarkerOptions().position( latLng );
        options.title("Annapurna Canteen");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //For MNIT Design Center
        latLng = new LatLng(26.864294, 75.810715);
        options = new MarkerOptions().position( latLng );
        options.title("Design Center");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //For MNIT Basketball Court
        latLng = new LatLng(26.861729, 75.814679);
        options = new MarkerOptions().position( latLng );
        options.title("MNIT Basketball Court");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //For MNIT Gargi Hostel
        latLng = new LatLng(26.864414, 75.815371);
        options = new MarkerOptions().position( latLng );
        options.title("Gargi Hostel");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //For MNIT Library
        latLng = new LatLng(26.862036,75.80881);
        options = new MarkerOptions().position( latLng );
        options.title("MNIT Library");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //For MNIT Central Lawn
        latLng = new LatLng(26.861492,75.808606);
        options = new MarkerOptions().position( latLng );
        options.title("MNIT Central Lawn");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //For MNIT Guest House
        latLng = new LatLng(26.866008,75.810784);
        options = new MarkerOptions().position( latLng );
        options.title("MNIT Guest House");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //For MNIT Air Toast
        latLng = new LatLng(26.861605,75.808273);
        options = new MarkerOptions().position( latLng );
        options.title("Air Toast");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //For MNIT Ashok Vatika
        latLng = new LatLng(26.863345,75.809614);
        options = new MarkerOptions().position( latLng );
        options.title("Ashok Vatika");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker( options );

        //initListeners();
    }

    private void initListeners() {
        getMap().setOnMapClickListener(this);
        getMap().setOnMarkerClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener( this );
    }
}
