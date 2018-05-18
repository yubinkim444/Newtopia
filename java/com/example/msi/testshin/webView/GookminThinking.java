package com.example.msi.testshin.webView;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.msi.testshin.R;

public class GookminThinking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gookmin_thinking);

        Uri uri = Uri.parse("https://idea.epeople.go.kr/main/main.do");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    }

