package com.example.msi.testshin.webView;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.msi.testshin.R;

public class Sinmoongo extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinmoongo);


        Uri uri = Uri.parse("http://www.epeople.go.kr/jsp/user/po/filterOff/forum/UPoForumList.jsp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }




}
