package com.example.androidsig;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.androidsig.modele.Escalier;
import com.example.androidsig.modele.EscalierSalle;
import com.example.androidsig.modele.Position;
import com.example.androidsig.modele.Salle;
import com.example.androidsig.modele.Voisin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private List<Object> pathDirection;
    private Object currentSalle;
    private Voisin currentVoisin;
    private Position currentPosition;
    private WebView myWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        salleList = new ArrayList<>();
        currentSalle = null;
        currentVoisin = new Voisin();
        escalierList = new ArrayList<>();
        currentPosition = new Position();
        pathDirection = new ArrayList<>();

        this.loadPosition();
        this.loadData();


        myWebView = findViewById(R.id.webview);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        myWebView.getSettings().setUseWideViewPort(true);
    }

    private void loadPosition() {
        try {
            URL url = new URL(getString(R.string.urlSpring) + "position");
            new PositionTask().execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
    public void updateView( ){
        if(currentSalle.getClass().equals(Salle.class)){
            Salle salle = (Salle) currentSalle;
            TextView textView = findViewById(R.id.textViewMain);
            textView.setText(salle.getNom());
            if(currentVoisin != null ){
                textView = findViewById(R.id.VoisinDroite);
                if(currentVoisin.getVoisinD() != null){
                    if (currentVoisin.getVoisinD().getClass().equals(Salle.class)){
                        textView.setText(((Salle)currentVoisin.getVoisinD()).getNom());
                    }else if (currentVoisin.getVoisinD().getClass().equals(Escalier.class)){
                        textView.setText(getString(R.string.escalier) + ((Escalier)currentVoisin.getVoisinD()).getId());
                    }
                }else{
                    textView.setText(R.string.NoVoisin);
                }
                textView = findViewById(R.id.VoisinGauche);
                if(currentVoisin.getVoisinG() != null){
                    if (currentVoisin.getVoisinG().getClass().equals(Salle.class)){
                        textView.setText(((Salle)currentVoisin.getVoisinG()).getNom());
                    }else if (currentVoisin.getVoisinG().getClass().equals(Escalier.class)){
                        textView.setText(getString(R.string.escalier) + ((Escalier)currentVoisin.getVoisinG()).getId());
                    }
                }else{
                    textView.setText(R.string.NoVoisin);
                }
                textView = findViewById(R.id.VoisinFace);
                if(currentVoisin.getVoisinF() != null){
                    if (currentVoisin.getVoisinF().getClass().equals(Salle.class)){
                        textView.setText(((Salle)currentVoisin.getVoisinF()).getNom());
                    }else if (currentVoisin.getVoisinF().getClass().equals(Escalier.class)){
                        textView.setText(getString(R.string.escalier) + ((Escalier)currentVoisin.getVoisinF()).getId());
                    }
                }else{
                    textView.setText(R.string.NoVoisin);
                }
            }



        }
        else{
            Escalier escalier = (Escalier) currentSalle;
            TextView textView = findViewById(R.id.textViewMain);
            textView.setText(getString(R.string.escalier) + escalier.getId());

            if(currentVoisin != null ){
                textView = findViewById(R.id.VoisinDroite);
                if(currentVoisin.getVoisinD() != null){
                    if (currentVoisin.getVoisinD().getClass().equals(Salle.class)){
                        textView.setText(((Salle)currentVoisin.getVoisinD()).getNom());
                    }else if (currentVoisin.getVoisinD().getClass().equals(Escalier.class)){
                        textView.setText(getString(R.string.escalier) + ((Escalier)currentVoisin.getVoisinD()).getId());
                    }
                }else{
                    textView.setText(R.string.NoVoisin);
                }
                textView = findViewById(R.id.VoisinGauche);
                if(currentVoisin.getVoisinG() != null){
                    if (currentVoisin.getVoisinG().getClass().equals(Salle.class)){
                        textView.setText(((Salle)currentVoisin.getVoisinG()).getNom());
                    }else if (currentVoisin.getVoisinG().getClass().equals(Escalier.class)){
                        textView.setText(getString(R.string.escalier) + ((Escalier)currentVoisin.getVoisinG()).getId());
                    }
                }else{
                    textView.setText(R.string.NoVoisin);
                }
                textView = findViewById(R.id.VoisinFace);
                if(currentVoisin.getVoisinF() != null){
                    if(((Escalier)currentVoisin.getVoisinF()).getEtage_courant() == 0){
                        textView.setText(R.string.descendre);
                    }else if(((Escalier)currentVoisin.getVoisinF()).getEtage_courant() == 1){
                        textView.setText(R.string.monter);
                    }

                }else{
                    textView.setText(R.string.NoVoisin);
                }
            }


        }
        if(currentSalle.getClass().equals(Salle.class)){
            if(((Salle)currentSalle).getEtage() == 0){
                myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage0");
            }else{
                myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage1");
            }
        }
        else {
            if(((Escalier)currentSalle).getEtage_courant() == 0){
                myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage0");
            }else{
                myWebView.loadUrl(getString(R.string.urlnodejs) + "/etage1");
            }
        }
        this.updateSpinner();
        this.updatePath();
    }

    private void updatePath() {
        FloatingActionButton floatingActionButtonG = findViewById(R.id.floatingActionButton4);
        FloatingActionButton floatingActionButtonD = findViewById(R.id.floatingActionButton2);
        FloatingActionButton floatingActionButtonF = findViewById(R.id.floatingActionButton5);
        floatingActionButtonD.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.grey));
        floatingActionButtonG.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.grey));
        floatingActionButtonF.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.grey));
        if(pathDirection.size() != 0){
            if(currentSalle.getClass().equals(Salle.class)){
                if(currentSalle.equals(pathDirection.get(0))){
                    pathDirection.remove(0);
                    Log.d("List", pathDirection.toString());
                    if(pathDirection.size() != 0){
                        if(pathDirection.get(0).getClass().equals(Salle.class)){
                            Salle s = (Salle)pathDirection.get(0);
                            if(currentVoisin.getVoisinD()!= null && currentVoisin.getVoisinD().getClass().equals(Salle.class)){
                                if(currentVoisin.getVoisinD().equals(s)){
                                    floatingActionButtonD.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.lightblue));
                                }
                            }
                            if(currentVoisin.getVoisinF()!= null && currentVoisin.getVoisinF().getClass().equals(Salle.class)){
                                if(currentVoisin.getVoisinF().equals(s)){
                                    floatingActionButtonF.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.lightblue));
                                }
                            }
                            if(currentVoisin.getVoisinG()!= null && currentVoisin.getVoisinG().getClass().equals(Salle.class)){
                                if(currentVoisin.getVoisinG().equals(s)){
                                    floatingActionButtonG.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.lightblue));
                                }
                            }
                        }else{
                            Escalier escalier = (Escalier) pathDirection.get(0);
                            Log.d("Test", escalier.toString());
                            if(currentVoisin.getVoisinD()!= null && currentVoisin.getVoisinD().getClass().equals(Escalier.class)){
                                if(currentVoisin.getVoisinD().equals(escalier)){
                                    floatingActionButtonD.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.lightblue));
                                }
                            }
                            if(currentVoisin.getVoisinF()!= null && currentVoisin.getVoisinF().getClass().equals(Escalier.class)){
                                if(currentVoisin.getVoisinF().equals(escalier)){
                                    floatingActionButtonF.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.lightblue));
                                }
                            }
                            if(currentVoisin.getVoisinG()!= null && currentVoisin.getVoisinG().getClass().equals(Escalier.class)){
                                if(currentVoisin.getVoisinG().equals(escalier)){
                                    floatingActionButtonG.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.lightblue));
                                }
                            }
                        }
                    }
                }else{
                    Salle sall = (Salle) currentSalle;
                    URL url = null;
                    try {
                        url = new URL(getString(R.string.urlSpring) + "parcours/" +  sall.getId() + "/" + ((Salle)pathDirection.get(pathDirection.size() - 1)).getId());
                        new ParcoursTask().execute(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                if(currentSalle.equals(pathDirection.get(0))){
                    pathDirection.remove(0);
                    Log.d("List", pathDirection.toString());
                    if(pathDirection.size() != 0){
                        if(pathDirection.get(0).getClass().equals(Salle.class)){
                            Salle s = (Salle)pathDirection.get(0);
                            if(currentVoisin.getVoisinD()!= null && currentVoisin.getVoisinD().getClass().equals(Salle.class)){
                                if(currentVoisin.getVoisinD().equals(s)){
                                    floatingActionButtonD.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.lightblue));
                                }
                            }
                            if(currentVoisin.getVoisinF()!= null && currentVoisin.getVoisinF().getClass().equals(Salle.class)){
                                if(currentVoisin.getVoisinF().equals(s)){
                                    floatingActionButtonF.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.lightblue));
                                }
                            }
                            if(currentVoisin.getVoisinG()!= null && currentVoisin.getVoisinG().getClass().equals(Salle.class)){
                                if(currentVoisin.getVoisinG().equals(s)){
                                    floatingActionButtonG.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.lightblue));
                                }
                            }
                        }else{
                            Escalier escalier = (Escalier) pathDirection.get(0);
                            Log.d("Test", escalier.toString());
                            floatingActionButtonF.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.lightblue));
                        }
                    }
                }else{
                    Salle sall = (Salle) currentSalle;
                    URL url = null;
                    try {
                        url = new URL(getString(R.string.urlSpring) + "parcours/" +  sall.getId() + "/" + ((Salle)pathDirection.get(pathDirection.size() - 1)).getId());
                        new ParcoursTask().execute(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
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

        if(currentSalle == null){
            if(currentPosition.getIdescalier() != 0){
                currentSalle = getEscalierFromId(currentPosition.getIdescalier());
                try {
                    URL url = new URL(getString(R.string.urlSpring) + "Escalier/joint/" + ((Escalier)currentSalle).getId());
                    new EscalierJoint().execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
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
            if(currentPosition.getIdsalle() != 0){
                currentSalle = getSalleFromId(currentPosition.getIdsalle());
                try {
                    new RestVoisin().execute(new URL(getString(R.string.urlSpring) + "salles/voisins/" + ((Salle)currentSalle).getId()),new URL(getString(R.string.urlSpring) + "salles/escalier/" + ((Salle)currentSalle).getId()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateSpinner(){
        Spinner spinner = findViewById(R.id.spinner);

        List<Salle> salles = new ArrayList<>(salleList);
        if(currentSalle.getClass().equals(Salle.class)){
            Salle s = (Salle) currentSalle;
            salles.remove(s);

        }
        ArrayAdapter<Salle> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, salles);
        spinner.setAdapter(arrayAdapter);
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
            this.loadPosition();
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
            this.loadPosition();
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
            this.loadPosition();
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
                    url += "escalier/" + ((Escalier)currentVoisin.getVoisinG()).getId();
                }else{
                    url += "salle/" + ((Salle)currentVoisin.getVoisinG()).getId();
                }
                break;
            case "right":
                if(currentVoisin.getVoisinD().getClass().equals(Escalier.class)){
                    url += "escalier/" + ((Escalier)currentVoisin.getVoisinD()).getId();
                }else{
                    url += "salle/" + ((Salle)currentVoisin.getVoisinD()).getId();
                }
                break;
            case "face":
                if(currentVoisin.getVoisinF().getClass().equals(Escalier.class)){
                    url += "escalier/" + ((Escalier)currentVoisin.getVoisinF()).getId();
                }else{
                    url += "salle/" + ((Salle)currentVoisin.getVoisinF()).getId();
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
        Log.d("ListEscae", escalierList.toString());
        for (Escalier escalier : escalierList){
            if(escalier.getId() == id){
                return escalier;
            }
        }
        return null;
    }

    public void searchPath(View view){
        Spinner spinner = findViewById(R.id.spinner);
        Salle salle = (Salle)spinner.getSelectedItem();
        Log.d("Path", String.valueOf(salle));

        if(currentSalle.getClass().equals(Salle.class)){
            Salle sall = (Salle) currentSalle;
            URL url = null;
            try {
                url = new URL(getString(R.string.urlSpring) + "parcours/" +  sall.getId() + "/" + salle.getId());
                new ParcoursTask().execute(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }

    private class ParcoursTask extends AsyncTask<URL, Void, List<Object>>{

        @Override
        protected List<Object> doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            List<Object> list = new ArrayList<>();
            try{
                connection = (HttpURLConnection) urls[0].openConnection();
                int response = connection.getResponseCode();

                if(response == HttpURLConnection.HTTP_OK){
                    StringBuilder stringBuilder = new StringBuilder();
                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                        String line;
                        while ((line = reader.readLine()) != null){
                            stringBuilder.append(line);
                        }
                        JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            if(object.toString().contains("etage_courant")){
                                Escalier escalier = new Escalier();
                                escalier.setId(object.getInt("id"));
                                escalier.setEtage_courant(object.getInt("etage_courant"));
                                escalier.setEtage_destination(object.getInt("etage_destination"));
                                list.add(escalier);
                            }else{
                                Salle salle = new Salle();
                                salle.setId(object.getInt("id"));
                                salle.setNom(object.getString("nom"));
                                salle.setEtage(object.getInt("etage"));
                                salle.setType_salle(object.getString("type_salle"));
                                list.add(salle);
                            }
                        }
                        return list;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Object> salles) {
            pathDirection = salles;
            updatePath();
        }
    }

    private class PositionTask extends AsyncTask<URL, Void, Position>{
        @Override
        protected Position doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            try {
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
                    JSONObject object = new JSONObject(stringBuilder.toString());

                    Position position = new Position();
                    position.setId(object.getInt("id"));
                    position.setIdescalier(object.getInt("idescalier"));
                    position.setIdsalle(object.getInt("idsalle"));

                    return position;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Position position) {
            currentPosition = position;
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
}