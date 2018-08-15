package com.rsking175453.com.sgh_try1;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
//
//    URL url = new URL("http://yoururl.com");
//    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//conn.setReadTimeout(10000);
//        conn.setConnectTimeout(15000);
//        conn.setRequestMethod("POST");
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
//
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("firstParam", paramValue1));
//        params.add(new BasicNameValuePair("secondParam", paramValue2));
//        params.add(new BasicNameValuePair("thirdParam", paramValue3));
//
//        OutputStream os = conn.getOutputStream();
//        BufferedWriter writer = new BufferedWriter(
//        new OutputStreamWriter(os, "UTF-8"));
//        writer.write(getQuery(params));
//        writer.flush();
//        writer.close();
//        os.close();
//
//        conn.connect();
//private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
//{
//    StringBuilder result = new StringBuilder();
//    boolean first = true;
//
//    for (NameValuePair pair : params)
//    {
//        if (first)
//            first = false;
//        else
//            result.append("&");
//
//        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
//        result.append("=");
//        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
//    }
//
//    return result.toString();
//}