package com.example.msi.testshin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.msi.testshin.login.KakaoSignupActivity;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;


public class InitActivity extends  Info {
    EditText idText;
    EditText passText;
    String Id;
    String Pass;

    private SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*if (getIntent().getExtras() == null) {
            startActivity(new Intent(this, .class));
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        //Session.initialize(this, AuthType.KAKAO_TALK);

        idText = (EditText) findViewById(R.id.id);
        passText = (EditText) findViewById(R.id.pass);
        initControl();

        callback = new SessionCallback();                  // 이 두개의 함수 중요함
        Session.getCurrentSession().addCallback(callback);


    }




        private Button button_login, button_register, button_goMain;

    public void login(View v) {

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT name, num, pass FROM " + tableName, null);
            int count = cursor.getCount();
            for(int i=0;i<count;i++) {
                cursor.moveToNext();

                Cname = cursor.getString(0);
                Cnum = cursor.getString(1);
                Cpass = cursor.getString(2);
            }
            Id = idText.getText().toString();
            Pass = passText.getText().toString();
            if (Id.equals(Cnum) && Pass.equals(Cpass)) {
                Intent main = new Intent(getApplication(), NewsBoard.class);
                main.putExtra("splash", "splash");
                startActivity(main);
                Toast.makeText(getApplicationContext(), Cname + "님 환영합니다.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "정확한 정보를 입력하세요.",
                        Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }

    }


    public void member(View view){

        Intent member = new Intent(getApplication(), Member.class);
        member.putExtra("splash", "splash");
        startActivity(member);

    }

    private void initControl() {
        button_login = (Button) findViewById(R.id.login);
        button_register= (Button) findViewById(R.id.button_register);

//        button_goMain = (Button) findViewById(R.id.button_goMain);


/*        button_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(InitActivity.this, Login.class);
                InitActivity.this.startActivity(intent);

            }
        });*/

        button_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(InitActivity.this, Member.class);
                InitActivity.this.startActivity(intent);

            }
        });
//        button_goMain.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//
//                Intent intent = new Intent(InitActivity.this, MainActivity.class);
//                InitActivity.this.startActivity(intent);
//
//            }
//        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

private class SessionCallback implements ISessionCallback {

    @Override
    public void onSessionOpened() {

        redirectSignupActivity();  // 세션 연결성공 시 redirectSignupActivity() 호출
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        if(exception != null) {
            Logger.e(exception);
        }
        setContentView(R.layout.activity_init); // 세션 연결이 실패했을때
    }                                            // 로그인화면을 다시 불러옴
}

    protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }






}

