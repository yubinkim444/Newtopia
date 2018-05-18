package com.example.msi.testshin;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.icu.lang.UProperty.AGE;
import static com.example.msi.testshin.R.id.join;

/**
 * Created by 박성균 on 2015-04-11.
 */
public class Member extends AppCompatActivity{
    EditText NAME,PASS,PASSSIGN,ID,SEX,AGE;
    String Tname, Tpass, Tpasssign,Tid,Tsex, Tage;
    String myResult;
    boolean loginSuccess;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_member);
        NAME = (EditText) findViewById(R.id.name);
        PASS = (EditText) findViewById(R.id.password);
        PASSSIGN = (EditText) findViewById(R.id.passsign);
        ID = (EditText) findViewById(R.id.id);
        SEX = (EditText) findViewById(R.id.sex);
        AGE = (EditText) findViewById(R.id.age);

        loginSuccess = false;
        Button join = (Button) findViewById(R.id.join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tname = NAME.getText().toString();
                Tpass = PASS.getText().toString();
                Tpasssign = PASSSIGN.getText().toString();
                Tsex = SEX.getText().toString();
                Tage = AGE.getText().toString();
                Tid = ID.getText().toString();
                if (Tname.length()<2) {
                    Toast.makeText(getApplicationContext(), "이름을 정확하게 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                } else if (Tpass.length() <4) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 4자리 이상 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                } else if (Tpasssign.length() <4) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                } /*else if (Tnum.length() <4 || Tname.equals(Cnum)) {
                    Toast.makeText(getApplicationContext(), "ID는 4자리이상 입력하세요",
                            Toast.LENGTH_SHORT).show();
                }*/ /*else if (Tmajor.length() <3) {
                    Toast.makeText(getApplicationContext(), "학과이름을 정확히 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                } */else {
                    Connect cn = new Connect();
                    cn.start();
                    try {
                        cn.join();
                    }
                    catch (Exception e){
                        Toast.makeText(getApplication(), Tname + "interrupt occur", Toast.LENGTH_SHORT).show();

                    }
                        if(loginSuccess) {
                            Toast.makeText(getApplication(), Tname + "님 회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplication(), Tname + "login fail", Toast.LENGTH_SHORT).show();
                        }

                }

            } });
    }
    class Connect extends Thread{
        public void run(){
            try {
                //--------------------------
                //   URL 설정하고 접속하기
                //--------------------------
                URL url = new URL("http://220.76.187.135/Newtopia/NewtopiaSignUp.php");       // URL 설정
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
                buffer.append("name").append("=").append(Tname).append("&");
                buffer.append("id").append("=").append(Tid).append("&");
                buffer.append("gender").append("=").append(Tsex).append("&");
                buffer.append("age").append("=").append(Tage).append("&");                 // php 변수에 값 대입
                buffer.append("pw").append("=").append(Tpass);
                Log.d("BUFFER",buffer.toString());
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
                // 전송결과를 전역 변수에 저장
                synchronized (this) {
                    // 전송결과를 전역 변수에 저장
                    if (!myResult.equals("")) {
                        loginSuccess = true;
                        Log.d("login Success", myResult);
                    }
                    Log.d("Result", myResult);
                }
                Log.d("Result",myResult);
            } catch (MalformedURLException e) {
                //
            } catch (IOException e) {
                //
            } // try
        } // HttpPostData


    } // HttpPostData
}