package sedm.agenda.dcastro.agenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEntryActivity extends AppCompatActivity {

    private Agenda instance;
    private EditText contactName, contactNumber;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        contactName = (EditText)findViewById(R.id.contactName_Text);
        contactNumber = (EditText)findViewById(R.id.contactNumber_Text);
        addButton = (Button)findViewById(R.id.addContactButton);

        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addContactButtonClick();
            }
        });

    }

    private void addContactButtonClick() {
        String nombre = contactName.getText().toString();
        String numero = contactNumber.getText().toString();

        if(Utils.checkIfEmpty(contactName) || Utils.checkIfEmpty(contactNumber)){
            Utils.showErrorDialog(getResources().getText(R.string.errorNoData).toString(), this);
        }else{
            Contacto contacto = new Contacto(nombre, numero);

            instance = Agenda.getInstance(getApplicationContext());

            int resultado = instance.addOrUpdateContact(contacto);

            if(resultado > 0){ //Se ha actualizado el contacto
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.contactUpdated).toString() + " " + nombre, Toast.LENGTH_LONG).show();
            }else if(resultado == 0){ //No se ha actualizado, inserción
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.contactAdded).toString() + " " + nombre, Toast.LENGTH_LONG).show();
            }else{ //Situación de error
                Utils.showErrorDialog(getResources().getText(R.string.errorDBAccess).toString(), this);
            }


        }
    }

}
