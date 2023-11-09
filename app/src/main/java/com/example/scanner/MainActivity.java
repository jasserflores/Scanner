package com.example.scanner;

import static android.webkit.URLUtil.isValidUrl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button scan_btn;
    private TextView result_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scan_btn = findViewById(R.id.scan_btn);
        result_txt = findViewById(R.id.result_txt);

        scan_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setPrompt("Escanea un codigo de barras o QR");
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String scanContent = result.getContents();
            result_txt.setText("Resultado del escaneo: "+ scanContent);

            if(isValidUrl(scanContent)){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanContent));
                startActivity(browserIntent);
            }
        }
    }

    private boolean isValidUrl(String url){
        return url != null && (url.startsWith("http://") || url.startsWith("https//"));
    }
}