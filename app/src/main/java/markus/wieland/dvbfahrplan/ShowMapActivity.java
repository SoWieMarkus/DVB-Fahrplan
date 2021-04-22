package markus.wieland.dvbfahrplan;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import markus.wieland.dvbfahrplan.api.models.routes.Route;
import markus.wieland.dvbfahrplan.ui.map.MapView;

public class ShowMapActivity extends AppCompatActivity {

    public static final String ROUTE = "markus.wieland.dvbfahrplan.ROUTE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapView webView = new MapView(this);

        String test = getIntent().getStringExtra(ROUTE);
        Route route = new Gson().fromJson(test, Route.class);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.showRoute(route);
            }
        });

        setContentView(webView);


    }
}
