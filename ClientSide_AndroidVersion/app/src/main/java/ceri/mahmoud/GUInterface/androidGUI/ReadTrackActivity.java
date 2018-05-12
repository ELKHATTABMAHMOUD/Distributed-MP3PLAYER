package ceri.mahmoud.GUInterface.androidGUI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.rtp.AudioStream;
import android.net.rtp.RtpStream;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Lecteurmp3.ManagerPrx;
import ceri.mahmoud.messageTranscription.WSInvoker;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class ReadTrackActivity extends Activity implements View.OnClickListener {
    private ImageView playButton ;
    private ImageView backButton ;

    private MediaPlayer mPlayer ;
    private ProgressDialog pDialog ;
    private boolean initialStage =true ;
    private boolean playPause;
    String videoURL = "http://192.168.0.12:8090/test.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_track);
        playButton = (ImageView) findViewById(R.id.playButton);
        backButton = (ImageView) findViewById(R.id.backButton) ;

        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        pDialog = new ProgressDialog(this);

        playButton.setOnClickListener(this);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*******************  this will be used to call ice server ***********************
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*****************************************************************
                try (Communicator communicator = Util.initialize()) {
                    com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("LecteurMP3:tcp -h 192.168.1.42 -p 20000");
                    ManagerPrx lecteurmp3 = ManagerPrx.checkedCast(base);
                    if (lecteurmp3 == null) {
                        throw new Error("Invalid proxy");
                    }
                    lecteurmp3.streamSound("sounds\\test.mp3", 100);
                }
            }
        });
         ************************************************************************/
    }
    @Override
    public void onClick(View v) {
        /*
        String url = getEncodedUrl(videoURL);
        new Player().execute(url);
        */
        if(!playPause){
            playButton.setImageResource(R.drawable.ic_pause_32);
            if(initialStage){
                new Player().execute(videoURL);
            }
            else{
                if(!mPlayer.isPlaying()){
                    mPlayer.start();
                }
            }
            playPause=true;
        }
        else{
            playButton.setImageResource(R.drawable.ic_play_bis_48);
            if(mPlayer.isPlaying()){
                mPlayer.pause();
            }
            playPause=false ;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPlayer !=null){
            mPlayer.reset();
            mPlayer.release();
            mPlayer=null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_track, menu);
        getActionBar().setIcon(R.drawable.ic_read_48);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private String getEncodedUrl(String mediaUrl){
        URL url = null;
        String urlStr = null ;
        try {
            url = new URL(mediaUrl);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            urlStr = uri.toASCIIString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return urlStr ;
    }
    class Player extends AsyncTask<String,Void,Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;
            try {
                mPlayer.setDataSource(strings[0]);
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        initialStage = true ;
                        playPause = false;
                        playButton.setImageResource(R.drawable.ic_play_bis_48);
                        mPlayer.stop();
                        mPlayer.release();
                    }
                });
                mPlayer.prepare();
                prepared=true;
            }  catch (IOException e) {
                e.printStackTrace();
                prepared = false ;
            }
            return  prepared;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(pDialog.isShowing())
                pDialog.cancel();
            mPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please wait ...");
            pDialog.show();
        }
    }

}

