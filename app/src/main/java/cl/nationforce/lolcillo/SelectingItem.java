package cl.nationforce.lolcillo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
/**
 * Created by seba on 14-04-2015.
 */
public class SelectingItem implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        Toast.makeText(parent.getContext(),
                "Servidor seleccionado : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
