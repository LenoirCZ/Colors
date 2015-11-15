package com.colors;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RandomColorService extends Service {

    private final String nameClass = this.getClass().toString();

    private Timer t;
    private final String TROCAR_COR = "com.colors.COM";
    private Random rand;

    public RandomColorService() {
        t = new Timer();
        rand = new Random();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(nameClass, "onStartCommand");

        t.scheduleAtFixedRate(new TimerTask() {

            @Override
             public void run() {
                Log.i(nameClass, "Enviando Cor");
                enviarCorLayout();
             }
          }, 0, 500);

        return 0;
    }

    public void enviarCorLayout(){

        int a = rand.nextInt(256);
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);

        Intent mIntentResposta = new Intent();
        mIntentResposta.setAction(TROCAR_COR);

        mIntentResposta.putExtra("a", a);
        mIntentResposta.putExtra("r", r);
        mIntentResposta.putExtra("g", g);
        mIntentResposta.putExtra("b", b);
        sendBroadcast(mIntentResposta);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        t.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
