package com.example.acer.exoplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    long cp;
    boolean pwr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleExoPlayerView=findViewById(R.id.simpleexoplayerview);
        if(savedInstanceState!=null){
            cp=savedInstanceState.getLong("currentpos");
            pwr=savedInstanceState.getBoolean("PlayWhenReady");
           // simpleExoPlayer.seekTo(cp);
           // simpleExoPlayer.setPlayWhenReady(pwr);
        }
        else
    startplayer();
    }
    public void startplayer(){
        simpleExoPlayer=ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),new DefaultTrackSelector(),new DefaultLoadControl());
        Uri videouri=Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        String useragent=Util.getUserAgent(this,"ExoPlayer");
        MediaSource mediaSource= new ExtractorMediaSource(videouri,new DefaultDataSourceFactory(this,useragent),new DefaultExtractorsFactory(),null,null);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
       simpleExoPlayer.seekTo(cp);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
    }
    public void stopplayer(){
        if(simpleExoPlayer!=null){
            cp=simpleExoPlayer.getContentPosition();
            simpleExoPlayer.release();
            simpleExoPlayer.stop();
            simpleExoPlayer=null;

            }
    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(Util.SDK_INT<=23||simpleExoPlayer==null){startplayer();}
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Util.SDK_INT<=23)
        stopplayer();
    }

   /* @Override
   protected void onResume() {
        super.onResume();

    }*/

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT>23)
        stopplayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopplayer();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(simpleExoPlayer!=null){
        outState.putLong("currentpos",simpleExoPlayer.getCurrentPosition());
        outState.putBoolean("PlayWhenReady",simpleExoPlayer.getPlayWhenReady());
    }}
}
