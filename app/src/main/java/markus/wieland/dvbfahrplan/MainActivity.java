package markus.wieland.dvbfahrplan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.ui.departures.DepartureActivity;
import markus.wieland.dvbfahrplan.ui.map.Coordinate;
import markus.wieland.dvbfahrplan.ui.map.MapView;
import markus.wieland.dvbfahrplan.ui.routes.RouteActivity;

public class MainActivity extends DefaultActivity {

    private MapView mapView;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void bindViews() {
        mapView = findViewById(R.id.mapView);
    }

    @Override
    public void initializeViews() {

        findViewById(R.id.departure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DepartureActivity.class));
            }
        });

        findViewById(R.id.route).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RouteActivity.class));
            }
        });

    }


    public LocationListener locationListener;
    private LocationManager locationManager;

    @Override
    public void execute() {

        //startActivity(new Intent(this, RouteActivity.class).putExtra(DepartureActivity.DEPARTURE_STOP_ID, "33000084"));


        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String TAG = "test";
                String longitude = "Longitude: " + location.getLongitude();
                Log.v(TAG, longitude);
                String latitude = "Latitude: " + location.getLatitude();
                Log.v(TAG, latitude);

                /*------- To get city name from coordinates -------- */
                String cityName = null;
                Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses = gcd.getFromLocation(location.getLatitude(),
                            location.getLongitude(), 1);
                    if (addresses.size() > 0) {
                        System.out.println(addresses.get(0).getLocality());
                        cityName = addresses.get(0).getLocality();
                        String street = addresses.get(0).getAddressLine(0);
                        Log.e("TAG",street);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                        + cityName;

                mapView.show(new Coordinate(location.getLatitude(), location.getLongitude()));

                Log.e(TAG, s);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        // TODO shimmer
        // TODO Abfahrtszeiten
        // TODO fußweg am Anfang oder am ende
        // TODO Empty Messages
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}