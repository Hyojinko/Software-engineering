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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                String id = "manager";
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(id);
                DatabaseReference timeRef = database.getReference("time");

                Date from = new Date();
                SimpleDateFormat nowDate = new SimpleDateFormat("yyyyMMddHHmmss");
                String nowTime = nowDate.format(from);
                String nowHour = nowTime.substring(8,10);
                String nowMinute = nowTime.substring(10,12);
                String nowSecond = nowTime.substring(12);


                if(code == AUTHENTICATION_CODE){

                    if(radioStop.isChecked()){
                        //Check '운행 중단'
                        myRef.setValue("운행 중단");
                        timeRef.setValue(nowTime);

                        Log.i("check","stop");




                    }
                    if(radioRun.isChecked()) {
                        //Check '운행 중'

                        myRef.setValue("운행 중");
                        timeRef.setValue(nowTime);

                        Log.i("check","run");
                    }

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
