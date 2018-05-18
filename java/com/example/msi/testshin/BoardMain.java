package com.example.msi.testshin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.msi.testshin.webView.PermissionRequester;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hansangjun on 2017. 2. 25..
 */
public class BoardMain extends AppCompatActivity {

    ImageView paserImage;
    HashMap<String,String> board;
    int boardId;

    TextView title;
    TextView url;
    TextView idText;
    TextView field;

    TextView prosText;
    TextView consText;
    HashMap<String,String> h;
    String[] jsonName = {"newsUrl", "option", "subject", "field", "date","boardId","memberCode","name","pros","cons","good"};
    //-----------------------------------------
    boolean agree;
    boolean checkOpinion;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.board_main);
        checkOpinion = false;
        title = (TextView)findViewById(R.id.discussiontitle);
        url = (TextView)findViewById(R.id.url);
        idText = (TextView)findViewById(R.id.nameText);
        field = (TextView)findViewById(R.id.fieldText);
        prosText=(TextView)findViewById(R.id.prosText);
        consText=(TextView)findViewById(R.id.consText);
        h = new HashMap<>();
        Intent intent = getIntent();
        for (int j = 0; j < jsonName.length; j++) {
            h.put(jsonName[j],intent.getStringExtra(jsonName[j]));
        }
        title.setText(h.get("subject"));
        url.setText(h.get("newsUrl"));
        idText.setText(h.get("name"));
        field.setText(h.get("field"));
        prosText.setText(h.get("pros")+"명 찬성");
        consText.setText(h.get("cons")+"명 반대");

    }

    public void onClickCommentButton(View v){
        if(checkOpinion) {
            Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
            intent.putExtra("boardId",h.get("boardId"));
            intent.putExtra("prosCons",agree);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"의견을 선택해 주세요.",Toast.LENGTH_SHORT).show();
        }
    }
    public void onAgree(View v){
        Toast.makeText(getApplicationContext(),"찬성을 선택하였습니다.",Toast.LENGTH_SHORT).show();
        agree = true;
        checkOpinion = true;
    }
    public void onDisAgree(View v){
        Toast.makeText(getApplicationContext(),"반대를 선택하였습니다.",Toast.LENGTH_SHORT).show();
        agree= false;
        checkOpinion = true;
    }
}