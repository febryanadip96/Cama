package com.example.biyan.ubama.messaging;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Biyan on 11/25/2017.
 */

public class SendToken {

    public static void sendRegistrationToServer(final Context context, final String refreshedToken){
        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("token", refreshedToken+"");
        String url = UrlUbama.USER_SEND_TOKEN;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response Token", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", UserToken.getToken(context));
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(request);
    }

}
