package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    JSONObject data;

    String lat = "";
    String lon = "";

    String [] coord = {lat, lon};

    EditText inputLAT;
    EditText inputLON;

    TextView cityN;
    TextView cityN2;
    TextView cityN3;

    TextView cityT;
    TextView cityT2;
    TextView cityT3;

    ImageView img;
    ImageView img2;
    ImageView img3;

    TextView Ctime;
    TextView Ctime2;
    TextView Ctime3;

    TextView Cdate;
    TextView Cdate2;
    TextView Cdate3;

    TextView Cweather;
    TextView Cweather2;
    TextView Cweather3;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputLAT = findViewById(R.id.lat);
        inputLON = findViewById(R.id.lon);

        cityN = findViewById(R.id.cityName);
        cityN2 = findViewById(R.id.cityName2);
        cityN3 = findViewById(R.id.cityName3);

        cityT = findViewById(R.id.cityTemp);
        cityT2 = findViewById(R.id.cityTemp3);
        cityT3 = findViewById(R.id.cityTemp2);

        img = findViewById(R.id.pic);
        img2 = findViewById(R.id.pic2);
        img3 = findViewById(R.id.pic3);

        Ctime = findViewById(R.id.time);
        Ctime2 = findViewById(R.id.time2);
        Ctime3 = findViewById(R.id.time3);

        Cweather = findViewById(R.id.weather);
        Cweather2 = findViewById(R.id.weather2);
        Cweather3 = findViewById(R.id.weather3);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //inputLAT.getText().toString();

                lat = inputLAT.getText().toString();
                lon = inputLON.getText().toString();

                coord = new String[]{lat, lon};

                //Log.d("TG","LAT:" + lat);
                //Log.d("TG", "LON:" + lon);

                //cityN.setText(lat);
                //cityN2.setText(lon);

                new JsonTask().execute(coord);


            }
        });


    }
    //String city = "London";


    public class JsonTask extends AsyncTask<String, Void, Void> {

        String firstName;
        Double firstTemp;
        long timeStamp;
        String formattedDate;
        String firstW;


        String secName;
        Double secTemp;
        long timeStamp2;
        String formattedDate2;
        String secW;

        String thirdName;
        Double thirdTemp;
        long timeStamp3;
        String formattedDate3;
        String thirdW;

        @Override
        protected Void doInBackground(String... params) {

            System.out.println("AASHIKA ");

            try {

                URL url = new URL("http://api.openweathermap.org/data/2.5/find?lat=" + coord[0] + "&lon=" + coord[1] + "&cnt=3&appid=5b2fafbfd767798bc7c9705462ba8eee");

                //URL url = new URL("api.openweathermap.org/data/2.5/weather?lat=40.3790&lon=74.5465&appid=5b2fafbfd767798bc7c9705462ba8eee");
                //URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=5b2fafbfd767798bc7c9705462ba8eee");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String stat = readJSON(reader);
                data = new JSONObject(stat);

                JSONObject root = new JSONObject(stat);
                JSONArray text = root.getJSONArray("list");

                //city 1
                //city name
                firstName = text.getJSONObject(0).getString("name");
                Log.d("TAG", firstName);

                //city 1 temp
                firstTemp = Double.parseDouble(text.getJSONObject(0).getJSONObject("main").getString("temp"));
                firstTemp = ((firstTemp-273.15)*(1.8) + 32);
                Log.d("TAG", firstTemp.toString());
                DecimalFormat df = new DecimalFormat("#.#");
                firstTemp = Double.parseDouble(df.format(firstTemp));

                //city 1 timestamp
                timeStamp = Long.parseLong(text.getJSONObject(0).getString("dt"));
                //Log.d("TAG", timeStamp);
                long unixSeconds = timeStamp;
                Date date = new java.util.Date(unixSeconds*1000L);
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy h:mm a z");
                //sdf.setTimeZone(java.util.TimeZone.getTimeZone("EST"));
                formattedDate = sdf.format(date);

                //city 1 weather
                firstW = text.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                //Log.d("TAG", firstW);

                //city 2
                //city 2 name
                secName = text.getJSONObject(1).getString("name");
                //Log.d("TAG", secName);

                //city 2 temp
                secTemp = Double.parseDouble(text.getJSONObject(1).getJSONObject("main").getString("temp"));
                secTemp = ((secTemp-273.15)*(1.8) + 32);
                Log.d("TAG", secTemp.toString());
                DecimalFormat df2 = new DecimalFormat("#.#");
                secTemp = Double.parseDouble(df2.format(secTemp));
                Log.d("TAG", secTemp.toString());

                //city 2 timestamp
                timeStamp2 = Long.parseLong(text.getJSONObject(1).getString("dt"));
                //Log.d("TAG", timeStamp2);
                long unixSeconds2 = timeStamp2;
                Date date2 = new java.util.Date(unixSeconds2*1000L);
                SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("MM/dd/yyyy h:mm a z");
                //sdf.setTimeZone(java.util.TimeZone.getTimeZone("EST"));
                formattedDate2 = sdf2.format(date2);

                //city 2 weather
                secW = text.getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("description");
                Log.d("TAG", secW);


                //city 3
                //city 3 name
                thirdName = text.getJSONObject(2).getString("name");
                Log.d("TAG", thirdName);

                //city 3 temp
                thirdTemp = Double.parseDouble(text.getJSONObject(2).getJSONObject("main").getString("temp"));
                thirdTemp = ((thirdTemp-273.15)*(1.8) + 32);
                Log.d("TAG", thirdTemp.toString());
                DecimalFormat df3 = new DecimalFormat("#.#");
                thirdTemp = Double.parseDouble(df3.format(thirdTemp));
                Log.d("TAG", thirdTemp.toString());

                //city 3 timestamp
                timeStamp3 = Long.parseLong(text.getJSONObject(2).getString("dt"));
                //Log.d("TAG", timeStamp3);
                long unixSeconds3 = timeStamp3;
                Date date3 = new java.util.Date(unixSeconds3*1000L);
                SimpleDateFormat sdf3 = new java.text.SimpleDateFormat("MM/dd/yyyy h:mm a z");
                //sdf.setTimeZone(java.util.TimeZone.getTimeZone("EST"));
                formattedDate3 = sdf3.format(date3);

                //city 3 weather
                thirdW = text.getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("description");
                Log.d("TAG", thirdW);



            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("AAAAparas");
            }
            return null;
        }

        public void picture1()
        {
            if(firstW.equals("few clouds") || firstW.equals("scattered clouds") || firstW.equals("broken clouds") || firstW.equals("overcast clouds")) {
                img.setImageResource(R.drawable.cloudly);
                Log.d("TAG", "1");
            }

            if(firstW.equals("clear sky"))
                img.setImageResource(R.drawable.sunny);

            if(firstW.equals("shower rain") || firstW.equals("rain") || firstW.equals("mist"))
                img.setImageResource(R.drawable.rain);

            if(firstW.equals("snow") || firstW.equals("light snow"))
                img.setImageResource(R.drawable.snow);

            if(firstW.equals("thunderstorm"))
                img.setImageResource(R.drawable.lightning);

        }


        public void picture2()
        {
            if(secW.equals("few clouds") || secW.equals("scattered clouds") || secW.equals("broken clouds") || secW.equals("overcast clouds")){
                img2.setImageResource(R.drawable.cloudly);
                Log.d("TAG", "2");
            }

            if(secW.equals("clear sky"))
                img2.setImageResource(R.drawable.sunny);

            if(secW.equals("shower rain") || secW.equals("rain") || secW.equals("mist"))
                img2.setImageResource(R.drawable.rain);

            if(secW.equals("snow") ||  secW.equals("light snow"))
                img2.setImageResource(R.drawable.snow);

            if(secW.equals("thunderstorm"))
                img2.setImageResource(R.drawable.lightning);

        }

        public void picture3()
        {
            if(thirdW.equals("few clouds") || thirdW.equals("scattered clouds") || thirdW.equals("broken clouds") || thirdW.equals("overcast clouds")){
                img3.setImageResource(R.drawable.cloudly);
                Log.d("TAG", "3");
            }

            if(thirdW.equals("clear sky"))
                img3.setImageResource(R.drawable.sunny);

            if(thirdW.equals("shower rain") || thirdW.equals("rain") || thirdW.equals("mist"))
                img3.setImageResource(R.drawable.rain);

            if(thirdW.equals("snow") ||  thirdW.equals("light snow"))
                img3.setImageResource(R.drawable.snow);

            if(thirdW.equals("thunderstorm"))
                img3.setImageResource(R.drawable.lightning);

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cityN.setText(firstName);
            cityT.setText(firstTemp.toString() + "°F");
            Ctime.setText(formattedDate);
            Log.d("TAG", formattedDate);
            Cweather.setText(firstW);
            picture1();

            cityN2.setText(secName);
            Cweather2.setText(secW);
            cityT2.setText(secTemp.toString() + "°F");
            Ctime2.setText(formattedDate2);
            Log.d("TAG", formattedDate2);
            picture2();

            cityN3.setText(thirdName);
            Cweather3.setText(thirdW);
            cityT3.setText(thirdTemp.toString() + "°F");
            Ctime3.setText(formattedDate3);
            Log.d("TAG", formattedDate3);
            picture3();

        }
    }

    public String readJSON(BufferedReader buffIn) throws IOException {

        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = buffIn.readLine()) != null) {
            buffer.append(line + "\n");
            Log.d("JSON: ", "> " + line);   //here u ll get whole response...... :-)
        }
        return buffer.toString();
    }

}


