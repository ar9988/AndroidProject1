package com.example.androidproject1;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;


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
        int dayofweek=intent.getIntExtra("dayofweek",-1);
        int lastday;
        if (year == -1 || month == -1) {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            lastday = cal.getActualMaximum(Calendar.DATE);
            cal.set(Calendar.DAY_OF_MONTH,1);
            dayofweek = cal.get(Calendar.DAY_OF_WEEK); // 1=일요일 7=토요일
        }
        else {
            switch (month+1) {
                case 2:
                    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                        lastday = 29;
                    else lastday = 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    lastday = 30;
                    break;
                default:
                    lastday = 31;
                    break;
            }
        }
        TextView txt=findViewById(R.id.Text);
        Button btn1=findViewById(R.id.Btn1);
        Button btn2=findViewById(R.id.Btn2);
        int finalYear = year;
        int finalMonth = month;
        int finalDayofweek = dayofweek;
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,MainActivity.class);
                if(finalMonth==0){
                    intent1.putExtra("year", finalYear-1);
                    intent1.putExtra("month", 11);
                    intent1.putExtra("dayofweek", getDayofWeek(finalYear-1,11));
                }
                else {
                    intent1.putExtra("year", finalYear);
                    intent1.putExtra("month", finalMonth-1);
                    intent1.putExtra("dayofweek", getDayofWeek(finalYear,finalMonth-1));
                }
                finish();
                startActivity(intent1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,MainActivity.class);
                if(finalMonth==11){
                    intent1.putExtra("year", finalYear+1);
                    intent1.putExtra("month", 0);
                    intent1.putExtra("dayofweek", getDayofWeek(finalYear+1,0));

                }
                else {
                    intent1.putExtra("year", finalYear);
                    intent1.putExtra("month", finalMonth+1);
                    intent1.putExtra("dayofweek", getDayofWeek(finalYear,finalMonth+1));

                }
                finish();
                startActivity(intent1);
            }
        });
        month+=1;
        txt.setText(year+"년 "+month+"월");
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
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        // id를 바탕으로 화면 레이아웃에 정의된 GridView 객체 로딩
        GridView gridview = (GridView) findViewById(R.id.gridview);
        // 어댑터를 GridView 객체에 연결
        gridview.setAdapter(adapt);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,finalYear+"."+(finalMonth+1)+"."+((int)id-finalDayofweek+2),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public int getDayofWeek(int year,int month){
        int []end_day= new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
            end_day[1]=29;
        year--;
        int dayofweek=year*365+year/4-year/100+year/400;
        for(int i=0;i<month;i++){
            dayofweek+=end_day[i];
        }
        dayofweek++;
        return dayofweek%7+1;
    }
}