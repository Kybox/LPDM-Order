package com.lpdm.msorder.model.paypal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class PaypalSuccess {

    public void getPaypal(HttpServletRequest request, PaypalConfig paypalConfig){

        String[] temp = null;
        String res = "";

        try{
            String transId = request.getParameter("tx");
            String authToken = paypalConfig.getAuthToken();
            String query = "cmd=_notify-synch&tx=" + transId + "&at=" + authToken;
            String postUrl = paypalConfig.getPostUrl();
            URL url = new URL(postUrl);

            HttpURLConnection req = (HttpURLConnection) url.openConnection();
            req.setRequestMethod("POST");
            req.setDoOutput(true);
            req.setDoInput(true);
            req.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            req.setFixedLengthStreamingMode(query.length());

            OutputStream outputStream = req.getOutputStream();
            outputStream.write(query.getBytes());
            outputStream.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream()));
            res = in.readLine();
            if(res.equals("SUCCESS")){
                while ((res = in.readLine()) != null){
                    temp = res.split("=");
                    if(temp.length == 1) continue;
                    temp[1] = URLDecoder.decode(temp[1], "UTF-8");
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
