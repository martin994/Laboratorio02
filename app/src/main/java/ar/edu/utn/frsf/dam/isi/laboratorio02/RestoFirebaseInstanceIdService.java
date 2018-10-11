package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class RestoFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public RestoFirebaseInstanceIdService() {
    }
    public void onTokenRefresh(){

    }

}
