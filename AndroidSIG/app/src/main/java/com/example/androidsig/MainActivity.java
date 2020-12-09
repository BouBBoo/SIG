package com.example.androidsig;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidsig.modele.Salle;
import com.example.androidsig.modele.Voisin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Salle> salleList;
    private Salle currentSalle;
    private Voisin currentVoisin;
    private WebView myWebView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        salleList = new ArrayList<>();
        currentSalle = null;
        currentVoisin = null;

        this.loadData();


        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebContentsDebuggingEnabled(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage0");

    }

    private void loadData(){
        try {
            URL url = new URL("http://192.168.1.21:8081/rest/salles");
            new SpringRest().execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateView(){
        TextView textView = (TextView) findViewById(R.id.textViewMain);
        textView.setText(currentSalle.getType_salle() + " : " + currentSalle.getNom());
        if(currentVoisin != null ){
            textView = (TextView) findViewById(R.id.VoisinDroite);
            if(currentVoisin.getVoisinD() != null){
                textView.setText(currentVoisin.getVoisinD().getNom());
            }else{
                textView.setText("Pas de voisin");
            }
            textView = (TextView) findViewById(R.id.VoisinGauche);
            if(currentVoisin.getVoisinG() != null){
                textView.setText(currentVoisin.getVoisinG().getNom());
            }else{
                textView.setText("Pas de voisin");
            }
            textView = (TextView) findViewById(R.id.VoisinFace);
            if(currentVoisin.getVoisinF() != null){
                textView.setText(currentVoisin.getVoisinF().getNom());
            }else{
                textView.setText("Pas de voisin");
            }
        }


        if(currentSalle.getEtage() == 0){
            myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage0");
        }else if(currentSalle.getEtage() == 1){
            myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage1");
        }

    }

    private class SpringRest extends AsyncTask<URL, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            try{
                connection = (HttpURLConnection) urls[0].openConnection();
                int reponse = connection.getResponseCode();

                if(reponse == HttpURLConnection.HTTP_OK){
                    StringBuilder stringBuilder = new StringBuilder();
                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                        String line;
                        while ((line = reader.readLine()) != null){
                            stringBuilder.append(line);
                        }
                    }
                    return new JSONArray(stringBuilder.toString());
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            finally {
                connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            if(jsonObject != null){
                updateListSalle(jsonObject);
            }
        }
    }

    private void updateListSalle(JSONArray jsonArray) {
        salleList.clear();
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                Salle salle = new Salle();
                salle.setId(object.getInt("id"));
                salle.setNom(object.getString("nom"));
                salle.setEtage(object.getInt("etage"));
                salle.setType_salle(object.getString("type_salle"));

                salleList.add(salle);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(currentSalle == null){
            this.currentSalle = salleList.get(0);
            try {
                new RestVoisin().execute(new URL("http://192.168.1.21:8081/rest/salles/voisins/" + currentSalle.getId()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            this.updateView();
        }
    }

    private class RestVoisin extends AsyncTask<URL, Void, Voisin>{

        @Override
        protected Voisin doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            try{
                connection = (HttpURLConnection) urls[0].openConnection();
                int reponse = connection.getResponseCode();
                Log.d("REST",""+reponse);

                if(reponse == HttpURLConnection.HTTP_OK){
                    StringBuilder stringBuilder = new StringBuilder();
                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                        String line;
                        while ((line = reader.readLine()) != null){
                            stringBuilder.append(line);
                        }
                    }
                    List<Salle> salles = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    String[] list = {"idvoisind","idvoising","idvoisinf"};
                    for(int i = 0; i < 3; i++){
                        int id = jsonObject.getInt(list[i]);
                        if(id == 0){
                            salles.add(null);
                        }else{
                            URL url = new URL("http://192.168.1.21:8081/rest/salles/" + id);
                            connection.disconnect();
                            connection = (HttpURLConnection) url.openConnection();
                            reponse = connection.getResponseCode();
                            if(reponse == HttpURLConnection.HTTP_OK) {
                                JSONObject object = getText(connection.getInputStream());
                                if(object == null){
                                    salles.add(null);
                                }else{
                                    Salle salle = new Salle();
                                    salle.setId(object.getInt("id"));
                                    salle.setNom(object.getString("nom"));
                                    salle.setEtage(object.getInt("etage"));
                                    salle.setType_salle(object.getString("type_salle"));

                                    salles.add(salle);
                                }
                            }
                        }
                    }

                    Voisin voisin = new Voisin();
                    voisin.setVoisinD(salles.get(0));
                    voisin.setVoisinG(salles.get(1));
                    voisin.setVoisinF(salles.get(2));
                    return voisin;
                }

                } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }finally {
                connection.disconnect();
            }
            return null;
        }

        private JSONObject getText(InputStream inputStream){
            StringBuilder stringBuilder = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
                String line;
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }
                return new JSONObject(stringBuilder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Voisin voisin) {
            if(voisin != null){
                updateVoisin(voisin);
            }
        }
    }

    private void updateVoisin(Voisin voisin) {
        this.currentVoisin = voisin;
        this.updateView();
    }


}