package com.example.musicplay;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.musicplay.MainActivity.musicFiles;

public class Player extends AppCompatActivity {

    TextView songName, artiste, durationP, duratiopnT;
    ImageView cover, next, prev, back, shuffle, repeat;
    FloatingActionButton pausePlay;
    SeekBar seekBar;
    int position = -1;
    static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
        getIntentMethod();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer !=null && fromUser){
                    mediaPlayer.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Player.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    int currentPosition = mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(currentPosition);
                    durationP.setText(formattedTime(currentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private String formattedTime(int currentPosition) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(currentPosition % 60);
        String minutes = String.valueOf(currentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1){
            return totalNew;
        }
        else {
            return totalOut;
        }
    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position", -1);
        listSongs = musicFiles;
        if(listSongs != null){
            pausePlay.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listSongs.get(position).getPath());
        }
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else{
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000);
    }

    private void initView() {
        songName = findViewById(R.id.songName);
        artiste = findViewById(R.id.artiste);
        durationP = findViewById(R.id.durationPlayed);
        duratiopnT = findViewById(R.id.durationTotal);
        cover = findViewById(R.id.cover);
        next = findViewById(R.id.skip_next);
        prev = findViewById(R.id.skip_previous);
        back = findViewById(R.id.backBtn);
        shuffle = findViewById(R.id.shuffle);
        repeat = findViewById(R.id.repeat);
        pausePlay = findViewById(R.id.playPause);
        seekBar = findViewById(R.id.seekBar);
    }
}