package com.example.androidplay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    public final static String LOGIN_USERNAME = "com.example.androidplay.USERNAME";
    public final static String LOGIN_PASSWORD = "com.example.androidplay.PASSWORD";
    public final static String TOKEN = "com.example.androidplay.TOKEN";
    ProgressDialog progressDialog;
    String responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleLogin(View view) {
//        Intent intent = new Intent(this, DisplayLoginDetailsActivity.class);
        final Intent intent = new Intent(this, MainListActivity.class);

        EditText usernameInput = (EditText) findViewById(R.id.input_email);
        EditText passwordInput = (EditText) findViewById(R.id.input_password);

        String usernameText = usernameInput.getText().toString();
        String passwordText = passwordInput.getText().toString();

        new HandleRequestConnection(usernameText, passwordText).execute("");

//        if (usernameText.equals("admin") && passwordText.equals("123")) {
//            intent.putExtra(LOGIN_USERNAME, usernameText);
//            intent.putExtra(LOGIN_PASSWORD, passwordText);

//        } else {
//            Toast.makeText(getBaseContext(), "Invalid Credentials! ", Toast.LENGTH_SHORT).show();
//        }
    }

    private class HandleRequestConnection extends AsyncTask<String, Integer, Long> {

        private final String username;
        private final String password;

        HandleRequestConnection(String username, String password) {
            this.username = username;
            this.password = password;
        }

        protected Long doInBackground(String... urls) {
            try {
                String data = URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(this.username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                        + URLEncoder.encode(this.password, "UTF-8");
                String text = "";
                BufferedReader reader=null;

                URL url = new URL("http://ucict.online:5000/api/authenticate/login");

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
//                HttpClient httpClient = new DefaultHttpClient();

//                Socket socket = new Socket("10.0.2.2", 4001);
//                Log.d("@@@ Socket Connected",String.valueOf(socket.isConnected()));
//                Log.d("@@@ Socket Username", this.username);
//
//                DataInputStream DIStream = new DataInputStream(socket.getInputStream());
//                DataOutputStream DOStream = new DataOutputStream(socket.getOutputStream());
//
//                String msg = this.username;
//                DOStream.write(msg.getBytes(), 0, msg.getBytes().length);
//                DOStream.flush();
//                DOStream.close();
//
//                String text = DIStream.toString();
//                DIStream.close();
//
//                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject reader = new JSONObject(responseText);
                String parsed = reader.get("token").toString();

                if (parsed != null) {
                    Log.d("Parsed",parsed);
                    Intent intent = new Intent(getApplicationContext(), DisplayLoginDetailsActivity.class);
                    intent.putExtra("TOKEN", parsed);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }
    }
}

