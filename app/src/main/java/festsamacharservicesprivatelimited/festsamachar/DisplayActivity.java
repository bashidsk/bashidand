package festsamacharservicesprivatelimited.festsamachar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by eruvaka on 12-07-2016.
 */
public class DisplayActivity extends AppCompatActivity {
    android.support.v7.app.ActionBar actionBar;
    Timer splashTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);try{
            actionBar = getSupportActionBar();
            actionBar.hide();
            splashTimer =  new Timer();
            splashTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    updateValue();
                }
            }, 2000, 90000);// 1 second for initial start value and 6 seconds to display splash screen

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void updateValue() {
        try{
            Intent intent =  new Intent(DisplayActivity.this, LoginActivity.class);
            startActivity(intent);
            splashTimer.cancel();
            finish();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
