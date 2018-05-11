package ceri.mahmoud.messageTranscription;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ceri.mahmoud.GUInterface.androidGUI.ReadTrackActivity;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class WSInvoker extends Activity{
    private static String action  ;
    private static String object ;
    private static String serviceWebUrl = "http://192.168.0.12:4040/RequestAnalyser/Analyser/getActionObject/jouer moi californie";

    public static Map<String,String> getActionObject(String command, Context context){
        Map<String,String> actionObject = new HashMap<String,String>(2);
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        AsyncHttpClient client = new AsyncHttpClient();
//      client.get(null, serviceWebUrl+command, headers.toArray(new Header[headers.size()]), null,
        client.get(null, serviceWebUrl, headers.toArray(new Header[headers.size()]), null,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            object = (String)  response.get("object");
                            action = (String) response.get("action");
                            actionObject.put("action",action);
                            actionObject.put("object",object);
                            System.out.println("url : " + serviceWebUrl);
                            System.out.println("Invocation du service web reussie : \n action : " + action + " : object : " + object);
                            WSInvoker.startActivity(actionObject, context);
                        } catch (JSONException e) {
                            System.out.println("Your vocal message was not clear!");
                        }

                    }
                });
        return actionObject ;
    }
    public static void startActivity(Map<String,String> transResult, Context context){
        Intent reader = new Intent(context,ReadTrackActivity.class);
        reader.putExtra("object", transResult.get("object"));
        reader.putExtra("action",transResult.get("action"));
        context.startActivity(reader);
    }
}
