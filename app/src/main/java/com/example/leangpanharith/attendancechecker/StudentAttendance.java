package com.example.leangpanharith.attendancechecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentAttendance extends AppCompatActivity {

    ArrayList<String> attendanceList;

    TextView name, gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        name = (TextView) findViewById(R.id.name);
        name.setText(bundle.getString("name"));
        gender = (TextView) findViewById(R.id.gender);
        gender.setText(bundle.getBoolean("isFemale")?"Female": "Male");
        attendanceList = new ArrayList<>();
        getStudent(id);
    }

    public void getStudent(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://" + ServerIP.IP + "/api/students/" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONArray attendances = data.getJSONArray("attendances");
                    Log.i("attendance: ", attendances.toString());
                    for (int i = 0; i < attendances.length(); i++){
                        attendanceList.add(attendances.getJSONObject(i).getString("date"));
                    }
                    populateList();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void populateList () {
        ListView list = (ListView) findViewById(R.id.attendance_list);
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.attendance,attendanceList);
        list.setAdapter(adapter);
    }
}
