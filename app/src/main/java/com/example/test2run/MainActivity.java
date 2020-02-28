package com.example.test2run;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView play_btn,like,shuffle,repeat,wallpaper,skip_next;
    MediaPlayer mediaPlayer;
    TextView totalduration,songName,artistName,startduration,test;
    SeekBar seekbar;
    int played=0;
    int like_num=0;
    int shuffle_num=0;
    int repeat_num=0;
    int num;
    int timer=0;
    int i;
    String[] song={"See You Again",
            "Places Like That",
            "Frequency 75",
            "Feel The Love",
            "Mi Gente",
            "Faded",
            "Attention",
            "On My Way",
            "There's Nothing Holdin' Me Back",
            "We Don't Talk Anymore"};
    String[] artist={"Halifa Kiz, " +
            "Davies",
            "Ascence",
            "DJ Snake",
            "KIDS SEE GHOSTS, Pusha T",
            "J Blavin, Willy William",
            "Alan Walker",
            "Charlie Puth",
            "Alan Walker, Sabrina Carpenter & Farruko",
            "Shawn Mendes",
            "Charlie Puth(feat Selena Gomez)"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    //    Log.i("Song:","Name");

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_main);

        skip_next=findViewById(R.id.skip_next);
        skip_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextTrack();
            }
        });

        play_btn=findViewById(R.id.play_btn);
        like=findViewById(R.id.like);
        startduration=findViewById(R.id.startduration);
        totalduration=findViewById(R.id.totalduration);
        shuffle=findViewById(R.id.shuffle);
        repeat=findViewById(R.id.repeat);
        seekbar=findViewById(R.id.seekbar);
        songName=findViewById(R.id.songName);
        artistName=findViewById(R.id.artistName);
        wallpaper=findViewById(R.id.wallpaper);
        i=0;
        num=0;

        test=findViewById(R.id.test);

    }
    public void play(View view){
        if(played==0) {
            play_btn.setImageResource(R.drawable.pause_btn);
            played=1;
            playSong();
        }
        else if(played==1){
            play_btn.setImageResource(R.drawable.play_btn);
            played=2;
            mediaPlayer.pause();
        }
        else{
            played=1;
            play_btn.setImageResource(R.drawable.pause_btn);
            mediaPlayer.start();
        }

    }
    public void like(View view){
        if(like_num==0)
        {
            like.setImageResource(R.drawable.like);
            like_num=1;
            Snackbar snackbar=Snackbar.make(like,"Added to Liked Songs.",Snackbar.LENGTH_SHORT).setAction("", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.BLACK);
            sbView.setBackgroundColor(Color.WHITE);
            snackbar.show();
        }
        else
        {
            like.setImageResource(R.drawable.liked);
            like_num=0;
            Snackbar snackbar=Snackbar.make(like,"Removed from Liked Songs.",Snackbar.LENGTH_SHORT).setAction("", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.BLACK);
            sbView.setBackgroundColor(Color.WHITE);
            snackbar.show();
        }
    }
    public void shuffle(View view){
        if(shuffle_num==0) {
            shuffle.setImageResource(R.drawable.shuffled);
            shuffle_num=1;
        }
        else{
            shuffle.setImageResource(R.drawable.shuffle);
            shuffle_num=0;
        }
    }
    public void repeat(View view){
        if(repeat_num==0) {
            repeat.setImageResource(R.drawable.repeated);
            repeat_num=1;
        }
        else{
            repeat.setImageResource(R.drawable.repeat);
            repeat_num=0;
        }
    }

    public void playSong()
    {
            String currentSong="demo"+i;
            mediaPlayer=MediaPlayer.create(this,getResources().getIdentifier(currentSong,"raw",getPackageName()));
            mediaPlayer.start();
            wallpaper.animate().alpha(0).setDuration(0);
            final int secondstotal=(mediaPlayer.getDuration()/1000)%60;
            final int minutestotal=(mediaPlayer.getDuration()/1000)/60;
            if(secondstotal<10){
            totalduration.setText(minutestotal+":0"+secondstotal);}
            else {totalduration.setText(minutestotal+":"+secondstotal);}
            seekbar.setMax(mediaPlayer.getDuration());
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                     timer=Integer.parseInt(Integer.toString(progress));
                     seekbar.setProgress(progress);
                     int minutes=(timer/1000)/60;
                     int seconds=(timer/1000)%60;
                     if(seconds<10) {
                         startduration.setText(minutes + ":0" + seconds);
                     }else{
                         startduration.setText(minutes + ":" + seconds);
                     }
                    if(repeat_num==1 && (secondstotal-1)==(seconds) && minutestotal==minutes)
                    {
                        mediaPlayer.stop();
                        playSong();
                    }
                    if(shuffle_num==1 && (secondstotal-1)==(seconds) && (minutestotal-3)==minutes)
                    {
                        i=((int)Math.random()*9+1);
                        Log.i("Song Name:",song[i]);
                        mediaPlayer.stop();
                        playSong();
                    }
                    if(seconds==0)
                    {
                        wallpaper.animate().alpha(1).setDuration(1000);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.pause();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.start();
                }
            });
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    seekbar.setProgress(mediaPlayer.getCurrentPosition());
                }
            },0,1000);
            songName.setText(song[i]);
            artistName.setText(artist[i]);
            switch(i){
                 case 0: wallpaper.setImageResource(R.drawable.wallpaper);
                 break;
                 case 1: wallpaper.setImageResource(R.drawable.song1);
                 break;
                 case 2: wallpaper.setImageResource(R.drawable.song2);
                 break;
                 case 3: wallpaper.setImageResource(R.drawable.song3);
                 break;
                 case 4: wallpaper.setImageResource(R.drawable.song4);
                 break;
                 case 5: wallpaper.setImageResource(R.drawable.song5);
                 break;
                 case 6: wallpaper.setImageResource(R.drawable.song6);
                 break;
                 case 7: wallpaper.setImageResource(R.drawable.song7);
                 break;
                 case 8: wallpaper.setImageResource(R.drawable.song8);
                 break;
                 case 9: wallpaper.setImageResource(R.drawable.song9);
                 break;
            }

    }

    public void nextTrack(){

            if (i == 9) {
                i = 0;
                mediaPlayer.stop();
                playSong();
            } else {
                i = i + 1;
                mediaPlayer.stop();
                play_btn.setImageResource(R.drawable.pause_btn);
                played = 1;
                playSong();
            }
    }
    public void prevTrack(View view){
        if(i==0)
        {
            i=9;
            mediaPlayer.stop();
            playSong();
        }else{
            i=i-1;
            mediaPlayer.stop();
            play_btn.setImageResource(R.drawable.pause_btn);
            played=1;
            playSong();
        }
    }
}
/* Song List
song0: See You Again
song1: Places Like That
song2: Frequency 75
song3: Feel The Love
song4: Mi Gente
song5: Faded
song6: Attention
song7: On My Way
song8; There's Nothing Holdin' Me Back
song9: We Don't Talk Anymore
*/