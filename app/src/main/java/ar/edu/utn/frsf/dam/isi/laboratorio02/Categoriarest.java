package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class Categoriarest {




    public void crearCategoria(Categoria c) {
        try{
            //Variables de conexión y stream de escritura y lectura
            HttpURLConnection urlConnection = null;
            DataOutputStream printout =null;
            InputStream in =null;

            //Crear el objeto json que representa una categoria
            JSONObject categoriaJson = new JSONObject();

            categoriaJson.put("nombre",c.getNombre().toString());

        //Abrir una conexión al servidor para enviar el POST
            URL url = new URL("http://10.15.155.236:3000/categorias");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            //Obtener el outputStream para escribir el JSON
            printout = new DataOutputStream(urlConnection.getOutputStream());
            String str = categoriaJson.toString();
            byte[] jsonData=str.getBytes("UTF-8");
            printout.write(jsonData);
            printout.flush();
            //Leer la respuesta
            in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            int data = isw.read();
            //Analizar el codigo de lar respuesta
            if( urlConnection.getResponseCode() ==200 ||
                    urlConnection.getResponseCode()==201){
                while (data != -1) {
                    char current = (char) data;
                    sb.append(current);
                    data = isw.read();
                }
                //analizar los datos recibidos
                Log.d("LAB_04",sb.toString());
            }else{
                // lanzar excepcion o mostrar mensaje de error
               Log.d("error","No se pudo completar la operacion");

            }
            // caputurar todas las excepciones y en el bloque finally
            // cerrar todos los streams y HTTPUrlCOnnection
            if(printout!=null) printout.close();
            if(in!=null)
             in.close();
            if(urlConnection !=null)urlConnection.disconnect();


    }
    catch (Exception e) { e.printStackTrace(); }
    }



}
