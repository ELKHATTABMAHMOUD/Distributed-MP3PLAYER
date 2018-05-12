package ceri.mahmoud.signalAcquisition;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Map;
import ceri.mahmoud.GUInterface.androidGUI.RecognitionCallback;
import ceri.mahmoud.messageTranscription.WSInvoker;

public class SignalAcqusitionImpl implements  ISignalAcqusition, RecognitionCallback{

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private static StringBuilder command ;

    private Context context ;
    public SignalAcqusitionImpl(){
        command = new StringBuilder();

    }
    @Override
    public String getSignalMessage(){
        return "";
    }
    @Override
    public String getCommand(Context context) {
        System.out.println("*getCommand*");
        this.context = context;
        recordSignal(context);
        try {
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        }
        catch (Exception ex){
            System.out.println("ERROR MESSAGE : erreur inconnu");
        }
        return command.toString();
    }

    @Override
    public void onRecoginitionFinished(ArrayList<String> matches) {
        command.append(matches.get(0));
        System.out.println("(RecordCommandactivity.java) recording has been finished ...");
        getObjectAction(command.toString());

    }
    private void recordSignal(Context context){
            System.out.println("*recordSignal*");
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
            mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,context.getPackageName());
            SpeechRecognitionListener listener = new SpeechRecognitionListener(mSpeechRecognizerIntent,mSpeechRecognizer,this);
            mSpeechRecognizer.setRecognitionListener(listener);
    }

    private void getObjectAction(String command){
        Map<String,String> actionObject = WSInvoker.getActionObject(command, context);
        System.out.println("Transcription with WS was : ");
        System.out.println("<Action : "+actionObject.get("action")+ ", Object : "+actionObject.get("object")+" >");
    }

}
