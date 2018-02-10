package com.example.android.quakereport;

/**
 * Created by geggg on 09.02.2018.
 */

//Объект, имеет 3 параметра
public class QuakeInf {

    //Магнитуда
    private double mMag;
    //Место
    private String mLocation;
    //Когда произошло
    private long mMillisecondsTime;

    //Создание объекта
    public QuakeInf (double qMag, String qLocation, long qDate ){

        mMag = qMag;
        mLocation = qLocation;
        mMillisecondsTime = qDate;
    }
    //Получить параметр амплитуды
    public double getMag(){
        return mMag;
    }
//Получить параметр Расположение
    public String getLocation(){
        return mLocation;
    }
    //Получение параметра даты, когда это случилось
    public long getDate() {
        return mMillisecondsTime;
    }
}