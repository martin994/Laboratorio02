package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class ConfiguracionActivity extends AppCompatActivity {

    public static class ConfiguracionFragmento extends PreferenceFragmentCompat{

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            setPreferencesFromResource(R.xml.configuration_ui,s);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias_compartidas2);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content,new ConfiguracionFragmento())
                .commit();
    }




}
