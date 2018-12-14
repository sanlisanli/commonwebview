package me.mikasa.commonwebview.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import butterknife.BindView;
import me.mikasa.commonwebview.R;
import me.mikasa.commonwebview.base.BaseToolbarActivity;

public class WebViewActivity extends BaseToolbarActivity{
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.loadingProgress)
    ProgressBar loadingProgress;
    @BindView(R.id.toolbar_switch)
    ImageView iv_switch;

    private String receivedUrl;
    private Context mContext;
    private WebSettings webSettings;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initData() {
        mContext=this;
        Intent intent=getIntent();
        receivedUrl=intent.getStringExtra("link");
        iv_switch.setVisibility(View.VISIBLE);
        iv_switch.setImageResource(R.mipmap.icon_switch);
    }

    @Override
    protected void initView() {
        webSettings=webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(chromeClient);
        webView.loadUrl(receivedUrl);
    }

    @Override
    protected void initListener() {
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final WebView.HitTestResult hitTestResult=webView.getHitTestResult();
                if (hitTestResult.getType()==WebView.HitTestResult.IMAGE_TYPE||hitTestResult.getType()==WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE){
                    Intent intent=new Intent(mContext,PhotoViewActivity.class);
                    intent.putExtra("imgurl",hitTestResult.getExtra());
                    startActivity(intent);
                }
                return false;
            }
        });
        iv_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuPopup();
            }
        });
    }
    private void showMenuPopup(){
        View menu= LayoutInflater.from(this).inflate(R.layout.popup_webview,null,false);
        PopupWindow popupWindow=new PopupWindow(menu,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        LinearLayout menuBrowser=(LinearLayout)menu.findViewById(R.id.menu_browser);
        LinearLayout menuBigmap=(LinearLayout)menu.findViewById(R.id.menu_bigimg);
        menuBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInBrowser();
            }
        });
        menuBigmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("长按图片可查看大图");
            }
        });
        popupWindow.showAsDropDown(iv_switch);
    }
    private void openInBrowser(){
        Uri uri=Uri.parse(receivedUrl);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    WebViewClient webViewClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);//??
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };
    WebChromeClient chromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            loadingProgress.setProgress(newProgress);
            if (newProgress==100){
                loadingProgress.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

}
