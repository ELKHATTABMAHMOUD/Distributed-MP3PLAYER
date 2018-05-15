package ceri.mahmoud.signalAcquisition;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import java.lang.reflect.Array;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Map;
import ceri.mahmoud.GUInterface.androidGUI.RecognitionCallback;
import ceri.mahmoud.messageTranscription.WSInvoker;

public class SignalAcqusitionImpl implements  ISignalAcqusition, RecognitionCallback{

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private static StringBuilder command ;
    private ArrayList<String> possibleActions = new ArrayList<String>(10);

    private Context context ;
    public SignalAcqusitionImpl(){
        command = new StringBuilder();
        possibleActions.add("jouer");
        possibleActions.add("jouez");
        possibleActions.add("entendre");
        possibleActions.add("stop");
        possibleActions.add("arrêter");
        possibleActions.add("arrêtez");
        possibleActions.add("pause");

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
        ArrayList<String> allCommands = new ArrayList<String>(10);
        command.append(matches.get(0));
        System.out.println("(RecordCommandactivity.java) recording has been finished ..."+extractCommand(matches));

//        getObjectAction(command.toString());

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

    }
    private String extractCommand(ArrayList<String> allCommands){
        for (int i=0; i< allCommands.size() ; i++)
        {
            for(String cmd : possibleActions) {
                if (allCommands.get(i).toLowerCase().contains(cmd)) {
                    System.out.println(command+"bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb : " + allCommands.get(i));
                    return allCommands.get(i);
                }
            }
        }
        return null;
    }
}
