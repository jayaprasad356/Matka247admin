package com.example.bigbillionadmin.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;


public class ApiConfig extends Application {
    static ApiConfig mInstance;
    public static final String TAG = ApiConfig.class.getSimpleName();
    RequestQueue mRequestQueue;


    public static String VolleyErrorMessage(VolleyError error) {
        String message = "";
        try {
            if (error instanceof NetworkError) {
                message = "Cannot connect to Internet...Please check your connection!";
            } else if (error instanceof ServerError) {
                message = "The server could not be found. Please try again after some time!";
            } else if (error instanceof AuthFailureError) {
                message = "Cannot connect to Internet...Please check your connection!";
            } else if (error instanceof ParseError) {
                message = "Parsing error! Please try again after some time!";
            } else if (error instanceof TimeoutError) {
                message = "Connection TimeOut! Please check your internet connection.";
            } else
                message = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void RequestToVolley(final VolleyCallback callback, final Activity activity, final String url, final Map<String, String> params, final boolean isProgress) {
        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        final ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        progressDisplay.hideProgress();

        if (isProgress)
            progressDisplay.showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            if (ApiConfig.isConnected(activity))
                callback.onSuccess(true, response);
            if (isProgress)
                progressDisplay.hideProgress();
        }, error -> {
            if (isProgress)
                progressDisplay.hideProgress();
            if (ApiConfig.isConnected(activity))
                callback.onSuccess(false, "");
            String message = VolleyErrorMessage(error);
            if (!message.equals(""))
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        }) {


            @Override
            protected Map<String, String> getParams() {

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
        ApiConfig.getInstance().getRequestQueue().getCache().clear();
        ApiConfig.getInstance().addToRequestQueue(stringRequest);
    }


    public static void RequestToVolley(final VolleyCallback callback, final Activity activity, final String url, final Map<String, String> params, final Map<String, String> fileParams) {
        if(isConnected(activity)) {
            VolleyMultiPartRequest multipartRequest = new VolleyMultiPartRequest(url,
                    response -> callback.onSuccess(true, response),
                    error -> callback.onSuccess(false, "")) {
                @Override
                public Map<String, String> getDefaultParams() {
                    return params;
                }


                @Override
                public Map<String, String> getFileParams() {
                    return fileParams;
                }
            };

            multipartRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getInstance().getRequestQueue().getCache().clear();
            getInstance().addToRequestQueue(multipartRequest);
        }
    }

    public static synchronized ApiConfig getInstance() {
        return mInstance;
    }

    public static Boolean isConnected(final Activity activity) {
        boolean check = false;
        try {
            ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                check = true;
            } else {
                Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


}