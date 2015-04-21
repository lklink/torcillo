package cl.nationforce.lolcillo;


import android.content.Intent;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.view.inputmethod.InputMethodManager;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity{
   //variables para icono
    private String urlicono;
    private Bitmap loadedImage;
    String url;
    ImageView IV_Profile;
    //fin variables icono
    private EditText usuarioBienvenidaText;
    private Spinner serverSpinner;
    private Button sgteButton;
    TextView usuarioPrincipalTextView, levelUsuarioTextView;
    JSONObject jsonObjectUsuario;
    JSONArray user = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serverSpinner = (Spinner)findViewById(R.id.serverSpinner);
        usuarioBienvenidaText=(EditText)findViewById(R.id.usuarioBienvenidaText);
        serverSpinner.setOnItemSelectedListener(new SelectingItem());
        sgteButton=(Button)findViewById(R.id.sgteButton);
        sgteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        esconderTeclado();
                        Intent i = new Intent (MainActivity.this,PantallaPrincipal.class);
                        i.putExtra("usuarioBienvenidaText",String.valueOf(usuarioBienvenidaText.getText()));
                        startActivity(i);
                        //setContentView(R.layout.fragment_pantalla_principal);
                        //asdf
                        //url = "https://na.api.pvp.net/api/lol/las/v1.4/summoner/by-name/" + String.valueOf(usuarioBienvenidaText.getText()) + "?api_key=6fc6de22-229a-47d4-b292-b9821943ffff";

                        //new JSONParse().execute(url);

                    }
                });
    }


    public void esconderTeclado(){

        // esconde teclado al presionar boton sgte

       InputMethodManager imm = (InputMethodManager) getSystemService(
                    MainActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(usuarioBienvenidaText.getWindowToken(), 0);
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
