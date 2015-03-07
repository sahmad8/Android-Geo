package com.mycompany.mygeo;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Saad on 3/6/2015.
 */
public class httpHandler extends AsyncTask<String, Void, String> {

    @Override

    protected String doInBackground(String... urls) {
        String json =null;
        JSONObject jObj=null;
        String message=null;
        StringBuilder stringBuilder = new StringBuilder();
        String query=urls[0];
        try {
            //------------------>>
            HttpGet httppost = new HttpGet("https://maps.googleapis.com/maps/api/geocode/json?address="+query);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            String loctype=((JSONArray)jsonObject.get("results")).getJSONObject(0).
                    getString("formatted_address");
             Log.d("loctypue:", ""+loctype);
            Log.d("latitude", "" + lat);
            Log.d("longitude", "" + lng);
            message = ""+lat + " " + lng+" "+loctype;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
       /* try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);
            HttpResponse response=httpclient.execute(request);
            /*HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
             catch (ClientProtocolException e) {
              e.printStackTrace();
            } catch (IOException e) {
                 e.printStackTrace();
        }*/
    }

    @Override
    protected void onPostExecute(String result) {
        // DisplayLocation dp =new DisplayLocation();
        //dp.process(result);
    }



}
