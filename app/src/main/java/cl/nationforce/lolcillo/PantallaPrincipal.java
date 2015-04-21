package cl.nationforce.lolcillo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class PantallaPrincipal extends ActionBarActivity {


    String url;
    String usuarioBienvenidaText;
    TextView usuarioPrincipalTextView, levelUsuarioTextView;
    ImageView IV_Profile;
    private String urlicono;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        usuarioBienvenidaText = getIntent().getStringExtra("usuarioBienvenidaText");

        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();

        url = "https://na.api.pvp.net/api/lol/las/v1.4/summoner/by-name/" + usuarioBienvenidaText + "?api_key=6fc6de22-229a-47d4-b292-b9821943ffff";

        new JSONParse().execute(url);
    }

    private void addDrawerItems() {
        String[] osArray = { "Buscar", "Opciones"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            uid = (TextView)findViewById(R.id.uid);
//            name1 = (TextView)findViewById(R.id.name);
//            email1 = (TextView)findViewById(R.id.email);
            usuarioPrincipalTextView = (TextView) findViewById(R.id.usuarioPrincipalTextView);
            levelUsuarioTextView = (TextView) findViewById(R.id.levelUsuarioTextView);
            pDialog = new ProgressDialog(PantallaPrincipal.this);
            pDialog.setMessage("Recibiendo datos...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JsonParser jParser = new JsonParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(args[0]);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array
                // user = json.getJSONArray(String.valueOf(usuarioBienvenidaText.getText()));
                //JSONObject c = user.getJSONObject(0);
                // Storing  JSON item in a Variable
                String id = json.getJSONObject(usuarioBienvenidaText).getString("id");
                String name = json.getJSONObject(usuarioBienvenidaText).getString("name");
                String profileIconId = json.getJSONObject(usuarioBienvenidaText).getString("profileIconId");
                int summonerLevel = json.getJSONObject(usuarioBienvenidaText).getInt("summonerLevel");
                int revisionDate = json.getJSONObject(usuarioBienvenidaText).getInt("revisionDate");
                //Set JSON Data in TextView
                usuarioPrincipalTextView.setText(name);
                levelUsuarioTextView.setText("Nivel: "+ summonerLevel);
                IV_Profile = (ImageView)findViewById(R.id.usuarioIconoImageView);
                urlicono = "http://avatar.leagueoflegends.com/las/"+ name +".png";
                Picasso.with(PantallaPrincipal.this)
                        .load(urlicono)
                        .into(IV_Profile);


                //descargarArchivo(urlicono);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
