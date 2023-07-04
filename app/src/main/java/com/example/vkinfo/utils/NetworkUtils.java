package com.example.vkinfo.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NetworkUtils {
    private static  final String VK_API_BASE_URL = "https://api.vk.com";
    private static  final String VK_USERS_GET = "/method/users.get";
    private static final String PARAM_USER_ID = "user_ids";
    private static final String PARAM_VERSION = "v";
    private static final String ACCESS_TOKEN = "access_token";


    public static URL generateURL(String userIDs){
        Uri builtUri = Uri.parse( VK_API_BASE_URL + VK_USERS_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_ID, userIDs)
                .appendQueryParameter(PARAM_VERSION, "5.131")
                .appendQueryParameter(ACCESS_TOKEN, "")
                .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //
    public static String getResponseFromUrl(URL url) throws IOException {
        //open connect with URL
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            //get answer from query

            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            //get the whole line at once
            //do not split into substrings
            //default separator " " - for this can not use .useDelimiter()
            scanner.useDelimiter("\\A");
            boolean hasNext = scanner.hasNext();
            if (hasNext)
                return scanner.next();
            else
                return null;
        }catch (UnknownHostException e){
            System.out.println(e.getMessage().toString());
            return null;
        }
        finally {
            urlConnection.disconnect();
        }
    }
}
