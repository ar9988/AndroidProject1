package com.example.androidproject1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject1.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar cal=Calendar.getInstance();
        Intent intent = getIntent();
        int j=0;
        int year = intent.getIntExtra("year", -1);
        int month = intent.getIntExtra("month", -1);
        if (year == -1 || month == -1) {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
        }
        int lastday = cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH,1);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK); // 1=일요일 7=토요일
        // 데이터 원본 준비
        String[] items = new String[lastday+dayofweek-1];

        for(int i=1;i<dayofweek;i++){
            items[j]=" ";
            j++;
        }
        for (int i = 0; i < lastday; i++) {
            items[j] = Integer.toString(i + 1);
            j++;
        }

        //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용
        ArrayAdapter<String> adapt
                = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                items);

        // id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView gridview = (GridView) findViewById(R.id.gridview);
        // 어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt);
    }
}