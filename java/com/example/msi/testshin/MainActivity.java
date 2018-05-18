package com.example.msi.testshin;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.msi.testshin.webView.GogakActivity;
import com.example.msi.testshin.webView.GookminThinking;
import com.example.msi.testshin.webView.Sinmoongo;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<HashMap<String,String>> boardList;
    String[] jsonName = {"newsUrl", "option", "subject", "field", "date","boardId","memberCode","name","pros","cons","good"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Animation scaleani = AnimationUtils.loadAnimation(this,R.anim.scale_ani);
//        startAnimation(scaleani);

//        //ImageView Rounding
//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
////        GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.background_rounding);
//
//        imageView.setBackground(new ShapeDrawable(new OvalShape()));
//        imageView.setClipToOutline(true);
//        //

        boardList = new ArrayList<HashMap<String, String>>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView listview ;
        final ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);
//----------------------------


    GetBoardData board = new GetBoardData();
        board.start();
        try{
            board.join();
        }
        catch (Exception e){

        }

        int size = boardList.size();
        for(int i=0; i<size; i++){
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_menu_camera),
                    boardList.get(i).get("subject"), boardList.get(i).get("name"),  boardList.get(i).get("option"),boardList.get(i).get("date")) ;
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                Drawable iconDrawable = item.getIcon() ;


                Intent intent = new Intent(getApplicationContext(),BoardMain.class);
                for (int j = 0; j < jsonName.length; j++) {
                    intent.putExtra(jsonName[j],boardList.get(position).get(jsonName[j]));
                }
                startActivity(intent);

            }
        }) ;


    }

//
//    public void onItemClick
//            (AdapterView<?> parent, View view, int position, long id) {
//
//    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void redirectLoginActivity() {
        final Intent intent = new Intent(this, InitActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
    private void onClickLogout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                redirectLoginActivity();
            }
        });
    }
    private WebView webView;

    private void onClickSinmoongo() {
        final Intent intent = new Intent(this, Sinmoongo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
    private void onClickThinking() {
        final Intent intent = new Intent(this, GookminThinking.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void onClickGogek() {
        final Intent intent = new Intent(this, GogakActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_gunwi) {
            Intent openAboutActivityIntent = new Intent(MainActivity.this, InitActivity.class);
            startActivity(openAboutActivityIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_toron) {


        } else if (id == R.id.nav_alram) {


        } else if (id == R.id.nav_settings) {
            onClickShare();
        } else if (id == R.id.nav_gogek) {
            onClickGogek();
        } else if (id == R.id.nav_logout) {
            onClickLogout();
        }
        else if (id == R.id.nav_sinmoongo){
            onClickSinmoongo();
        }
        else if(id == R.id.nav_thinking){
            onClickThinking();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onClickShare(){

        Intent intent = new Intent(getApplicationContext(),NewsBoard.class);
        startActivity(intent);

    }
    class GetBoardData extends Thread{
        String myResult;
        public void run() {
            try {
                //--------------------------
                //   URL 설정하고 접속하기
                //--------------------------
                URL url = new URL("http://220.76.187.135/Newtopia/NewtopiaGetAllBoards.php");       // URL 설정
                HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
                //--------------------------
                //   전송 모드 설정 - 기본적인 설정이다
                //--------------------------
                http.setDefaultUseCaches(false);
                http.setDoInput(true);                         // 서버에서 읽기 모드 지정
                http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
                http.setRequestMethod("POST");         // 전송 방식은 POST

                // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
                http.setRequestProperty("content-type", "application/x-www-form-urlencoded");


                //--------------------------
                //   서버에서 전송받기
                //--------------------------
                InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "utf8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                    builder.append(str);                     // View에 표시하기 위해 라인 구분자 추가
                }
                synchronized (this) {
                    myResult = builder.toString();
                    Log.d("Result", myResult);
                }

                JSONArray arrResults = new JSONArray(myResult);
                int iCount = arrResults.length();


                for (int i = 0; i < iCount; i++) {
                    JSONObject obj = arrResults.getJSONObject(i);
                    HashMap<String, String> h = new HashMap<>();
                    for (int j = 0; j < jsonName.length; j++) {
                        h.put(jsonName[j], obj.getString(jsonName[j]));
                        Log.d(jsonName[j], obj.getString(jsonName[j]));
                    }
                    boardList.add(h);
                }
            } catch (Exception e) {

            }
        }


    }

}