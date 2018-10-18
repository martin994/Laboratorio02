package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class RestoFirebaseInstanceIdService {/*extends FirebaseInstanceIdService {
    public static final String TAG = "RestoFirebaseInstanceIdService";
    public RestoFirebaseInstanceIdService() {
    }
    @SuppressLint("LongLogTag")
    public void onTokenRefresh(){
        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed Token: " + refreshedToken);
        guardarToken(refreshedToken);
    }
    @SuppressLint("LongLogTag")
    public void onCreate(){
        super.onCreate();
        Log.d(TAG,"Refreshed Token: SERVICIO CREADO!" );
    }
    private void guardarToken(String _token){
        SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("registration_id", _token);
        editor.apply();
    }
    /*private String leerToken(){
        SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id",null);
    }*/
}
