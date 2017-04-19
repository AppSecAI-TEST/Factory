package com.aishindus.factory;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by LENOVO on 07-10-2016.
 */

public class Get_Result extends AsyncTask<String, Void, String> {
    public ValidationResponse delegate;
    Context ctx;

    Get_Result(Context ctx) {
        this.ctx = ctx;
    }
    //int rf;

    @Override
    protected String doInBackground(String... params) {
        String resp = "";
        //rf= Integer.parseInt(params[1].substring(1,2));
        try {
            URL dburl = new URL("http://aishwary.heliohost.org/fetch_result1.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) dburl.openConnection();
            httpURLConnection.setReadTimeout(20000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            /*if(rf==1 || rf==4) {
                String data= URLEncoder.encode("Style_Num", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");;
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
            }*/
            InputStream IS = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
                sb.append(line+"\n");
            resp = sb.toString();
            bufferedReader.close();
            IS.close();
            httpURLConnection.disconnect();
        } catch (SocketTimeoutException e) {
            Log.e("SocketTimeoutException", "here");
            resp = "Connection Error. Please Try Again! ";
        } catch (MalformedURLException e) {
            Log.e("SocketTimeoutException", "here" + e);
            resp = "Connection Error. Please Try Again! ";
        } catch (Exception e) {
            Log.e("SocketTimeoutException", "here" + e);
            resp = "Connection Error. Please Try Again! ";
        }

        return resp;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("dopost", "here" +s);
        if (s.equals("Connection Error. Please Try Again! ")) {
            Toast.makeText(ctx, s+" hello", Toast.LENGTH_LONG).show();
            delegate.response(false,s);
        }
        else {
            Log.e("Result", s);
            if (!s.equals("None")) {
                delegate.response(true, s);
            } else {
                delegate.response(false, s);
                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
            }
        }
    }
}
