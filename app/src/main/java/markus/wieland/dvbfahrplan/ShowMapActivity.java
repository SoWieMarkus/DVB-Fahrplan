package markus.wieland.dvbfahrplan;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/map.html?c=12");

        setContentView(webView);


    }
}
