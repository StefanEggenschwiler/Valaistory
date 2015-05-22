package hevs.ch.valaistory;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import hevs.ch.database.DatabaseAccess;
import hevs.ch.database.HistoricImage;
import hevs.ch.session.NoGPSDialog;
import hevs.ch.session.Session;

public class MainActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private DatabaseAccess dbAccess;
    private List<HistoricImage> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!((LocationManager) getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            NoGPSDialog.showNoGPSDialog(this);
        }
        dbAccess = new DatabaseAccess(this);
        dbAccess.writeSomeDummyData();
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.setCurrentCameraPosition(mMap.getCameraPosition());
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Session.setCurrentCameraPosition(mMap.getCameraPosition());
            }
        });

        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if(Session.hasValidCameraPosition()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(Session.getCurrentCameraPosition().target));
        } else {
            // Coordinates of Technopole
            LatLng latLng = new LatLng(46.28276878, 7.53949642);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        setUpDummyMarker();
    }

    private void setUpDummyMarker() {
        images = dbAccess.readAllImage();

        for(HistoricImage i : images) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(i.getLatitude(), i.getLongitude())).draggable(false).visible(true));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (HistoricImage img : images) {
                    if(img.getLatitude() == marker.getPosition().latitude && img.getLongitude() == marker.getPosition().longitude) {
                        Intent i = new Intent(getApplicationContext(), TestParseXMLActivity.class);
                        i.putExtra("imageUrl", img.getUrl());
                        startActivity(i);
                    }
                }

                return false;
            }
        });

    }
}
