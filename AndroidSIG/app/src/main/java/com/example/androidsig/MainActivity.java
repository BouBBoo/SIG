package com.example.androidsig;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidsig.modele.Escalier;
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

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        salleList = new ArrayList<>();
        currentSalle = null;
        currentVoisin = null;

        this.loadData();


        myWebView = findViewById(R.id.webview);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
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
        TextView textView = findViewById(R.id.textViewMain);
        textView.setText(currentSalle.getType_salle() + " : " + currentSalle.getNom());
        if(currentVoisin != null ){
            textView = findViewById(R.id.VoisinDroite);
            if(currentVoisin.getVoisinD() != null){
                if (currentVoisin.getVoisinD().getClass().equals(Salle.class)){
                    textView.setText(((Salle)currentVoisin.getVoisinD()).getNom());
                }else if (currentVoisin.getVoisinD().getClass().equals(Escalier.class)){
                    textView.setText("Escalier " + ((Escalier)currentVoisin.getVoisinD()).getId());
                }
            }else{
                textView.setText("Pas de voisin");
            }
            textView = findViewById(R.id.VoisinGauche);
            if(currentVoisin.getVoisinG() != null){
                if (currentVoisin.getVoisinG().getClass().equals(Salle.class)){
                    textView.setText(((Salle)currentVoisin.getVoisinG()).getNom());
                }else if (currentVoisin.getVoisinG().getClass().equals(Escalier.class)){
                    textView.setText("Escalier " + ((Escalier)currentVoisin.getVoisinG()).getId());
                }
            }else{
                textView.setText("Pas de voisin");
            }
            textView = findViewById(R.id.VoisinFace);
            if(currentVoisin.getVoisinF() != null){
                if (currentVoisin.getVoisinF().getClass().equals(Salle.class)){
                    textView.setText(((Salle)currentVoisin.getVoisinF()).getNom());
                }else if (currentVoisin.getVoisinF().getClass().equals(Escalier.class)){
                    textView.setText("Escalier " + ((Escalier)currentVoisin.getVoisinF()).getId());
                }
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

    @SuppressLint("StaticFieldLeak")
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
                assert connection != null;
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
                new RestVoisin().execute(new URL("http://192.168.1.21:8081/rest/salles/voisins/" + currentSalle.getId()),new URL("http://192.168.1.21:8081/rest/salles/escalier/" + currentSalle.getId()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            this.updateView();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class RestVoisin extends AsyncTask<URL, Void, Voisin>{

        @Override
        protected Voisin doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                int reponse = connection.getResponseCode();
                Log.d("REST", "" + reponse);
                List<Object> Voisins = new ArrayList<>();
                if (reponse == HttpURLConnection.HTTP_OK) {
                    StringBuilder stringBuilder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                    }
                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    String[] list = {"idvoisind", "idvoising", "idvoisinf"};
                    for (int i = 0; i < 3; i++) {
                        int id = jsonObject.getInt(list[i]);
                        if (id == 0) {
                            Voisins.add(null);
                        } else {
                            URL url = new URL("http://192.168.1.21:8081/rest/salles/" + id);
                            connection.disconnect();
                            connection = (HttpURLConnection) url.openConnection();
                            reponse = connection.getResponseCode();
                            if (reponse == HttpURLConnection.HTTP_OK) {
                                JSONObject object = getText(connection.getInputStream());
                                if (object == null) {
                                    Voisins.add(null);
                                } else {
                                    Salle salle = new Salle();
                                    salle.setId(object.getInt("id"));
                                    salle.setNom(object.getString("nom"));
                                    salle.setEtage(object.getInt("etage"));
                                    salle.setType_salle(object.getString("type_salle"));

                                    Voisins.add(salle);
                                }
                            }
                        }
                    }
                }
                connection.disconnect();
                connection = (HttpURLConnection) urls[1].openConnection();
                reponse = connection.getResponseCode();
                Log.d("Escalier",""+ reponse);
                if(reponse == HttpURLConnection.HTTP_OK){
                    JSONObject object = getText(connection.getInputStream());
                    assert object != null;
                    int id = object.getInt("escalier");
                    URL url = new URL("http://192.168.1.21:8081/rest/Escalier/" + id);
                    connection.disconnect();
                    connection = (HttpURLConnection) url.openConnection();
                    reponse = connection.getResponseCode();
                    Log.d("Escalier",""+ reponse);
                    if(reponse == HttpURLConnection.HTTP_OK){
                        JSONObject new_object = getText(connection.getInputStream());
                        if(new_object != null){
                            Escalier escalier = new Escalier();
                            escalier.setEtage_courant(new_object.getInt("etage_courant"));
                            escalier.setEtage_destination(new_object.getInt("etage_destination"));
                            escalier.setId(new_object.getInt("id"));

                            String direction = object.getString("direction");
                            switch (direction){
                                case "droite":
                                    Voisins.set(0, escalier);
                                    break;
                                case "gauche":
                                    Voisins.set(1, escalier);
                                    break;
                                case "face":
                                    Voisins.set(2, escalier);
                                    break;
                            }
                        }
                    }
                }


                Voisin voisin = new Voisin();
                voisin.setVoisinD(Voisins.get(0));
                voisin.setVoisinG(Voisins.get(1));
                voisin.setVoisinF(Voisins.get(2));
                return voisin;

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } finally {
                assert connection != null;
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

    public void deplacementFace(View view){
        if(currentVoisin.getVoisinF() != null){
            if(currentVoisin.getVoisinF().getClass().equals(Salle.class)){
                currentSalle = (Salle) currentVoisin.getVoisinF();
                try {
                    new RestVoisin().execute(new URL("http://192.168.1.21:8081/rest/salles/voisins/" + currentSalle.getId()),new URL("http://192.168.1.21:8081/rest/salles/escalier/" + currentSalle.getId()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deplacementDroite(View view){
        if(currentVoisin.getVoisinD() != null) {
            if (currentVoisin.getVoisinD().getClass().equals(Salle.class)) {
                currentSalle = (Salle) currentVoisin.getVoisinD();
                try {
                    new RestVoisin().execute(new URL("http://192.168.1.21:8081/rest/salles/voisins/" + currentSalle.getId()), new URL("http://192.168.1.21:8081/rest/salles/escalier/" + currentSalle.getId()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deplacementGauche(View view){
        if(currentVoisin.getVoisinG() != null){
            if(currentVoisin.getVoisinG().getClass().equals(Salle.class)){
                currentSalle = (Salle) currentVoisin.getVoisinG();
                try {
                    new RestVoisin().execute(new URL("http://192.168.1.21:8081/rest/salles/voisins/" + currentSalle.getId()),new URL("http://192.168.1.21:8081/rest/salles/escalier/" + currentSalle.getId()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}