package com.example.msi.testshin;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

import static com.example.msi.testshin.login.KakaoSignupActivity.kakaoNickname;

public class Login extends AppCompatActivity {
    EditText idText;
    EditText passText;
    String myResult;
    static int myCode;
    static String myName;
    boolean loginSuccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText = (EditText) findViewById(R.id.id);
        passText = (EditText) findViewById(R.id.pass);
       loginSuccess = false;
    }

    public void login(View v) {

      CheckIdAndPw login =  new CheckIdAndPw();
        login.start();
        try{
            login.join();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Interrupt occur",
                    Toast.LENGTH_SHORT).show();
        }

if(loginSuccess) {
    myName = idText.getText().toString();
    Toast.makeText(getApplicationContext(), myName+"님 환영합니다.",
            Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    startActivity(intent);
}
else{
    Toast.makeText(getApplicationContext(), "login fail",
            Toast.LENGTH_SHORT).show();
}
    }

    public void member(View view) {
        Intent intent = new Intent(getApplicationContext(), Member.class);

    startActivity(intent);

    }
    class CheckIdAndPw extends Thread{
        public void run(){
            try {
                //--------------------------
                //   URL 설정하고 접속하기
                //--------------------------
                URL url = new URL("http://220.76.187.135/Newtopia/NewtopiaLogin.php");       // URL 설정
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
                //   서버로 값 전송
                //--------------------------
                StringBuffer buffer = new StringBuffer();

                buffer.append("id").append("=").append(idText.getText().toString()).append("&");
                buffer.append("pw").append("=").append(passText.getText().toString());

                OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(),"utf8");
                PrintWriter writer = new PrintWriter(outStream);
                writer.write(buffer.toString());
                writer.flush();
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
                myResult = builder.toString();
                    if (myResult.equals("")) {
                        loginSuccess = false;
                    } else {
                        loginSuccess = true;
                      myCode = Integer.parseInt(myResult);
                        Log.d("Result", myResult);
                    }

            } catch (Exception e) {

            }
        }


    }
}