package com.example.androidplay;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DisplayLoginDetailsActivity extends AppCompatActivity {
    String[] courses, year, campus;
    String selected_course,selected_gender,selected_year,selected_campus;
    RadioGroup radioGroup;
    Spinner s1,s2,s3;
    String responseText;
    String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_login_details);


        // SPINNER COURSE
        courses=getResources().getStringArray(R.array.course_array);
        s1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index=parent.getSelectedItemPosition();
                selected_course=courses[index];
                //Toast.makeText(getBaseContext(), "You have selected item : " + courses[index], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // SPINNER YEAR
        year=getResources().getStringArray(R.array.year_array);
        s2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
        s2.setAdapter(adapter2);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index=parent.getSelectedItemPosition();
                selected_year=year[index];
                //Toast.makeText(getBaseContext(), "You have selected item : " + courses[index], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // SPINNER CAMPUS
        campus=getResources().getStringArray(R.array.campus_array);
        s3 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, campus);
        s3.setAdapter(adapter3);
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index=parent.getSelectedItemPosition();
                selected_campus=campus[index];
                //Toast.makeText(getBaseContext(), "You have selected item : " + courses[index], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void handleSubmitStudent(View view) {
        EditText idnumber = (EditText) findViewById(R.id.idnumber);
        EditText givenname = (EditText) findViewById(R.id.given_name);
        EditText familyname = (EditText) findViewById(R.id.family_name);
        EditText ticketnumber = (EditText) findViewById(R.id.ticketnumber);

        String idnumberText = idnumber.getText().toString();
        String familynameText = familyname.getText().toString();
        String givennameText = givenname.getText().toString();
        String ticketnumberText = ticketnumber.getText().toString();

        Log.d("Display Details",getIntent().getStringExtra("TOKEN").toString());
        authToken = getIntent().getStringExtra("TOKEN").toString();

//        if(!idnumberText.equals("")  || !selected_course.equals("----")) {
            String header_dialog="Student Information";
            String ticket_number_dialog="Ticket Number: "+ticketnumberText;
            String id_number_dialog="ID Number: "+idnumberText;
            String family_name_dialog="Family Name: "+familynameText;
            String given_name_dialog="Given Name: "+givennameText;
            String year_dialog="Year: "+selected_year;
            String campus_dialog="Campus: "+selected_campus;
            String course_dialog="Course: "+selected_course;

//            ((EditText) findViewById(R.id.idnumber)).getText().clear();
////            ((EditText) findViewById(R.id.fullname)).getText().clear();
//            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
//            ((Spinner) findViewById(R.id.spinner1)).setSelection(0);
//
//            ((EditText) findViewById(R.id.idnumber)).requestFocus();

            new HandleRequestConnection(ticketnumberText, idnumberText, familynameText, givennameText, selected_course, selected_year, selected_campus, authToken).execute("");

//            Fragment1 dialogFragment = Fragment1.newInstance(header_dialog,id_number_dialog+"\n"+year_dialog+"\n"+course_dialog, "student_info");
//            dialogFragment.show(getFragmentManager(), "dialog");
//        } else {
//            Toast.makeText(getBaseContext(), "Missing field input!", Toast.LENGTH_SHORT).show();
//        }
    }

    public void handleCancelStudent(View view) {

        ((EditText) findViewById(R.id.idnumber)).getText().clear();
//        ((EditText) findViewById(R.id.fullname)).getText().clear();
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        ((Spinner) findViewById(R.id.spinner1)).setSelection(0);

        ((EditText) findViewById(R.id.idnumber)).requestFocus();
    }

    private class HandleRequestConnection extends AsyncTask<String, Integer, Long> {

        private final String ticket_number;
        private final String id_number;
        private final String family_name;
        private final String given_name;
        private final String course;
        private final String year;
        private final String campus;
        private final String token;

        HandleRequestConnection(String ticket_number, String id_number, String family_name, String given_name ,String course ,String year, String campus, String token) {
            this.ticket_number = ticket_number;
            this.id_number = id_number;
            this.family_name = family_name;
            this.given_name = given_name;
            this.course = course;
            this.year = year;
            this.campus = campus;
            this.token = token;
        }

        protected Long doInBackground(String... urls) {
            try {
                String data = URLEncoder.encode("ticket_number", "UTF-8")
                        + "=" + URLEncoder.encode(this.ticket_number, "UTF-8");
                data += "&" + URLEncoder.encode("id_number", "UTF-8") + "="
                        + URLEncoder.encode(this.id_number, "UTF-8");
                data += "&" + URLEncoder.encode("family_name", "UTF-8") + "="
                        + URLEncoder.encode(this.family_name, "UTF-8");
                data += "&" + URLEncoder.encode("given_name", "UTF-8") + "="
                        + URLEncoder.encode(this.given_name, "UTF-8");
                data += "&" + URLEncoder.encode("course", "UTF-8") + "="
                        + URLEncoder.encode(this.course, "UTF-8");
                data += "&" + URLEncoder.encode("year", "UTF-8") + "="
                        + URLEncoder.encode(this.year, "UTF-8");
                data += "&" + URLEncoder.encode("campus", "UTF-8") + "="
                        + URLEncoder.encode(this.campus, "UTF-8");
                String text = "";
                BufferedReader reader=null;

                URL url = new URL("http://ucict.online:5000/api/registrant/create?token="+token);

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    sb.append(line + "\n");
                }


                responseText = sb.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject reader = new JSONObject(responseText);
                String parsed = reader.get("token").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Called ==",responseText);
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }
    }

    public void doPositiveClick() {
        Log.d("DialogFragment", "User clicks OK");
    }

    public void doNegativeClick() {
        Log.d("DialogFragment", "User clicks Cancel");
    }
}
