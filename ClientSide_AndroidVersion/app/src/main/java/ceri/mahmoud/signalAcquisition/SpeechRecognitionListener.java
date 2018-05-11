package ceri.mahmoud.signalAcquisition;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import java.util.ArrayList;

import ceri.mahmoud.GUInterface.androidGUI.RecognitionCallback;

public class SpeechRecognitionListener implements RecognitionListener {
    private final String TAG = "SpeechRecognitionListener";
    private Intent mSpeechRecognizerIntent;
    private SpeechRecognizer mSpeechRecognizer;
    private RecognitionCallback mCallback ;

    public SpeechRecognitionListener(Intent speechRecognizerIntent, SpeechRecognizer speechRecognizer, RecognitionCallback callback ) {
        mSpeechRecognizerIntent = speechRecognizerIntent;
        mSpeechRecognizer = speechRecognizer;
        mCallback = callback;
    }
    @Override
    public void onReadyForSpeech(Bundle params) { }
    @Override
    public void onBeginningOfSpeech() { }
    @Override
    public void onRmsChanged(float rmsdB) { }
    @Override
    public void onBufferReceived(byte[] buffer) { }
    @Override
    public void onEndOfSpeech() { }
    @Override
    public void onError(int error) {
        System.out.println("*onError*");
    }
    @Override
    public void onResults(Bundle results) {
        ArrayList<String> transcriptedMessage = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        mCallback.onRecoginitionFinished(transcriptedMessage);
    }
    @Override
    public void onPartialResults(Bundle partialResults) { }
    @Override
    public void onEvent(int eventType, Bundle params) { }
}
