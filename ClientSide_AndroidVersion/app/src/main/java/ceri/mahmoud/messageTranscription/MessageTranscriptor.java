package ceri.mahmoud.messageTranscription;

import android.content.Context;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import ceri.mahmoud.signalAcquisition.ISignalAcqusition;
import ceri.mahmoud.signalAcquisition.SignalAcqusitionImpl;
import ceri.mahmoud.signalAcquisition.SignalAcqusitionImpl;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class MessageTranscriptor implements IMessageTranscriptor{

    private static ArrayList<String> transcriptMessage = new ArrayList<String>(5) ;

    @Override
    public String getMessageCommand(Context context) {
        System.out.println("*getMessageCommand*");
        ISignalAcqusition msgFromSignal = new SignalAcqusitionImpl();
        String messageCommand = msgFromSignal.getCommand(context);
        return messageCommand;
    }

}
