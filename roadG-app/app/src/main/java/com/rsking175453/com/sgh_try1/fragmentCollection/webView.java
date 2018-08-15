package com.rsking175453.com.sgh_try1.fragmentCollection;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rsking175453.com.sgh_try1.R;

/**
 * Created by user on 09/03/2018.
 */

public class webView extends Fragment{

    WebView webView;
    View view;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.web_view, container, false);
         webView = (WebView) view.findViewById(R.id.mWebView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarWeb);

        initWebView();
        webView.loadUrl("https://rsking175453.000webhostapp.com/hack/getcsv.html");
        Log.d("loaded","ji");

        WebSettings webs = webView.getSettings();
        webs.setJavaScriptCanOpenWindowsAutomatically(true);
        return view;
    }




//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("");

//        url = getIntent().getStringExtra("url");


    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
             //   invalidateOptionsMenu();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
               // invalidateOptionsMenu();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
                //invalidateOptionsMenu();
            }
        });
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.browser, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
//        if (!webView.canGoBack()) {
//            menu.getItem(0).setEnabled(false);
//            menu.getItem(0).getIcon().setAlpha(130);
//        } else {
//            menu.getItem(0).setEnabled(true);
//            menu.getItem(0).getIcon().setAlpha(255);
//        }
//
//        if (!webView.canGoForward()) {
//            menu.getItem(1).setEnabled(false);
//            menu.getItem(1).getIcon().setAlpha(130);
//        } else {
//            menu.getItem(1).setEnabled(true);
//            menu.getItem(1).getIcon().setAlpha(255);
//        }
//
//        return true;
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//
//        if (item.getItemId() == R.id.action_back) {
//            back();
//        }
//
//        if (item.getItemId() == R.id.action_forward) {
//            forward();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void back() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        }
//    }
//
//    private void forward() {
//        if (webView.canGoForward()) {
//            webView.goForward();
//        }
//    }
//
//    class MyWebChromeClient extends WebChromeClient {
//    Context context;
//
//        public MyWebChromeClient(Context context) {
//            super();
//            this.context = context;
//        }
//    }
////
//
//    }



}

