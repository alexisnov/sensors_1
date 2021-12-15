package com.example.sensors_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;//Управление датчиками
    private ListView listDevices;
    private  SensorEventListener mySensorEventListener(int sensor,float values[]){

    }
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
        SensorEventListener mySensorEventListener;
        sensorManager.registerListener(mySensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        //float->sting

    }
}