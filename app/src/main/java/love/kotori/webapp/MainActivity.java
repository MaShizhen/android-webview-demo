package love.kotori.webapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.JsResult;

public class MainActivity extends AppCompatActivity {

    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();//执行初始化函数
        webView.loadUrl("https://www.google.com/search?q=%E5%BE%AE%E4%BF%A1%E7%BD%91%E9%A1%B5%E7%89%88&oq=%E5%BE%AE%E4%BF%A1%E7%BD%91%E9%A1%B5%E7%89%88&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIGCAEQABgeMgYIAhAAGB4yBggDEAAYHjIJCAQQABgeGPEEMgYIBRAAGB4yBggGEAAYHjIGCAcQRRg90gEIMTgxNWowajSoAgCwAgA&sourceid=chrome&ie=UTF-8");
        // webView.loadUrl("https://baymax-app-stg.fpdev.tech/h5web/basic-communication/update/info?type=email&auth-token=cclwX0fIbqzVTOqDodhJE%2F1oSkUFEKqql0afA%3D%3D&lang=zh-CN");
        // webView.loadUrl("https://mobile.ant.design/~demos/button-demo1");
    }

    public void init() {
        webView = (WebView) findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);//使用JavaScript
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //webview.setScrollBarStyle(0);

        // 设置 WebChromeClient 以支持 alert() 函数
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                // 处理 alert 弹窗
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();

                return true; // 表明我们处理了弹窗
            }
        });

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 页面加载完成后注入 JavaScript 代码
                // webView.evaluateJavascript("javascript:alert('Webview信息：'+navigator.userAgent);", null);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回键
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            ConfirmExit();//按了返回键，但已经不能返回，则执行退出确认
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ConfirmExit() {//退出确认
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("退出");
        ad.setMessage("确定要退出吗?");
        ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {//退出按钮
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MainActivity.this.finish();//关闭activity

            }
        });
        ad.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //不退出不用执行任何操作
            }
        });
        ad.show();//显示对话框
    }

}
