package com.example.sensors_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;//Управление датчиками
    private ListView listDevices;
    SensorEventListener mySensorEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Получаем идентификатор системной службы сенсоров
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //Список устройств
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        //Получаем идентификаторы
        List<String> listSensorType = new ArrayList<>();
        for(int i =0;i<deviceSensors.size();i++){
            listSensorType.add(deviceSensors.get(i).getName());
        }
        listDevices = (ListView) findViewById(R.id.listDevices);
        listDevices.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,listSensorType));
        //Получение данных датчика
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mySensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
                    TextView tv = (TextView) findViewById(R.id.textView);
                    tv.setText(String.format("Акселерометр: X %.3f, Y %.3f, Z %.3f",
                            event.values[0], event.values[1], event.values[2]));
                    //Перемещение объекта
                    ImageView iv = (ImageView) findViewById(R.id.imageView);
                    ConstraintLayout.LayoutParams layoutParams =
                            (ConstraintLayout.LayoutParams) iv.getLayoutParams();

                    layoutParams.horizontalBias -= event.values[0]/100;
                    if(layoutParams.horizontalBias<0){
                        layoutParams.horizontalBias = 0;
                    } else if (layoutParams.horizontalBias>1){
                        layoutParams.horizontalBias = 1;
                    }
                    iv.setLayoutParams(layoutParams);
                }
                if(event.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    TextView tv = (TextView) findViewById(R.id.textViewTemp);
                    tv.setText(String.format("Температура: %.1f",event.values[0]));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //Акселерометр
        sensorManager.registerListener(mySensorEventListener,sensor,
                SensorManager.SENSOR_DELAY_GAME);
        //Температура
        Sensor sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(mySensorEventListener,sensorTemp,
                SensorManager.SENSOR_DELAY_NORMAL);

        //float->sting

    }
}