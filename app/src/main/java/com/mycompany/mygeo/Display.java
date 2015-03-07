package com.mycompany.mygeo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.StringTokenizer;


public class Display extends FragmentActivity
        implements OnMapReadyCallback {
    //GoogleMap gmap;
    LatLng mypos;
    double lat;
    double lng;
    String formataddress = "";
Button street;
Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
       //SupportMapFragment mf= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
       //gmap=mf.getMap();
       // gmap = mf.getMap();
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainGeoActive.EXTRA_MESSAGE);
        httpHandler req=new httpHandler();
        String message2="";
        req.execute(message);
        try{
            message2= req.get();
        }
        catch (Exception e)
        {

        }
        StringTokenizer tk=new StringTokenizer(message2);
        String strlat=tk.nextToken(" ");
        String strlng=tk.nextToken(" ");
        lat=Double.parseDouble(strlat);
        lng=Double.parseDouble(strlng);
        while(tk.hasMoreTokens()) {
            formataddress = formataddress + tk.nextToken(" ") + " ";
        }
        Log.d("formataddress:", " "+formataddress);
        mypos=new LatLng(lat, lng);
        SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);
        street= (Button)findViewById(R.id.street);
        street.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + lat + "," + lng);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        search= (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lng+"?q=restaurants");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
      /*  Intent intent = getIntent();
        MarkerOptions mOptions = new MarkerOptions();
        mOptions.position(new LatLng(lat, lng));
        gmap.addMarker(mOptions);*/
            //message2 = intent.getStringExtra(MainGeoActive.EXTRA_MESSAGE);
       /* TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(strlng);

        // Set the text view as the activity layout
        setContentView(textView);*/
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(mypos)
                .title(formataddress));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mypos, 15));
       /* LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(mypos);
        LatLngBounds bounds=builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 70);
        map.moveCamera(cu);*/
    }




    public void viewStreet(){
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" +lat+","+lng);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void process(String x)
    {

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(x);

        // Set the text view as the activity layout
        setContentView(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
