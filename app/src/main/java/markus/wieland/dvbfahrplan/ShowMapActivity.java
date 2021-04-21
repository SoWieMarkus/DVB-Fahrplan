package markus.wieland.dvbfahrplan;

import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;
import markus.wieland.dvbfahrplan.ui.map.MapView;

public class ShowMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapView webView = new MapView(this);

        webView.getSettings().setJavaScriptEnabled(true);

        String test = getIntent().getStringExtra("test");

        /*webView.loadUrl("file:///android_asset/map.html");*/
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                DVBApi dvbApi = new DVBApi(ShowMapActivity.this);
                dvbApi.searchRoute(new APIResult<Routes>() {
                    @Override
                    public void onLoad(Routes routes) {
                        webView.showRoute(routes.getRouteList().get(0));
                    }
                },"33000016", "33000742");

                super.onPageFinished(view, url);
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("MyApplication", consoleMessage.message() + " -- From line "
                        + consoleMessage.lineNumber() + " of "
                        + consoleMessage.sourceId());
                return super.onConsoleMessage(consoleMessage);
            }
        });

        setContentView(webView);


    }
}
