package com.multipz.clock24.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.clock24.R;
import com.multipz.clock24.Weather.models.Weather;
import com.multipz.clock24.Weather.tasks.GenericRequestTask;
import com.multipz.clock24.Weather.tasks.ParseResult;
import com.multipz.clock24.Weather.tasks.TaskOutput;
import com.multipz.clock24.Weather.utils.UnitConvertor;
import com.multipz.clock24.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class WeatherActivity extends AppCompatActivity implements LocationListener {
    Double d;
    int i;
    ImageView imageView;
    GifImageView gifImageView;
    Weather todayWeather = new Weather();
    Weather lo = new Weather();
    boolean destroyed = false;

    Context context;
    Typeface weatherFont;

    private static Map<String, Integer> speedUnits = new HashMap<>(3);
    private static Map<String, Integer> pressUnits = new HashMap<>(3);
    private static boolean mappingsInitialised = false;

    TextView todayTemperature;
    TextView todayIcon, todaycity, todayDescription;
    TextView one_temp_low,two_low_temp,three_low_temp,four_low_temp;

    LocationManager locationManager;
    ProgressDialog progressDialog;
    protected static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
    public String recentCity = "";

    View appView;

    private List<Weather> longTermTodayWeather = new ArrayList<>();
    private List<Weather> longTermTomorrowWeather = new ArrayList<>();
    private List<Weather> longTermWeatherthree = new ArrayList<>();
    private List<Weather> longTermWeatherfour = new ArrayList<>();
    private List<Weather> longTermWeatherfive = new ArrayList<>();
    private List<Weather> longTermWeathersix = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView = (ImageView) findViewById(R.id.backbutton);
        one_temp_low=(TextView)findViewById(R.id.one_temp_low);
        two_low_temp=(TextView)findViewById(R.id.two_low_temp);
        three_low_temp=(TextView)findViewById(R.id.three_temp_min);
        four_low_temp=(TextView)findViewById(R.id.four_temp_min);

/*

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/


        progressDialog = new ProgressDialog(WeatherActivity.this);

        gifImageView = (GifImageView) findViewById(R.id.gifmain);
        todayTemperature = (TextView) findViewById(R.id.todayTemperature);
        todaycity = (TextView) findViewById(R.id.city_text);

        todayIcon = (TextView) findViewById(R.id.todayIcon);
        todayDescription = (TextView) findViewById(R.id.todayDescription);
//        weatherFont = Typeface.createFromAsset(getAssets(), "Leelawadee_font/LeelawUI.ttf");
        weatherFont=Typeface.createFromAsset(getAssets(),"font/weather.ttf");
        todayIcon.setTypeface(weatherFont);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        preloadWeather();

    }

    private void preloadWeather() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);

        String lastToday = sp.getString("lastToday", "");
        if (!lastToday.isEmpty()) {
            new TodayWeatherTask(this, this, progressDialog).execute("cachedResponse", lastToday);
        }

        String lastLongterm = sp.getString("lastLongterm", "");
        if (!lastLongterm.isEmpty()) {
            new LongTermWeatherTask(this, this, progressDialog).execute("cachedResponse", lastLongterm);
        }
    }

    class TodayWeatherTask extends GenericRequestTask {
        public TodayWeatherTask(Context context, WeatherActivity activity, ProgressDialog progressDialog) {
            super(context, activity, progressDialog);
        }

        @Override
        protected void onPreExecute() {
            loading = 0;
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(TaskOutput output) {
            super.onPostExecute(output);
            // Update widgets
        }

        @Override
        protected ParseResult parseResponse(String response) {
            return parseTodayJson(response);
        }

        @Override
        protected String getAPIName() {
            return "weather";
        }

        @Override
        protected void updateMainUI() {
            updateTodayWeatherUI();
        }
    }

    class LongTermWeatherTask extends GenericRequestTask {
        public LongTermWeatherTask(Context context, WeatherActivity activity, ProgressDialog progressDialog) {
            super(context, activity, progressDialog);
        }

        @Override
        protected ParseResult parseResponse(String response) {
            return parseLongTermJson(response);
        }

        @Override
        protected String getAPIName() {
            return "forecast";
        }

        @Override
        protected void updateMainUI() {
            updateLongTermWeatherUI();
        }
    }

    public ParseResult parseLongTermJson(String result) {
        int i;
        try {
            JSONObject reader = new JSONObject(result);

            final String code = reader.optString("cod");
            if ("404".equals(code)) {
                if (longTermWeatherthree == null) {
                    longTermWeatherthree = new ArrayList<>();
                    longTermWeatherfour = new ArrayList<>();
                    longTermWeatherfive = new ArrayList<>();
                    longTermWeathersix = new ArrayList<>();
                    longTermTodayWeather = new ArrayList<>();
                    longTermTomorrowWeather = new ArrayList<>();
                }
                return ParseResult.CITY_NOT_FOUND;
            }

            longTermWeatherthree = new ArrayList<>();
            longTermWeatherfour = new ArrayList<>();
            longTermWeatherfive = new ArrayList<>();
            longTermWeathersix = new ArrayList<>();
            longTermTodayWeather = new ArrayList<>();
            longTermTomorrowWeather = new ArrayList<>();

            JSONArray list = reader.getJSONArray("list");
            for (i = 0; i < list.length(); i++) {
                Weather weather = new Weather();

                JSONObject listItem = list.getJSONObject(i);
                JSONObject main = listItem.getJSONObject("main");

                weather.setDate(listItem.getString("dt"));
                weather.setTemperature(main.getString("temp"));
                weather.setDescription(listItem.optJSONArray("weather").getJSONObject(0).getString("description"));
                JSONObject windObj = listItem.optJSONObject("wind");
                if (windObj != null) {
                    weather.setWind(windObj.getString("speed"));
                    weather.setWindDirectionDegree(windObj.getDouble("deg"));
                }
                weather.setPressure(main.getString("pressure"));
                weather.setHumidity(main.getString("humidity"));

                JSONObject rainObj = listItem.optJSONObject("rain");
                String rain = "";
                if (rainObj != null) {
                    rain = getRainString(rainObj);
                } else {
                    JSONObject snowObj = listItem.optJSONObject("snow");
                    if (snowObj != null) {
                        rain = getRainString(snowObj);
                    } else {
                        rain = "0";
                    }
                }
                weather.setRain(rain);

                final String idString = listItem.optJSONArray("weather").getJSONObject(0).getString("id");
                weather.setId(idString);

                final String dateMsString = listItem.getString("dt") + "000";
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(Long.parseLong(dateMsString));
                weather.setIcon(setWeatherIcon(Integer.parseInt(idString), cal.get(Calendar.HOUR_OF_DAY)));

                Calendar today = Calendar.getInstance();
                if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                    longTermTodayWeather.add(weather);
                } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 1) {
                    longTermTomorrowWeather.add(weather);
                } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 2) {
                    longTermWeatherthree.add(weather);
                } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 3) {
                    longTermWeatherfour.add(weather);
                } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 4) {
                    longTermWeatherfive.add(weather);
                } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 5) {
                    longTermWeathersix.add(weather);
                }
            }
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
            editor.putString("lastLongterm", result);
            editor.commit();
        } catch (JSONException e) {
            Log.e("JSONException Data", result);
            e.printStackTrace();
            return ParseResult.JSON_EXCEPTION;
        }

        return ParseResult.OK;
    }

    private void updateLongTermWeatherUI() {
        if (destroyed) {
            return;
        }

        TimeZone tz = TimeZone.getDefault();
//        String defaultDateFormat = context.getResources().getStringArray(R.array.dateFormatsValues)[0];

        String dateString, dateStringthree = null, dateStringfour = null, dateStringfive = null, dateStringsix = null, dateStringseven = null;
        try {
            SimpleDateFormat resultFormat = new SimpleDateFormat("E dd");
            resultFormat.setTimeZone(tz);
            dateString = resultFormat.format(longTermTomorrowWeather.get(0).getDate());
            dateStringthree = resultFormat.format(longTermWeatherthree.get(0).getDate());
            dateStringfour = resultFormat.format(longTermWeatherfour.get(0).getDate());
            dateStringfive = resultFormat.format(longTermWeatherfive.get(0).getDate());
            //dateStringsix = resultFormat.format(longTermWeathersix.get(0).getDate());
        } catch (IllegalArgumentException e) {
            dateString = context.getResources().getString(R.string.error_dateFormat);
        }

        Typeface weatherFont = Typeface.createFromAsset(getAssets(), "font/weather.ttf");
        TextView tmr_icon = (TextView) findViewById(R.id.tommorrow_icon);
        TextView three_icon = (TextView) findViewById(R.id.three_icon);
        TextView four_icon = (TextView) findViewById(R.id.four_icon);
        TextView five_icon = (TextView) findViewById(R.id.five_icon);
//        TextView six_icon=(TextView)findViewById(R.id.six_icon);

        tmr_icon.setTypeface(weatherFont);
        three_icon.setTypeface(weatherFont);
        four_icon.setTypeface(weatherFont);
        five_icon.setTypeface(weatherFont);
//        six_icon.setTypeface(weatherFont);

        tmr_icon.setText(longTermTomorrowWeather.get(0).getIcon());
        three_icon.setText(longTermWeatherthree.get(0).getIcon());
        four_icon.setText(longTermWeatherfour.get(0).getIcon());
        five_icon.setText(longTermWeatherfive.get(0).getIcon());
//        six_icon.setText(longTermWeathersix.get(0).getIcon());

        TextView txt_temp = (TextView) findViewById(R.id.one_temp);
        float temperatureToday = UnitConvertor.kelvinToCelsius(Float.parseFloat(longTermTomorrowWeather.get(0).getTemperature()));
        //txt_temp.setText(new DecimalFormat("#.#").format(temperatureToday) + " C"+Math.round(d));
        float temperatureTodayMin = UnitConvertor.kelvinToCelsius(Float.parseFloat(longTermTomorrowWeather.get(longTermTodayWeather.size()).getTemperature()));

        txt_temp.setText(Math.round(temperatureToday) + " ");
        one_temp_low.setText(Math.round(temperatureTodayMin)+"");

        TextView txt_temp2 = (TextView) findViewById(R.id.two_temp);
        float temperatureTommorrow = UnitConvertor.kelvinToCelsius(Float.parseFloat(longTermWeatherthree.get(0).getTemperature()));
        /*txt_temp2.setText(new DecimalFormat("#.#").format(temperatureTommorrow) + " C");*/
        float temperatureTwoMin = UnitConvertor.kelvinToCelsius(Float.parseFloat(longTermTomorrowWeather.get(longTermTodayWeather.size()).getTemperature()));
        txt_temp2.setText(Math.round(temperatureTommorrow) + "");
        two_low_temp.setText(Math.round(temperatureTwoMin)+"");

        TextView txt_temp3 = (TextView) findViewById(R.id.three_temp);
        float temperature3 = UnitConvertor.kelvinToCelsius(Float.parseFloat(longTermWeatherfour.get(0).getTemperature()));
        float temperatureTodayMin3 = UnitConvertor.kelvinToCelsius(Float.parseFloat(longTermTomorrowWeather.get(longTermTodayWeather.size()).getTemperature()));
        /*txt_temp3.setText(new DecimalFormat("#.#").format(temperature3) + " C");*/
        txt_temp3.setText(Math.round(temperature3) + "");
        three_low_temp.setText(Math.round(temperatureTodayMin3)+"");



        TextView txt_temp4 = (TextView) findViewById(R.id.four_temp);
        float temperature4 = UnitConvertor.kelvinToCelsius(Float.parseFloat(longTermWeatherfive.get(0).getTemperature()));
        float temperatureTodayMin4 = UnitConvertor.kelvinToCelsius(Float.parseFloat(longTermTomorrowWeather.get(longTermTodayWeather.size()).getTemperature()));
        txt_temp4.setText(Math.round(temperature4) + " ");
        four_low_temp.setText(Math.round(temperatureTodayMin4)+"");
//
//        TextView txt_temp5=(TextView)findViewById(R.id.five_temp);
//        float temperature5 = UnitConvertor.kelvinToCelsius(Float.parseFloat(longTermWeathersix.get(0).getTemperature()));
//        txt_temp5.setText(new DecimalFormat("#.#").format(temperature5) + " C");

        TextView date = (TextView) findViewById(R.id.datetommorrow);
        date.setText(dateString);

        TextView date3 = (TextView) findViewById(R.id.datethree);
        date3.setText(dateStringthree);

        TextView date4 = (TextView) findViewById(R.id.datefour);
        date4.setText(dateStringfour);

        TextView date5 = (TextView) findViewById(R.id.datefive);
        date5.setText(dateStringfive);
//
//        TextView date6=(TextView)findViewById(R.id.datesix);
//        date6.setText(dateStringsix);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
        double rain = Double.parseDouble(todayWeather.getRain());
        // String rainString = UnitConvertor.getRainString(rain, sp);

        TextView txt_desc = (TextView) findViewById(R.id.tommorrow_desc);
        TextView three_desc = (TextView) findViewById(R.id.three_desc);
        TextView four_desc = (TextView) findViewById(R.id.four_desc);
        TextView five_des = (TextView) findViewById(R.id.five_desc);
        txt_desc.setText(longTermTomorrowWeather.get(0).getDescription().substring(0, 1).toUpperCase() +
                longTermTomorrowWeather.get(0).getDescription().substring(1) /*+ rainString*/);


        three_desc.setText(longTermWeatherthree.get(0).getDescription().substring(0, 1).toUpperCase() +
                longTermTomorrowWeather.get(0).getDescription().substring(1) /*+ rainString*/);

        four_desc.setText(longTermWeatherfour.get(0).getDescription().substring(0, 1).toUpperCase() +
                longTermTomorrowWeather.get(0).getDescription().substring(1)/* + rainString*/);

        five_des.setText(longTermWeatherfour.get(0).getDescription().substring(0, 1).toUpperCase() +
                longTermTomorrowWeather.get(0).getDescription().substring(1)/* + rainString*/);
    }

    void getCityByLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Explanation not needed, since user requests this themmself

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            }

        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.getting_location));
            progressDialog.setCancelable(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        locationManager.removeUpdates(WeatherActivity.this);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            });
            progressDialog.show();
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        } else {
            showLocationSettingsDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;

        if (locationManager != null) {
            try {
                locationManager.removeUpdates(WeatherActivity.this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void showLocationSettingsDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.location_settings);
        alertDialog.setMessage(R.string.location_settings_message);
        alertDialog.setPositiveButton(R.string.location_settings_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCityByLocation();
                }
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        progressDialog.hide();
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            Log.e("LocationManager", "Error while trying to stop listening for location updates. This is probably a permissions issue", e);
        }
        Log.i("LOCATION (" + location.getProvider().toUpperCase() + ")", location.getLatitude() + ", " + location.getLongitude());
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        new ProvideCityNameTask(this, this, progressDialog).execute("coords", Double.toString(latitude), Double.toString(longitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    class ProvideCityNameTask extends GenericRequestTask {

        public ProvideCityNameTask(Context context, WeatherActivity activity, ProgressDialog progressDialog) {
            super(context, activity, progressDialog);
        }

        @Override
        protected void onPreExecute() { /*Nothing*/ }

        @Override
        protected String getAPIName() {
            return "weather";
        }

        @Override
        protected ParseResult parseResponse(String response) {
            Log.i("RESULT", response.toString());
            try {
                JSONObject reader = new JSONObject(response);

                final String code = reader.optString("cod");
                if ("404".equals(code)) {
                    Log.e("Geolocation", "No city found");
                    return ParseResult.CITY_NOT_FOUND;
                }

                String city = reader.getString("name");
                String country = "";
                JSONObject countryObj = reader.optJSONObject("sys");
                if (countryObj != null) {
                    country = ", " + countryObj.getString("country");
                }

                saveLocation(city + country);

            } catch (JSONException e) {
                Log.e("JSONException Data", response);
                e.printStackTrace();
                return ParseResult.JSON_EXCEPTION;
            }

            return ParseResult.OK;
        }

        @Override
        protected void onPostExecute(TaskOutput output) {
            /* Handle possible errors only */
            handleTaskOutput(output);
        }
    }

    private void saveLocation(String result) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
        recentCity = preferences.getString("city", Constant.DEFAULT_CITY);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("city", result);
        editor.commit();

        if (!recentCity.equals(result)) {
            // New location, update weather
            getTodayWeather();
            getLongTermWeather();
        }
    }

    private void getTodayWeather() {
        new TodayWeatherTask(this, this, progressDialog).execute();
    }

    private void getLongTermWeather() {
        new LongTermWeatherTask(this, this, progressDialog).execute();
    }

    private void updateTodayWeatherUI() {
        try {
            if (todayWeather.getCountry().isEmpty()) {
                preloadWeather();
                return;
            }
        } catch (Exception e) {
            preloadWeather();
            return;
        }

        String city = todayWeather.getCity();
        String country = todayWeather.getCountry();
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());

//        getSupportActionBar().setTitle(city + (country.isEmpty() ? "" : ", " + country));
        todaycity.setText(city + (country.isEmpty() ? "" : ", " + country));

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);

        float temperature = UnitConvertor.kelvinToCelsius(Float.parseFloat(todayWeather.getTemperature()));
        if (sp.getBoolean("temperatureInteger", false)) {
            temperature = Math.round(temperature);
        }

        double rain = Double.parseDouble(todayWeather.getRain());
        String rainString = UnitConvertor.getRainString(rain, sp);

      /*  todayTemperature.setText(new DecimalFormat("#.#").format(temperature) + " " + sp.getString("unit", "C"));*/

        todayTemperature.setText(Math.round(temperature) + " " + sp.getString("unit", "C"));
        todayIcon.setText(todayWeather.getIcon());


        todayDescription.setText(todayWeather.getDescription().substring(0, 1).toUpperCase() +
                todayWeather.getDescription().substring(1) /*+ rainString*/);

    }


    private ParseResult parseTodayJson(String result) {
        try {
            JSONObject reader = new JSONObject(result);

            final String code = reader.optString("cod");
            if ("404".equals(code)) {
                return ParseResult.CITY_NOT_FOUND;
            }

            String city = reader.getString("name");
            String country = "";
            JSONObject countryObj = reader.optJSONObject("sys");
            if (countryObj != null) {
                country = countryObj.getString("country");
                todayWeather.setSunrise(countryObj.getString("sunrise"));
                todayWeather.setSunset(countryObj.getString("sunset"));
            }
            todayWeather.setCity(city);
            todayWeather.setCountry(country);

            JSONObject coordinates = reader.getJSONObject("coord");
            if (coordinates != null) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                sp.edit().putFloat("latitude", (float) coordinates.getDouble("lon")).putFloat("longitude", (float) coordinates.getDouble("lat")).commit();
            }

            JSONObject main = reader.getJSONObject("main");

            JSONObject rainObj = reader.optJSONObject("rain");
            String rain;
            if (rainObj != null) {
                rain = getRainString(rainObj);
            } else {
                JSONObject snowObj = reader.optJSONObject("snow");
                if (snowObj != null) {
                    rain = getRainString(snowObj);
                } else {
                    rain = "0";
                }
            }
            todayWeather.setRain(rain);

            todayWeather.setTemperature(main.getString("temp"));
            final String idString = reader.getJSONArray("weather").getJSONObject(0).getString("id");
            todayWeather.setId(idString);
            todayWeather.setIcon(setWeatherIcon(Integer.parseInt(idString), Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));


            if (reader.getJSONArray("weather").getJSONObject(0).getString("main").contentEquals("Rain")) {
                try {
                    GifDrawable gifFromResource = new GifDrawable(getResources(), R.drawable.rain);
                    //gifFromResource.reset();
                    gifFromResource.setLoopCount(50);
                    gifImageView.setImageDrawable(gifFromResource);
                    gifImageView.setFreezesAnimation(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    final GifDrawable gifFromResource = new GifDrawable(getResources(), R.drawable.sun);
                    //gifFromResource.reset();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gifFromResource.setLoopCount(50);
                            gifImageView.setImageDrawable(gifFromResource);
                            gifImageView.setFreezesAnimation(true);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            todayWeather.setDescription(reader.getJSONArray("weather").getJSONObject(0).getString("main"));

            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
            editor.putString("lastToday", result);
            editor.commit();

        } catch (JSONException e) {
            Log.e("JSONException Data", result);
            e.printStackTrace();
            return ParseResult.JSON_EXCEPTION;
        }

        return ParseResult.OK;
    }

    public static long saveLastUpdateTime(SharedPreferences sp) {
        Calendar now = Calendar.getInstance();
        sp.edit().putLong("lastUpdate", now.getTimeInMillis()).apply();
        return now.getTimeInMillis();
    }


    public static String getRainString(JSONObject rainObj) {
        String rain = "0";
        if (rainObj != null) {
            rain = rainObj.optString("3h", "fail");
            if ("fail".equals(rain)) {
                rain = rainObj.optString("1h", "0");
            }
        }
        return rain;
    }

    private String setWeatherIcon(int actualId, int hourOfDay) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            if (hourOfDay >= 7 && hourOfDay < 20) {
                icon = this.getString(R.string.weather_sunny);
            } else {
                icon = this.getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = this.getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = this.getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = this.getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = this.getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = this.getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = this.getString(R.string.weather_rainy);
                    break;
            }
        }
        return icon;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getTodayWeather();
        getLongTermWeather();
        getCityByLocation();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    public boolean onSupportNavigateUp() {

        onBackPressed();

        // or call onBackPressed()
        return true;
    }
}

