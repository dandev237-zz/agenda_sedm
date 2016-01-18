package sedm.agenda.dcastro.agenda;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean visible = false;

    private Agenda instance;
    private EditText contactName;
    private Button searchButton, addButton, callButton;
    private TextView resultName, resultNumber;
    private RelativeLayout resultLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultLayout = (RelativeLayout)findViewById(R.id.resultLayout);
        checkVisibility();

        contactName = (EditText)findViewById(R.id.contactText);

        resultName = (TextView)findViewById(R.id.resultNameText);
        resultNumber = (TextView)findViewById(R.id.resultNumberText);

        searchButton = (Button)findViewById(R.id.searchButton);
        addButton = (Button)findViewById(R.id.addButton);
        callButton = (Button)findViewById(R.id.callButton);

        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                searchButtonClick();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addButtonClick();
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callButtonClick();
            }
        });
    }

    private void searchButtonClick(){
        instance = Agenda.getInstance(this);

        if(Utils.checkIfEmpty(contactName)){
            //error, búsqueda de contacto vacío
            Utils.showErrorDialog(getResources().getText(R.string.errorNoContact).toString(), this);
        }else{
            Contacto contacto = instance.getContact(contactName.getText().toString());
            if(contacto == null){
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.errorContactNotFound).toString(), Toast.LENGTH_LONG).show();
            }else{
                resultName.setText(contacto.nombre);
                resultNumber.setText(contacto.numero);
                visible = true;
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.contactFound).toString(), Toast.LENGTH_LONG).show();
            }
        }
        checkVisibility();
    }

    private void addButtonClick(){
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivity(intent);
    }

    private void callButtonClick(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        //Intent intent = new Intent(Intent.ACTION_CALL); Para hacer la llamada directamente
        intent.setData(Uri.parse("tel:" + resultNumber.getText().toString()));
        startActivity(intent);
    }

    private void checkVisibility(){
        if(visible){
            resultLayout.setVisibility(View.VISIBLE);
        }else{
            resultLayout.setVisibility(View.INVISIBLE);
        }
    }
}
