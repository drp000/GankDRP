package com.drp.gankdrp.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;

import com.drp.gankdrp.databinding.LayoutWebviewBinding;
import com.orhanobut.logger.Logger;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewLayout extends RelativeLayout {
    private String mUrl;
    private LayoutWebviewBinding viewBinding;

    public WebViewLayout(Context context) {
        this(context, null);
    }

    public WebViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initWebViewSettings();
        initListener();
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewBinding = LayoutWebviewBinding.inflate(inflater, this, true);
    }

    private void initWebViewSettings() {
        WebSettings settings = viewBinding.webView.getSettings();
        settings.setJavaScriptEnabled(true); // 默认false，设置true后我们才能在WebView里与我们的JS代码进行交互
        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 设置JS是否可以打开WebView新窗口

        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 支持手势缩放
        settings.setDisplayZoomControls(false); // 不显示缩放按钮

        settings.setDatabaseEnabled(true);
        settings.setSaveFormData(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setAppCacheEnabled(true);

        settings.setUseWideViewPort(true); // 将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); // 自适应屏幕
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setPluginState(WebSettings.PluginState.ON);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        viewBinding.webView.setHorizontalScrollBarEnabled(false);
        viewBinding.webView.setScrollbarFadingEnabled(true);
        viewBinding.webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        viewBinding.webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
    }

    private void initListener() {
        viewBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                viewBinding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                viewBinding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                viewBinding.progressBar.setVisibility(View.GONE);
                Logger.e("" + error.getDescription());
            }
        });

        viewBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
                    viewBinding.progressBar.setVisibility(View.GONE);
                } else {
                    if (viewBinding.progressBar.getVisibility() == View.GONE) {
                        viewBinding.progressBar.setVisibility(View.VISIBLE);
                    }
                    viewBinding.progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);

            }
        });


    }

    public void loadUrl(String url) {
        mUrl = url;
        viewBinding.webView.loadUrl(url);
    }

    public void setProgressDrawable(@DrawableRes int id) {
        viewBinding.progressBar.setProgressDrawable(viewBinding.progressBar.getContext().getResources().getDrawable(id));
    }

    public WebView getWebView() {
        return viewBinding.webView;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean goBack() {
        if (viewBinding.webView.canGoBack()) {
            viewBinding.webView.goBack();
            return true;
        }
        return false;
    }

    public void onResume() {
        viewBinding.webView.getSettings().setJavaScriptEnabled(true);
        viewBinding.webView.onResume();
    }

    public void onPause() {
        viewBinding.webView.getSettings().setJavaScriptEnabled(false);
        viewBinding.webView.onPause();
    }

    public void onDestroy() {
        viewBinding.webView.setVisibility(GONE);
        viewBinding.webView.destroy();
    }

}
