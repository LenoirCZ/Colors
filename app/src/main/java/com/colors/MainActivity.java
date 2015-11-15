package com.colors;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final String nameClass = this.getClass().toString();

    private final String TROCAR_COR = "com.colors.COM";
    private RelativeLayout mLayout;
    private Switch mSwitch;
    private Intent mIntentService;

    private int mCor;
    private final String MY_PREFERENCES = "MyPrefs" ;
    private final String COR_SALVA = "cor_salva";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private boolean isRandom;

    private int randomColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (RelativeLayout) findViewById(R.id.layout);
        mSwitch = (Switch) findViewById(R.id.switch_colors);

        mIntentService = new Intent(this, RandomColorService.class);

        IntentFilter intentFilter = new IntentFilter(TROCAR_COR);

        ColorReceiver colorReceiver = new ColorReceiver();

        registerReceiver(colorReceiver, intentFilter);

        mSharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        mEditor = mSharedPreferences.edit();

        setUltimaCorSalva(mSharedPreferences.getInt(COR_SALVA, 0));

        Log.e(nameClass, String.valueOf(mSharedPreferences.getInt(COR_SALVA, 0)));

    }

    public void setBGAzul(View view) {
        setAzul();
        stopService(mIntentService);
        mSwitch.setChecked(false);
    }

    public void setBGVermelho(View view) {
        setVermelho();
        stopService(mIntentService);
        mSwitch.setChecked(false);
    }

    public void setBGAmarelo(View view) {
        setAmarelo();
        stopService(mIntentService);
        mSwitch.setChecked(false);
    }

    public void setBGRandom(View view) {

        isRandom = true;

        if(mSwitch.isChecked()){
            startService(mIntentService);
        } else {
            stopService(mIntentService);
        }
    }

    public void setAzul(){
        mLayout.setBackgroundColor(getResources().getColor(R.color.blue));
        mCor = 0;
        isRandom = false;
    }

    public void setVermelho(){
        mLayout.setBackgroundColor(getResources().getColor(R.color.red));
        mCor = 1;
        isRandom = false;
    }

    public void setAmarelo(){
        mLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
        mCor = 2;
        isRandom = false;
    }

    public void setRandomCor(int a, int r, int g, int b){

        randomColor = Color.argb(a, r, g, b);

        mLayout.setBackgroundColor(randomColor);

//        mCor = randomColor;
    }

    public void setSalvarCor(View view) {

        if(isRandom){
            mEditor.putInt(COR_SALVA, randomColor).commit();
            Toast.makeText(this, "Cor salva com sucesso", Toast.LENGTH_SHORT).show();

        }else{

            switch (mCor){
                case 0:
                    randomColor = Color.rgb(0, 0, 255);
                    break;

                case 1:
                    randomColor = Color.rgb(255, 0, 0);
                    break;

                case 2:
                    randomColor = Color.rgb(255, 255, 0);
                    break;
            }

            mEditor.putInt(COR_SALVA, randomColor).commit();
            Toast.makeText(this, "Cor salva com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUltimaCorSalva(int numCor) {
        mLayout.setBackgroundColor(numCor);
    }

    //============================= RECEIVER =============================//

    class ColorReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e(nameClass, "onReceive");

            int a = intent.getIntExtra("a", -1);
            int r = intent.getIntExtra("r", -1);
            int g = intent.getIntExtra("g", -1);
            int b = intent.getIntExtra("b", -1);

            setRandomCor(a, r, g, b);
        }
    }
}



