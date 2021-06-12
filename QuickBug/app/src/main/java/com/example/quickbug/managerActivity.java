package com.example.quickbug;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class managerActivity extends AppCompatActivity {

    EditText mngAth;
    Button btnAth;
    RadioGroup radioGroup;
    RadioButton radioStop;
    RadioButton radioRun;
    //임의의 인증 코드
    int AUTHENTICATION_CODE = 3921;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        mngAth = (EditText)findViewById(R.id.mngAth);
        btnAth = (Button)findViewById(R.id.btnAth);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioStop = (RadioButton) findViewById(R.id.radioStop);
        radioRun = (RadioButton) findViewById(R.id.radioRun);


        //Click '인증하기' button
        btnAth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int code = Integer.parseInt(mngAth.getText().toString());

                if(code == AUTHENTICATION_CODE){

                    if(radioStop.isChecked()){
                        //Check '운행 중단'

                        Log.i("check","stop");
                    }
                    if(radioRun.isChecked()) {
                        //Check '운행 중'

                        Log.i("check","run");
                    }

                    //인증코드 맞으면 수정 후 main activity로
                    Toast.makeText(getApplicationContext(), "수정되었습니다", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else if(code != AUTHENTICATION_CODE){
                    Toast.makeText(getApplicationContext(), "인증코드가 틀립니다!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
