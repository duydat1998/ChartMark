package assignment.prm.chartmarkapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        moveToHomePage();
    }

    private void moveToHomePage(){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
