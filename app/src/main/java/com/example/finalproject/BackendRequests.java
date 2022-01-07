package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class BackendRequests {

    public static void postRequest(String endpoint, Context context, Map<String, String> params, final RequestCallback callback) {
        String url = "http://10.0.2.2/HotelApp/" + endpoint;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("Backend Response", "RESPONSE = " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    callback.onResponse(null, !jsonObject.getBoolean("error"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("BackendError", error.getMessage());
                Toast.makeText(context,"Fail to get response = " + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        queue.add(request);
    }

    public static void getRequest(String endpoint, Context context, final RequestCallback callback) {
        String url = "http://10.0.2.2/HotelApp/" + endpoint;
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<JSONObject> result = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        result.add(response.getJSONObject(i));
                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                callback.onResponse(result, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }
}
