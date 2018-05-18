package com.example.msi.testshin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.R.attr.type;
import static com.example.msi.testshin.Login.myCode;
import static com.example.msi.testshin.login.KakaoSignupActivity.kakaoNickname;

/**
 * Created by msi on 2017-02-25.
 */

public class NewsBoard extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    EditText edit_newsUrl;
    Spinner optionSpinner;
    EditText edit_subject;
    EditText edit_field;
    String option;
    boolean addSuccess;
    String[] items = { "사회", "정치", "문화", "경제", "연예", "IT", "생활","방송","스포츠","자유방" };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_board);
        edit_newsUrl = (EditText)findViewById(R.id.textUrl);
        edit_subject = (EditText)findViewById(R.id.textSubject);
        edit_field = (EditText)findViewById(R.id.textField);


        optionSpinner = (Spinner) findViewById(R.id.spinner);
        optionSpinner .setOnItemSelectedListener(this);
// adapter를 정의하고 items을 dropdown 형태로 붙인다.
        ArrayAdapter<String> aa= new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        aa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// spin에  adapter를 붙여넣는다.
        optionSpinner.setAdapter(aa);
        addSuccess = false;


    }



    public void onClickRegisterButton(View v){
        String newsUrl = edit_newsUrl.getText().toString() ;
        String subject = edit_subject.getText().toString();
        String field = edit_field.getText().toString();
        String name =  kakaoNickname ;

//--------------------------------------------------------------------------------------------------
        AddBoard addBoard = new AddBoard(newsUrl,subject,field,name);
        addBoard.start();
        try {
            addBoard.join();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Interrupt occur", Toast.LENGTH_SHORT).show();
        }
        if (addSuccess){
            Toast.makeText(getApplicationContext(), "Insert Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

//----------------------------------------------------------------------------------
        } else {
            Toast.makeText(getApplicationContext(), "Insert Fail", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        // TODO Auto-generated method stub
        // 특정한 spinner 내의 항목 호출
        option = items[arg2];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        // 선택이 해제될때
    }
    class AddBoard extends Thread{
        String newsUrl ;
        String subject ;
        String field ;
        String code;
        String myResult;
        AddBoard(String url,  String sub,String fld, String nm){
            newsUrl = url;
            subject = sub;
            field = fld;
        }
        public void run(){
            try {
                //--------------------------
                //   URL 설정하고 접속하기
                //--------------------------
                URL url = new URL("http://220.76.187.135/Newtopia/NewtopiaAddBoard.php");       // URL 설정
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
                buffer.append("url").append("=").append(newsUrl).append("&");
                buffer.append("option").append("=").append(option).append("&");
                buffer.append("subject").append("=").append(subject).append("&");
                buffer.append("field").append("=").append(field).append("&");
                buffer.append("memberCode").append("=").append(Integer.toString(myCode));

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
                    myResult = builder.toString();
                    // 전송결과를 전역 변수에 저장
                    if (!myResult.equals("")) {
                        addSuccess = true;
                        Log.d("add Success", myResult);
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

