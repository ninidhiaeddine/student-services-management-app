package com.nini.studentservicesmanagementapp.data.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nini.studentservicesmanagementapp.shared.SharedPrefsKeys;

import java.nio.charset.StandardCharsets;

public class ApiService {
    protected final static String API_URL = "https://10.0.2.2:7093/api";
    protected final RequestQueue queue;
    protected final ObjectMapper mapper;
    protected String authorizationToken;

    public ApiService(Context context) {
        queue = Volley.newRequestQueue(context);
        mapper = new ObjectMapper();
        getAuthorizationToken(context);
    }

    private void getAuthorizationToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SharedPrefsKeys.SHARED_PREFS,
                Context.MODE_PRIVATE
        );
        prefs.getString(SharedPrefsKeys.AUTHORIZATION_TOKEN_KEY, "");
    }

    public static StringRequest makeGetStringRequest(String url, final VolleyCallback callback) {
        return new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    callback.onSuccess(response);
                },
                error -> {
                    callback.onError(error);
                });
    }

    public static StringRequest makePostStringRequest(String url, String jsonBody, final VolleyCallback callback) {
        return new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    callback.onSuccess(response);
                },
                error -> {
                    callback.onError(error);
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonBody.getBytes(StandardCharsets.UTF_8);
            }
        };
    }

    public static StringRequest makePutStringRequest(String url, String jsonBody, final VolleyCallback callback) {
        return new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    callback.onSuccess(response);
                },
                error -> {
                    callback.onError(error);
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonBody.getBytes(StandardCharsets.UTF_8);
            }
        };
    }
}
