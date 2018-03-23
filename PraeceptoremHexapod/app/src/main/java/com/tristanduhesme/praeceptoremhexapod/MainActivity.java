package com.tristanduhesme.praeceptoremhexapod;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button avancer = null;
    Button reculer = null;
    Button debout = null;
    Button gauche = null;
    Button droite = null;
    SeekBar vitesse = null;

    TextView vitesseValue = null;
    TextView console = null;
    Resources res = null;

    String url = "http://10.3.141.1:8080";
    int speed = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = getResources();

        avancer = findViewById(R.id.avancerViewId);
        avancer.setOnClickListener(avancerListener);
        reculer = findViewById(R.id.reculerViewId);
        reculer.setOnClickListener(reculerListener);
        debout = findViewById(R.id.deboutViewId);
        debout.setOnClickListener(deboutListener);
        droite = findViewById(R.id.droiteViewId);
        droite.setOnClickListener(droiteListener);
        gauche = findViewById(R.id.gaucheViewId);
        gauche.setOnClickListener(gaucheListener);

        console = findViewById(R.id.console);
        vitesseValue = findViewById(R.id.vitesseValueViewId);
        vitesseValue.setText("vitesse : "+speed);

        vitesse = findViewById(R.id.vitesseViewId);
        vitesse.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                speed = progress;
                vitesseValue.setText("vitesse : "+speed);
                consolePrint("vitesse : "+speed);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private View.OnClickListener avancerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //refreshSpeed();
            String param = "?ordre=avance&vitesse="+speed;

            if (!isConnected()) {
                Log.i("INFO", "Non connecté à internet");
                //Snackbar.make(this.linearLayout, "Aucune connexion à internet.", Snackbar.LENGTH_LONG).show();
                return;
            }
            String command = url + param;
            Log.i("INFO", command);
            consolePrint(command);
            FetchTask mAsyncTask;
            mAsyncTask = (FetchTask) new FetchTask().execute(command);
        }
    };
    private View.OnClickListener reculerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //refreshSpeed();
            String param = "?ordre=recule&vitesse="+speed;

            if (!isConnected()) {
                Log.i("INFO", "Non connecté à internet");
                //Snackbar.make(this.linearLayout, "Aucune connexion à internet.", Snackbar.LENGTH_LONG).show();
                return;
            }
            String command = url + param;
            Log.i("INFO", command);
            consolePrint(command);
            FetchTask mAsyncTask;
            mAsyncTask = (FetchTask) new FetchTask().execute(command);
        }
    };
    private View.OnClickListener deboutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String param = "?ordre=debout";

            if (!isConnected()) {
                Log.i("INFO", "Non connecté à internet");
                //Snackbar.make(this.linearLayout, "Aucune connexion à internet.", Snackbar.LENGTH_LONG).show();
                return;
            }
            String command = url + param;
            Log.i("INFO", command);
            consolePrint(command);
            FetchTask mAsyncTask;
            mAsyncTask = (FetchTask) new FetchTask().execute(command);
        }
    };
    private View.OnClickListener gaucheListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //refreshSpeed();
            String param = "?ordre=gauche&vitesse="+speed;

            if (!isConnected()) {
                Log.i("INFO", "Non connecté à internet");
                //Snackbar.make(this.linearLayout, "Aucune connexion à internet.", Snackbar.LENGTH_LONG).show();
                return;
            }
            String command = url + param;
            Log.i("INFO", command);
            consolePrint(command);
            FetchTask mAsyncTask;
            mAsyncTask = (FetchTask) new FetchTask().execute(command);
        }
    };
    private View.OnClickListener droiteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //refreshSpeed();
            String param = "?ordre=droite&vitesse="+speed;

            if (!isConnected()) {
                Log.i("INFO", "Non connecté à internet");
                //Snackbar.make(this.linearLayout, "Aucune connexion à internet.", Snackbar.LENGTH_LONG).show();
                return;
            }
            String command = url + param;
            consolePrint(command);
            Log.i("INFO", command);
            FetchTask mAsyncTask;
            mAsyncTask = (FetchTask) new FetchTask().execute(command);
        }
    };

    private void consolePrint(String msg) {
        String t = (String) console.getText();
        t = t+"\n"+msg;
        String[] tbis = t.split("\n");
        String lastLine = "";
        String line2 = "";
        String line3 = "";
        if(tbis.length>0) {
            lastLine = tbis[tbis.length - 1];
        }
        if(tbis.length>1) {
            line2 = tbis[tbis.length - 2];
        }
        if(tbis.length>2) {
            line3 = tbis[tbis.length - 3];
        }
        console.setText(line3+"\n"+line2+"\n"+lastLine+"");
    }
    private class FetchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //snackbar.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i("INFODEBUG doInBackgroun","url = "+strings[0]);
            InputStream inputStream = null;
            HttpURLConnection conn = null;
            String stringUrl = strings[0];
            try {
                URL url = new URL(stringUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int response = conn.getResponseCode();
                if (response != 200) {
                    return null;
                }
                inputStream = conn.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    buffer.append("\n");
                    //Log.i("INFODEBUG", line);
                }
                return new String(buffer);
            } catch (IOException e) {
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
             //   snackbar2.show();
            } else {
                //populateList(listView, s);
            }
            //snackbar.dismiss();
        }
    }


}
