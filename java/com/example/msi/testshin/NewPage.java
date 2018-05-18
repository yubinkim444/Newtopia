package com.example.msi.testshin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class NewPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newpage);
        setTitle("안건등록하기");
    }
}
