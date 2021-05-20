package com.example.quickbug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    String[] items = {"IT 융합대학","글로벌센터","예술대학/공과대학","대학원","바이오나노연구원","비전타워/법대/공대2","산학협력관","가천관","교육대학원","중앙도서관","학생회관","기숙사"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //출발지 스피너
        Spinner departure = (Spinner)findViewById(R.id.departure);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departure.setAdapter(adapter1);

        //도착지 스피너
        Spinner destination = (Spinner)findViewById(R.id.destination);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destination.setAdapter(adapter2);


        departure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                //스피너 값 선택했을 때
            }

            @Override
            public void onNothingSelected(AdapterView adapterView){

            }
        });

        destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                //스피너 값 선택했을 때
            }
            @Override
            public void onNothingSelected(AdapterView adapterView){

            }
        });

        //지도 버튼
        ImageButton map = (ImageButton)findViewById(R.id.imageButton);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //길찾기 버튼
        Button findPath = (Button)findViewById(R.id.btnCompute);
        findPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //QR코드 버튼
        Button qr = (Button)findViewById(R.id.btnQR);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, qrCheck.class);
                startActivity(intent);
            }
        });
    }
}