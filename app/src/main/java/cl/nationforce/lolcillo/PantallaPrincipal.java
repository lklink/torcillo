package cl.nationforce.lolcillo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PantallaPrincipal extends ActionBarActivity {


    String url, teamSolo;
    String usuarioBienvenidaText;
    TextView usuarioPrincipalTextView, levelUsuarioTextView, tierSoloTextView, lpSoloTextView, kdaSoloTextView,winlossSoloTextView,leagueSoloTextView;
    ImageView IV_Profile,IV_SoloRanked;
    private String urlicono,urlSoloRanked;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        usuarioBienvenidaText = getIntent().getStringExtra("usuarioBienvenidaText");

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //asdfd

        url = "https://na.api.pvp.net/api/lol/las/v1.4/summoner/by-name/" + usuarioBienvenidaText + "?api_key=6fc6de22-229a-47d4-b292-b9821943ffff";


        new JSONParse().execute(url);
    }

    private void addDrawerItems() {
        String[] osArray = { "Buscar", "Opciones"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(PantallaPrincipal.this, "Hola tu! " + position, Toast.LENGTH_SHORT).show();
                if(position == 0){
                    mDrawerList.setItemChecked(position, true);
                    setTitle("Buscar");
                    mDrawerLayout.closeDrawer(mDrawerList);
                    Intent i = new Intent (PantallaPrincipal.this,MainActivity.class);
                    i.putExtra("usuarioBienvenidaText","buscar");
                    startActivity(i);
                }

            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        CharSequence mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navegación");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject[]> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            uid = (TextView)findViewById(R.id.uid);
//            name1 = (TextView)findViewById(R.id.name);
//            email1 = (TextView)findViewById(R.id.email);
            usuarioPrincipalTextView = (TextView) findViewById(R.id.usuarioPrincipalTextView);
            levelUsuarioTextView = (TextView) findViewById(R.id.levelUsuarioTextView);
            tierSoloTextView = (TextView) findViewById(R.id.tierSoloTextView);
            lpSoloTextView = (TextView) findViewById(R.id.lpSoloTextView);
            kdaSoloTextView = (TextView) findViewById(R.id.kdaSoloTextView);
            winlossSoloTextView = (TextView) findViewById(R.id.winlossSoloTextView);
            leagueSoloTextView = (TextView) findViewById(R.id.leagueSoloTextView);
            pDialog = new ProgressDialog(PantallaPrincipal.this);
            pDialog.setMessage("Recibiendo datos...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject[] doInBackground(String... args) {
            JsonParser jParser = new JsonParser();
            JSONObject[] json = new JSONObject[2];
            // Getting JSON from URL
            json[0] = jParser.getJSONFromUrl(args[0]);
            try {
                teamSolo = String.format("https://las.api.pvp.net/api/lol/las/v2.5/league/by-summoner/%s/entry?api_key=6fc6de22-229a-47d4-b292-b9821943ffff", json[0].getJSONObject(usuarioBienvenidaText).getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            json[1] = jParser.getJSONFromUrl(teamSolo);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject[] json) {
            JSONObject jsonUsuario = json[0];


            pDialog.dismiss();
            try {
                // Getting JSON Array
                // user = json.getJSONArray(String.valueOf(usuarioBienvenidaText.getText()));
                //JSONObject c = user.getJSONObject(0);
                // Storing  JSON item in a Variable
                String id = jsonUsuario.getJSONObject(usuarioBienvenidaText).getString("id");
                String name = jsonUsuario.getJSONObject(usuarioBienvenidaText).getString("name");
                String profileIconId = jsonUsuario.getJSONObject(usuarioBienvenidaText).getString("profileIconId");
                int summonerLevel = jsonUsuario.getJSONObject(usuarioBienvenidaText).getInt("summonerLevel");
                int revisionDate = jsonUsuario.getJSONObject(usuarioBienvenidaText).getInt("revisionDate");

                //Set JSON Data in TextView
                usuarioPrincipalTextView.setText(name);
                levelUsuarioTextView.setText("Nivel: "+ summonerLevel);
                IV_Profile = (ImageView)findViewById(R.id.usuarioIconoImageView);
                urlicono = "http://avatar.leagueoflegends.com/las/"+ name +".png";
                Picasso.with(PantallaPrincipal.this)
                        .load(urlicono)
                        .into(IV_Profile);

                //set/get team solo
                JSONArray jsonTeamSolo = json[1].getJSONArray(id);
                JSONObject c = jsonTeamSolo.getJSONObject(0);
                JSONArray jsonTeamSoloEntries = c.getJSONArray("entries");
                JSONObject d = jsonTeamSoloEntries.getJSONObject(0);

                tierSoloTextView.setText(c.getString("tier")+" "+d.getString("division"));
                lpSoloTextView.setText(d.getInt("leaguePoints")+ " Puntos de Liga");
                kdaSoloTextView.setText("Proximamente KDA");
                winlossSoloTextView.setText(d.getInt("wins")+"W - "+ d.getInt("losses")+"L");
                leagueSoloTextView.setText(c.getString("name"));
                IV_SoloRanked = (ImageView)findViewById(R.id.rankedSoloImageView);

                if(c.getString("tier").equals("BRONZE")){
                    urlSoloRanked = "http://1-ps.googleusercontent.com/xk/whxP6Ksdo2cx-tCaurOYI6g4lI/www.lolskill.net/static.lolskill.net/img/tiers/192/xbronze"+d.getString("division")+".png.pagespeed.ic.8etylxDd_zwDtxvZMXQV.png";
                }
                if(c.getString("tier").equals("SILVER")){
                    urlSoloRanked = "http://1-ps.googleusercontent.com/xk/whxP6Ksdo2cx-tCaurOYI6g4lI/www.lolskill.net/static.lolskill.net/img/tiers/192/xsilver"+d.getString("division")+".png.pagespeed.ic.0ffvGDsbUzfWCAx1x3V4.png";
                }
                if(c.getString("tier").equals("GOLD")){
                    urlSoloRanked = "http://1-ps.googleusercontent.com/x/www.lolskill.net/static.lolskill.net/img/tiers/192/gold"+d.getString("division")+".png.pagespeed.ce.9_iuxEb7ek10xpOiCcf1.png";
                }
                if(c.getString("tier").equals("PLATINUM")){
                    urlSoloRanked = "http://1-ps.googleusercontent.com/xk/whxP6Ksdo2cx-tCaurOYI6g4lI/www.lolskill.net/static.lolskill.net/img/tiers/192/platinum"+d.getString("division")+".png.pagespeed.ce.JtGfhNRnSjbNX0L7wcve.png";
                }
                if(c.getString("tier").equals("DIAMOND")){
                    urlSoloRanked = "http://1-ps.googleusercontent.com/xk/whxP6Ksdo2cx-tCaurOYI6g4lI/www.lolskill.net/static.lolskill.net/img/tiers/192/diamond"+d.getString("division")+".png.pagespeed.ce.-HyPrQHOvK4hLRCNWTIU.png";
                }
                if(c.getString("tier").equals("CHALLENGER")){
                    urlSoloRanked = "http://1-ps.googleusercontent.com/xk/whxP6Ksdo2cx-tCaurOYI6g4lI/www.lolskill.net/static.lolskill.net/img/tiers/192/challenger"+d.getString("division")+".png.pagespeed.ce.ZQjm8v-93hrUkDxoU5oe.png";
                }
                Picasso.with(PantallaPrincipal.this)
                        .load(urlSoloRanked)
                        .into(IV_SoloRanked);



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

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
