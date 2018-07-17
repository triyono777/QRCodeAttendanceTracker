package com.example.leangpanharith.attendancechecker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class StudentList extends AppCompatActivity {

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private String url = "http://"+ServerIP.IP+"/api/students";

    public ArrayList<Student> students;


    ListView studentList;
    StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        students = new ArrayList<>();
        getStudent();
    }

    public void getStudent() {

        requestQueue = Volley.newRequestQueue(this);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray studentArray = response.getJSONArray("data");
                    for(int i = 0; i < studentArray.length(); i++) {
                        JSONObject studentJson = studentArray.getJSONObject(i);
                        Student student = new Student(
                                studentJson.getInt("id"),
                                studentJson.getString("name"),
                                studentJson.getString("qr"),
                                studentJson.getString("gender").equals("0")
                        );
                        students.add(student);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                populateList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }
    public void populateList() {
        studentAdapter = new StudentAdapter(this, students);
        studentList = (ListView) findViewById(R.id.student_list);
        studentList.setAdapter(studentAdapter);
    }

    public class StudentAdapter extends ArrayAdapter<Student> {
        public StudentAdapter (Context context, ArrayList<Student> students){
            super(context, 0, students);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            final Student student = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_item, parent, false);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.name);
            TextView tvGender = (TextView) convertView.findViewById(R.id.gender);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), StudentAttendance.class);
                    intent.putExtra("id", student.id);
                    intent.putExtra("name", student.name);
                    intent.putExtra("isFemale", student.isFemale);
                    startActivity(intent);
                }
            });

            tvName.setText(student.name);
            tvGender.setText(student.isFemale?"Female": "Male");

            return convertView;        }
    }

}
