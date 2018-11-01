package com.muvbolivia.radiodesafio;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.muvbolivia.radiodesafio.player.PlaybackStatus;
import com.muvbolivia.radiodesafio.player.RadioManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    RadioManager radioManager;
    SeekBar sk_volumen;
    AudioManager audioManager;
    Button pause, play, stop;
    ImageView imgFacebook, imgTwitter, imgYoutube, imgTunein, imgEmail, imgWeb;

    String streamURL = "http://icecasthd.net:49047/live";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Radio Desafío");
        //toolbar.setTitleTextColor(Color.rgb(0,86,189));
        setSupportActionBar(toolbar);
        imgFacebook = findViewById(R.id.imgFacebook);
        imgTwitter = findViewById(R.id.imgTwitter);
        imgYoutube = findViewById(R.id.imgYoutube);
        imgEmail = findViewById(R.id.imgEmail);
        imgTunein = findViewById(R.id.imgTunein);
        imgWeb = findViewById(R.id.imgWeb);
        radioManager = RadioManager.with(MainActivity.this);

        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);

        pause.setEnabled(false);
        stop.setEnabled(false);

        pause.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.centro));
        stop.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.centro));

        Volumen();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioManager.playOrPause(streamURL);
                play.setEnabled(false);
                pause.setEnabled(true);
                stop.setEnabled(true);
                pause.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.colorPrimary));
                stop.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.colorPrimary));
                play.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.centro));
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioManager.playOrPause(streamURL);
                play.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(true);
                pause.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.centro));
                stop.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.colorPrimary));
                play.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.colorPrimary));
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioManager.stop();
                play.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(false);
                pause.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.centro));
                stop.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.centro));
                play.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.colorPrimary));
            }
        });

        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/MINISTERIOINTERNACIONALDESAFIOBOLIVIA"));
                startActivity(viewIntent);
            }
        });

        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.twitter.com/Min_Desafio"));
                startActivity(viewIntent);
            }
        });

        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/channel/UCbG-QVK69yn2Z2ZY33kIBuQ"));
                startActivity(viewIntent);
            }
        });

        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","ofministerial@mi-desafio.org", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        imgTunein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://tun.in/sfzJB"));
                startActivity(viewIntent);
            }
        });

        imgWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.mi-desafio.org"));
                startActivity(viewIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.contacto) {
            Intent intent = new Intent(MainActivity.this, SocialActivity.class);
            startActivity(intent);
        }*/
        if (id == R.id.acerca_de) {
            Intent intent = new Intent(MainActivity.this, AcercaDeActivity.class);
            startActivity(intent);
        }
        if (id == R.id.compartir) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Estoy escuchando Radio Desafío en vivo desde la app para Android";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Radio Desafío 103.0 FM");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Compartir en"));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {

        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        radioManager.unbind();

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        radioManager.bind();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        //finish();
    }

    @Subscribe
    public void onEvent(String status){

        switch (status){

            case PlaybackStatus.LOADING:

                // loading

                break;

            case PlaybackStatus.ERROR:

                Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();

                break;

        }

    }

    private void Volumen() {

        try {
            sk_volumen = findViewById(R.id.volumen);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            sk_volumen.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            sk_volumen.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

            sk_volumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override            public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override            public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
