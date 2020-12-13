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
import com.example.androidsig.modele.EscalierSalle;
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
    private List<Escalier> escalierList;
    private Object currentSalle;
    private Voisin currentVoisin;
    private WebView myWebView;
    // ----->
    private List<Object> parcoursTemporaire;
    // <-----

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        salleList = new ArrayList<>();
        currentSalle = null;
        currentVoisin = null;
        escalierList = new ArrayList<>();

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
            URL url = new URL(getString(R.string.urlSpring) + "salles");
            new SpringRestSalle().execute(url);
            url = new URL(getString(R.string.urlSpring) + "escalier");
            new SpringRestEscalier().execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateView(){
        if(currentSalle.getClass().equals(Salle.class)){
            Salle salle = (Salle) currentSalle;
            TextView textView = findViewById(R.id.textViewMain);
            textView.setText(salle.getType_salle() + " : " + salle.getNom());
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



        }else{
            Escalier escalier = (Escalier) currentSalle;
            TextView textView = findViewById(R.id.textViewMain);
            textView.setText("Escalier " + escalier.getId());

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
                    if(((Escalier)currentVoisin.getVoisinF()).getEtage_courant() == 0){
                        textView.setText("Descendre");
                    }else if(((Escalier)currentVoisin.getVoisinF()).getEtage_courant() == 1){
                        textView.setText("Monter");
                    }

                }else{
                    textView.setText("Pas de voisin");
                }
            }


        }
        if(currentSalle.getClass().equals(Salle.class)){
            if(((Salle)currentSalle).getEtage() == 0){
                myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage0");
            }else{
                myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage1");
            }
        }else {
            if(((Escalier)currentSalle).getEtage_courant() == 0){
                myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage0");
            }else{
                myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage1");
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SpringRestSalle extends AsyncTask<URL, Void, JSONArray>{

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

    private class SpringRestEscalier extends AsyncTask<URL, Void, JSONArray>{

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
                updateListEscalier(jsonObject);
            }
        }
    }

    private void updateListEscalier(JSONArray jsonObject) {
        escalierList.clear();
        for (int i = 0; i < jsonObject.length(); i++){
            try {
                JSONObject object = jsonObject.getJSONObject(i);
                Escalier escalier = new Escalier();
                escalier.setEtage_destination(object.getInt("etage_destination"));
                escalier.setEtage_courant(object.getInt("etage_courant"));
                escalier.setId(object.getInt("id"));

                escalierList.add(escalier);
            } catch (JSONException e) {
                e.printStackTrace();
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
                new RestVoisin().execute(new URL(getString(R.string.urlSpring) + "salles/voisins/" + ((Salle)currentSalle).getId()),new URL(getString(R.string.urlSpring) + "salles/escalier/" + ((Salle)currentSalle).getId()));
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
                            URL url = new URL(getString(R.string.urlSpring) + "salles/" + id);
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
                if(reponse == HttpURLConnection.HTTP_OK){
                    JSONObject object = getText(connection.getInputStream());
                    if(object != null){
                        int id = object.getInt("escalier");
                        URL url = new URL(getString(R.string.urlSpring) + "Escalier/" + id);
                        connection.disconnect();
                        connection = (HttpURLConnection) url.openConnection();
                        reponse = connection.getResponseCode();
                        Log.d("Code ", ""+reponse);
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
            if(!stringBuilder.toString().equals("")){
                return new JSONObject(stringBuilder.toString());
            }else{
                return null;
            }

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
            updateLocalisation("face");
            if(currentVoisin.getVoisinF().getClass().equals(Salle.class)){
                currentSalle = currentVoisin.getVoisinF();
                try {
                    new RestVoisin().execute(new URL(getString(R.string.urlSpring) + "salles/voisins/" + ((Salle)currentSalle).getId()),new URL(getString(R.string.urlSpring) + "salles/escalier/" + ((Salle)currentSalle).getId()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }else{
                currentSalle = currentVoisin.getVoisinF();
                currentVoisin = new Voisin();

                try {
                    URL url = new URL(getString(R.string.urlSpring) + "Escalier/joint/" + ((Escalier)currentSalle).getId());
                    new EscalierJoint().execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deplacementDroite(View view){
        if(currentVoisin.getVoisinD() != null) {
            updateLocalisation("right");
            if (currentVoisin.getVoisinD().getClass().equals(Salle.class)) {
                currentSalle = currentVoisin.getVoisinD();
                try {
                    new RestVoisin().execute(new URL(getString(R.string.urlSpring) + "salles/voisins/" + ((Salle)currentSalle).getId()), new URL(getString(R.string.urlSpring) + "salles/escalier/" + ((Salle)currentSalle).getId()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }else{
                currentSalle = currentVoisin.getVoisinF();
                currentVoisin = new Voisin();
                try {
                    URL url = new URL(getString(R.string.urlSpring) + "Escalier/joint/" + ((Escalier)currentSalle).getId());
                    new EscalierJoint().execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deplacementGauche(View view){
        if(currentVoisin.getVoisinG() != null){
            updateLocalisation("left");
            if(currentVoisin.getVoisinG().getClass().equals(Salle.class)){
                currentSalle = currentVoisin.getVoisinG();
                try {
                    new RestVoisin().execute(new URL(getString(R.string.urlSpring) + "salles/voisins/" + ((Salle)currentSalle).getId()),new URL(getString(R.string.urlSpring) + "salles/escalier/" + ((Salle)currentSalle).getId()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }else{
                currentSalle = currentVoisin.getVoisinG();
                currentVoisin = new Voisin();
                try {
                    URL url = new URL(getString(R.string.urlSpring) + "Escalier/joint/" + ((Escalier)currentSalle).getId());
                    new EscalierJoint().execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void updateLocalisation(String direction) {
        String url = getString(R.string.urlSpring) + "position/1/" + direction + "/";
        if(currentSalle.getClass().equals(Escalier.class)){
            url += "escalier/";
        }else{
            url += "salle/";
        }
        switch (direction){
            case "left":
                if(currentVoisin.getVoisinG().getClass().equals(Escalier.class)){
                    url += "escalier";
                }else{
                    url += "salle";
                }
                break;
            case "right":
                if(currentVoisin.getVoisinD().getClass().equals(Escalier.class)){
                    url += "escalier";
                }else{
                    url += "salle";
                }
                break;
            case "face":
                if(currentVoisin.getVoisinF().getClass().equals(Escalier.class)){
                    url += "escalier";
                }else{
                    url += "salle";
                }
                break;
        }

        URL newurl = null;
        try {
            newurl = new URL(url);
            new UpdateLocalisation().execute(newurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private class UpdateLocalisation extends AsyncTask<URL, Void, Void> {

        @Override
        protected Void doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                int reponse = connection.getResponseCode();

                if(reponse == HttpURLConnection.HTTP_OK){
                    //TODO
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class EscalierJoint extends AsyncTask<URL, Void, EscalierSalle>{

        @Override
        protected EscalierSalle doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                int response = connection.getResponseCode();
                if(response == HttpURLConnection.HTTP_OK){
                    JSONObject object = getText(connection.getInputStream());
                    EscalierSalle escalierSalle = new EscalierSalle();
                    escalierSalle.setIdvoisind(object.getInt("idvoisind"));
                    escalierSalle.setIdvoising(object.getInt("idvoising"));
                    escalierSalle.setIdvoisinf(object.getInt("idvoisinf"));

                    return escalierSalle;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
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
        protected void onPostExecute(EscalierSalle escalier) {
            setVoisinEscalier(escalier);
        }
    }

    // ----->
    private class navigationParcoursLargeur {

        protected ArrayList<Object> parcoursToSalle(Object current) {
            System.out.println("TestInnerClass");
            System.out.println(salleList);
            System.out.println(escalierList);
            // On lance la recurcivite
            return avancerDansParcours(current);
        }

        protected ArrayList<Object> avancerDansParcours(Object current) {
            // Le chemin que l'on vas parcourir
            ArrayList<Object> chemin = new ArrayList<>();
            // Les endroits deja visites
            ArrayList<Object> visite = new ArrayList<>();
            return null;
        }

    }
    // <-----

    private void setVoisinEscalier(EscalierSalle escalier) {
        if(escalier != null){
            currentVoisin.setVoisinF(getEscalierFromId(escalier.getIdvoisinf()));
            currentVoisin.setVoisinG(getSalleFromId(escalier.getIdvoising()));
            currentVoisin.setVoisinD(getSalleFromId(escalier.getIdvoisind()));
            this.updateView();
        }
    }

    private Salle getSalleFromId(int id){
        for (Salle salle : salleList){
            if(salle.getId() == id){
                return salle;
            }
        }
        return null;
    }

    private Escalier getEscalierFromId(int id){
        for (Escalier escalier : escalierList){
            if(escalier.getId() == id){
                return escalier;
            }
        }
        return null;
    }

    // ----->
    private ArrayList<Object> setPlusCourtChemin(Object current) {
        navigationParcoursLargeur navClass = new navigationParcoursLargeur();
        return navClass.parcoursToSalle(current);
    }

    // Utilise sur le bouton
    public void TempoFuncForNav(View view) {
        System.out.println(setPlusCourtChemin(currentSalle));
    }
    // <-----

}