package migra.br.smart.atvConfig;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import migra.br.smart.R;

public class AtvPortalOnline extends AppCompatActivity {

    WebView webAtvPortalOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_portal_online);

        webAtvPortalOnline = (WebView) findViewById(R.id.webAtvPortalOnline);
        webAtvPortalOnline.getSettings().setJavaScriptEnabled(true);
        webAtvPortalOnline.loadUrl("http://www.fileo.esy.es");



    }
}
