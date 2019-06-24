package com.developersian.webService.volley;

import androidx.annotation.*;
import com.android.volley.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

import java.nio.charset.*;
import java.util.*;

public class GsonRequest<T> extends Request<T> {
    private Gson gson;
    private Response.Listener<T> responseListener;
    private String body;

    public GsonRequest(int method, String url, Response.Listener<T> responseListener, @Nullable Response.ErrorListener listener) {
        super(method, url, listener);
        this.responseListener = responseListener;
        setRetryPolicy();
    }

    public GsonRequest(int method, String url, Response.Listener<T> responseListener) {
        super(method, url, error -> {
        });
        this.responseListener = responseListener;
        setRetryPolicy();
    }

    public GsonRequest(int method, String url, String body, Response.Listener<T> responseListener) {
        super(method, url, error -> {
        });
        this.body = body;
        this.responseListener = responseListener;
        setRetryPolicy();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String responseString = new String(response.data, StandardCharsets.UTF_8);
            T result = gson.fromJson(responseString, new TypeToken<T>() {
            }.getType());
            return Response.success(result, null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError());
        }
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (body == null) return super.getBody();
        else return body.getBytes();
    }

    @Override
    protected void deliverResponse(T response) {
        responseListener.onResponse(response);

    }

    private void setRetryPolicy() {
        setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 3;
            }

            @Override
            public void retry(VolleyError error) { }
        });
    }
}