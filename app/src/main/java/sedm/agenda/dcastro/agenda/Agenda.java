package sedm.agenda.dcastro.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel Alejandro Castro García on 07/01/2016.
 *
 * Clase dedicada a controlar los accesos a la base de datos.
 * Implementada con el patrón Singleton.
 */

@SuppressWarnings("EmptyMethod")
public class Agenda extends SQLiteOpenHelper{

    private static Agenda instance;

    //Información de la base de datos
    private static final String DATABASE_NAME = "agenda";
    private static final String DATABASE_TABLE = "contactos";
    private static final int DATABASE_VERSION = 1;
    //--------------

    //Columnas de la tabla "contactos"
    private static final String KEY_CONTACTOS_ID = "id";
    private static final String KEY_CONTACTOS_NAME = "nombre";
    private static final String KEY_CONTACTOS_NUMBER = "numero";
    //--------------

    //Constructor + Singleton
    private Agenda(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static Agenda getInstance(Context context){
        if(instance == null){
            instance = new Agenda(context.getApplicationContext());
        }
        return instance;
    }
    //--------------

    //Métodos de configuración, creación y actualización
    @Override
    public void onConfigure(SQLiteDatabase db){
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //CREATE TABLE contactos(id INTEGER PRIMARY KEY, nombre TEXT, numero TEXT)
        String CREATE_TABLE_CONTACTS = "CREATE TABLE " + DATABASE_TABLE + "(" +
                KEY_CONTACTOS_ID + " INTEGER PRIMARY KEY," +
                KEY_CONTACTOS_NAME + " TEXT," +
                KEY_CONTACTOS_NUMBER + " TEXT" +
                ")";

        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
    //--------------

    //Métodos CRUD
    public int addOrUpdateContact(Contacto contact){
        int resultado = 0;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_CONTACTOS_NAME, contact.nombre);
            values.put(KEY_CONTACTOS_NUMBER, contact.numero);

            //'resultado' almacena el número de filas afectadas por la actualización
            resultado = db.update(DATABASE_TABLE, values, KEY_CONTACTOS_NAME + " = ?", new String[]{contact.nombre});

            //Si no ha habido actualización, insertar los datos en la tabla
            if(resultado != 1){
                db.insertOrThrow(DATABASE_TABLE, null, values);
            }

            db.setTransactionSuccessful();
        }catch(Exception e){
            resultado = -1;
        }finally{
            db.endTransaction();
        }

        return resultado;
    }

    public Contacto getContact(String name){
        Contacto contacto = new Contacto();

        String SELECT = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + KEY_CONTACTOS_NAME + " = '" + name + "'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT, null);
        try{
            if(cursor.moveToFirst()){
                    String nombre = cursor.getString(cursor.getColumnIndex(KEY_CONTACTOS_NAME));
                    String numero = cursor.getString(cursor.getColumnIndex(KEY_CONTACTOS_NUMBER));
                    contacto = new Contacto(nombre, numero);
            }else{
                contacto = null;
            }
        }catch(Exception e){
            contacto = null;
        }finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return contacto;
    }


    //--------------
}

