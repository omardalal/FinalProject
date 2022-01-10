package com.example.finalproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReserveActivity extends AppCompatActivity {

    private static Button startDateBtn;
    private static Button endDateBtn;
    private Button reserveBtn;
    private TextView availableLbl;
    private TextView startWeatherLbl;
    private TextView startWeatherValLbl;
    private TextView endWeatherLbl;
    private TextView endWeatherValLbl;
    private ImageView startWeatherImg;
    private ImageView endWeatherImg;

    private int roomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.reserveActivityHeader));

        startDateBtn = findViewById(R.id.startDateBtn);
        endDateBtn = findViewById(R.id.endDateBtn);
        reserveBtn = findViewById(R.id.reserveBtn);
        availableLbl = findViewById(R.id.availableLbl);
        startWeatherLbl = findViewById(R.id.startWeatherLbl);
        startWeatherValLbl = findViewById(R.id.startWeatherValueLbl);
        endWeatherLbl = findViewById(R.id.endWeatherLbl);
        endWeatherValLbl = findViewById(R.id.endWeatherValueLbl);
        startWeatherImg = findViewById(R.id.startWeatherImage);
        endWeatherImg = findViewById(R.id.endWeatherImage);

        setupDates();
        Intent srcIntent = getIntent();
        roomNumber = srcIntent.getIntExtra("roomNumber", 1);
    }

    public void reserve(View view) {
        final String startDate = createSQLDateTime(startYear, startMonth, startDay);
        final String endDate = createSQLDateTime(endYear, endMonth, endDay);
        Map<String, String> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("roomNumber", ""+roomNumber);
        params.put("userEmail", PublicData.loggedEmail);
        BackendRequests.postRequest("reserveRoom.php", this, params, (response, success) -> {
            if (success) {
                Intent intent = new Intent(this, RoomActivity.class);
                intent.putExtra("roomNumber", roomNumber);
                startActivity(intent);
            } else {
                Toast.makeText(ReserveActivity.this, getResources().getString(R.string.roomReserveFail), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void setupDates() {
        startYear = year;
        startMonth = month;
        startDay = day;
        endYear = year;
        endMonth = month;
        endDay = day;
        startDateBtn.setText(day+"/"+(month+1)+"/"+year);
        endDateBtn.setText(day+"/"+(month+1)+"/"+year);
    }

    public static String createSQLDateTime(int year, int month, int day) {
        return (year+"-"+String.format("%02d", month+1)+"-"+String.format("%02d", day));
    }

    public void check(View view) {
        final String startDate = createSQLDateTime(startYear, startMonth, startDay);
        final String endDate = createSQLDateTime(endYear, endMonth, endDay);
        final String endpoint = "checkAvailability.php?roomNumber=" + roomNumber+"&startDate="+startDate+"&endDate="+endDate;
        getWeatherData();
        BackendRequests.getRequest(endpoint, this, getResources().getString(R.string.invalidDate), (response, success) -> {
            try {
                boolean available = response.get(0).getBoolean("available");
                showViews();
                if (available) {
                    availableLbl.setTextColor(PublicData.AVAILABLE_COLOR);
                    availableLbl.setText(getResources().getString(R.string.roomStatusAvailable));
                    reserveBtn.setVisibility(View.VISIBLE);
                } else {
                    availableLbl.setTextColor(PublicData.BOOKED_COLOR);
                    availableLbl.setText(getResources().getString(R.string.roomStatusBooked));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void startDateClicked(View view) {
        DialogFragment newFragment = new DatePickerFragment(0);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void endDateClicked(View view) {
        DialogFragment newFragment = new DatePickerFragment(1);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showViews() {
        availableLbl.setVisibility(View.VISIBLE);
        availableLbl.setVisibility(View.VISIBLE);
        startWeatherLbl.setVisibility(View.VISIBLE);
        startWeatherValLbl.setVisibility(View.VISIBLE);
        endWeatherLbl.setVisibility(View.VISIBLE);
        endWeatherValLbl.setVisibility(View.VISIBLE);
        startWeatherImg.setVisibility(View.VISIBLE);
        endWeatherImg.setVisibility(View.VISIBLE);
    }

    private void getWeatherData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.metaweather.com/api/location/44418/";
        String imgBaseUrl = "https://www.metaweather.com/static/img/weather/";
        JsonObjectRequest request = new JsonObjectRequest
            (Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String stDate = createSQLDateTime(startYear, startMonth, startDay);
                    String endDate = createSQLDateTime(endYear, endMonth, endDay);
                    try {
                        JSONArray array = response.getJSONArray("consolidated_weather");
                        boolean stSet = false;
                        boolean endSet = false;
                        for(int i = 0; i<array.length(); i++){
                            JSONObject responseObj = array.getJSONObject(i);
                            String date = responseObj.getString("applicable_date");
                            if (date.equals(stDate)) {
                                startWeatherValLbl.setText(responseObj.getString("weather_state_name"));
                                int rcsId = getResources().getIdentifier(responseObj.getString("weather_state_abbr"), "drawable", ReserveActivity.this.getPackageName());
                                startWeatherImg.setImageDrawable(getResources().getDrawable(rcsId));
                                stSet = true;
                            }
                            if (date.equals(endDate)) {
                                endWeatherValLbl.setText(responseObj.getString("weather_state_name"));
                                int rcsId = getResources().getIdentifier(responseObj.getString("weather_state_abbr"), "drawable", ReserveActivity.this.getPackageName());
                                endWeatherImg.setImageDrawable(getResources().getDrawable(rcsId));
                                endSet = true;
                            }
                        }
                        if (!stSet) {
                            startWeatherValLbl.setText(getResources().getString(R.string.weatherOutOfRange));
                            startWeatherImg.setVisibility(View.INVISIBLE);
                        }
                        if (!endSet) {
                            endWeatherValLbl.setText(getResources().getString(R.string.weatherOutOfRange));
                            endWeatherImg.setVisibility(View.INVISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ReserveActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            });
        queue.add(request);
    }

    private static int startDay=-1;
    private static int startMonth=-1;
    private static int startYear=-1;
    private static int endDay=-1;
    private static int endMonth=-1;
    private static int endYear=-1;
    private static int year = Calendar.getInstance().get(Calendar.YEAR);
    private static int month = Calendar.getInstance().get(Calendar.MONTH);
    private static int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        //0->start, 1->end
        private int pickerNum=-1;

        public DatePickerFragment(int pickerNum) {
            this.pickerNum = pickerNum;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if (pickerNum==0&&startDay!=-1&&startMonth!=-1&&startYear!=-1) {
                day = startDay;
                month = startMonth;
                year = startYear;
            }
            if (pickerNum==1&&endDay!=-1&&endMonth!=-1&&endYear!=-1) {
                day = endDay;
                month = endMonth;
                year = endYear;
            }
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (pickerNum == 0) {
                startYear = year;
                startMonth = month;
                startDay = day;
                startDateBtn.setText(day+"/"+(month+1)+"/"+year);
            } else if (pickerNum == 1) {
                endYear = year;
                endMonth = month;
                endDay = day;
                endDateBtn.setText(day+"/"+(month+1)+"/"+year);
            }
        }
    }
}