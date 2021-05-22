package com.example.quickbug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.lang.reflect.Array;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView seoulweather;
    String[] items = {"IT 융합대학","글로벌센터","예술대학/공과대학","대학원","바이오나노연구원","비전타워/법대/공대2","산학협력관","가천관","교육대학원","중앙도서관","학생회관","기숙사"};
    private static final String TAG = "MainActivity";
    private MapView mapView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1000;
    private FusedLocationSource locationSource;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private NaverMap naverMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        CameraPosition cameraPosition = new CameraPosition(new LatLng(37.45087165240168, 127.12888504524742),16,20,0);
        NaverMapOptions options = new NaverMapOptions().camera(cameraPosition).mapType(NaverMap.MapType.Terrain).enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING).compassEnabled(true).scaleBarEnabled(true).locationButtonEnabled(true);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        seoulweather = (TextView) findViewById(R.id.seoulweather);
        String Item = "";
        try {
            URL url = new URL(
                    "http://www.kma.go.kr/XML/weather/sfc_web_map.xml");
            XmlPullParserFactory factory = XmlPullParserFactory
                    .newInstance();

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), "utf-8");

            String ItemName = "";
            String ItemContents = "";
            String badstate1 = "소나기";
            String badstate2 = "비";
            String badstate3 = "눈";
            String badstate4 = "비 또는 눈";
            String badstate5 = "눈 또는 비";
            String badstate6 = "천둥번개";


            boolean bSet = false;
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if (tag.equals("local")) {
                            ItemContents = "";
                            String state = parser.getAttributeValue(null,
                                    "desc");
                            String temperature = "섭씨 ";
                            temperature += parser.getAttributeValue(null,
                                    "ta");
                            temperature += "º";
                            ItemContents = "운행중" + " / " + state + " , " + temperature
                                    + "  ";

                            if (state.equals(badstate1) || state.equals(badstate2) || state.equals(badstate3) ||
                                    state.equals(badstate4) || state.equals(badstate5) || state.equals(badstate6)) {
                                ItemContents = "운행 중단" + " / " + state + " , " + temperature
                                        + "  ";
                            }
                            bSet = true;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        if (bSet) {
                            ItemName = "";
                            String region = parser.getText();
                            ItemName += region + "   - ";
                            if(region.equals("서울")){
                                Item = ItemContents;
                                Item += "\n";
                                bSet = false;
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
            seoulweather.setText(Item);
        } catch (Exception e) {
        }

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

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
    //지도상에 마커 표
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d(TAG, "onMapReady");
        this.naverMap = naverMap;
        // 지도상에 현위치 표시
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        //건물마다 마커 표시

        //가천관
        Marker gcmarker = new Marker();
        gcmarker.setPosition(new LatLng(37.45078969194221, 127.12887595771895));
        gcmarker.setMap(naverMap);

        //미래2관
        Marker m2marker = new Marker();
        m2marker.setPosition(new LatLng(37.45001906066103, 127.12858993867684));
        m2marker.setMap(naverMap);

        //미래1관
        Marker m1marker = new Marker();
        m1marker.setPosition(new LatLng(37.449795952342, 127.12812990821833));
        m1marker.setMap(naverMap);

        //창의관
        Marker cmarker = new Marker();
        cmarker.setPosition(new LatLng(37.44925222720644, 127.12850670247174));
        cmarker.setMap(naverMap);

        //비전타워
        Marker vmarker = new Marker();
        vmarker.setPosition(new LatLng(37.44975246993713, 127.12722948248846));
        vmarker.setMap(naverMap);

        //IT대학
        Marker itmarker = new Marker();
        itmarker.setPosition(new LatLng(37.451003316420696, 127.12715289011525));
        itmarker.setMap(naverMap);

        //글로벌 센터
        Marker glmarker = new Marker();
        glmarker.setPosition(new LatLng(37.451914, 127.127061));
        glmarker.setMap(naverMap);

        //웅지관
        Marker wmarker = new Marker();
        wmarker.setPosition(new LatLng(37.449417729151314, 127.12954248732703));
        wmarker.setMap(naverMap);

        //학생회관
        Marker smarker = new Marker();
        smarker.setPosition(new LatLng(37.45031021741935, 127.13020001963851));
        smarker.setMap(naverMap);

        //기술관
        Marker tmarker = new Marker();
        tmarker.setPosition(new LatLng(37.45037835673896, 127.13037704542226));
        tmarker.setMap(naverMap);

        //진리관
        Marker zmarker = new Marker();
        zmarker.setPosition(new LatLng(37.451250352699894, 127.12974464730218));
        zmarker.setMap(naverMap);

        //정의관
        Marker jmarker = new Marker();
        jmarker.setPosition(new LatLng(37.45133934844123, 127.1303944329334));
        jmarker.setMap(naverMap);

        //공학관
        Marker techmarker = new Marker();
        techmarker.setPosition(new LatLng(37.45160591647754, 127.1280979944333));
        techmarker.setMap(naverMap);

        //복지관
        Marker caremarker = new Marker();
        caremarker.setPosition(new LatLng(37.4514809759294, 127.12896145275238));
        caremarker.setMap(naverMap);

        //예음관
        Marker amarker = new Marker();
        amarker.setPosition(new LatLng(37.45170655219594, 127.12964482180288));
        amarker.setMap(naverMap);

        //창조관
        Marker cremarker = new Marker();
        cremarker.setPosition(new LatLng(37.45223513440125, 127.12868095331365));
        cremarker.setMap(naverMap);

        //재경관
        Marker bmarker = new Marker();
        bmarker.setPosition(new LatLng(37.45264958577441, 127.12936622012947));
        bmarker.setMap(naverMap);

        //평생교육원
        Marker submarker = new Marker();
        submarker.setPosition(new LatLng(37.452734757250994, 127.12999385704026));
        submarker.setMap(naverMap);

        //아름관
        Marker ahmarker = new Marker();
        ahmarker.setPosition(new LatLng(37.45193718705055, 127.13171724265881));
        ahmarker.setMap(naverMap);

        //중앙도서관
        Marker libmarker = new Marker();
        libmarker.setPosition(new LatLng(37.4522800687859, 127.13299724409359));
        libmarker.setMap(naverMap);

        //세종관
        Marker sjmarker = new Marker();
        sjmarker.setPosition(new LatLng(37.453147844616055, 127.13440816146066));
        sjmarker.setMap(naverMap);

        //기숙사
        Marker dormarker = new Marker();
        dormarker.setPosition(new LatLng(37.45435364837424, 127.13546452545525));
        dormarker.setMap(naverMap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) {
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
