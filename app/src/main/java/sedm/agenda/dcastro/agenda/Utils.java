package sedm.agenda.dcastro.agenda;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.EditText;

/**
 * Created by Daniel Alejandro Castro García on 11/01/2016.
 * Clase con métodos auxiliares empleados en diversas partes del código de la aplicación
 */
class Utils {

    /**
     * Muestra un cuadro de diálogo de tipo AlertDialog con información sobre un error.
     * @param error Información del error.
     * @param activity La actividad donde se mostrará el diálogo.
     */
    public static void showErrorDialog(String error, Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(error).setTitle("Error");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Comprueba si el texto contenido en un widget de texto es vacío, es decir, no tiene ningún
     * caracter.
     * @param text El widget que contiene el texto a comprobar
     * @return true si el texto es vacío, false en caso contrario
     */
    public static boolean checkIfEmpty(EditText text){
        return text.getText().toString().trim().isEmpty();
    }
}
