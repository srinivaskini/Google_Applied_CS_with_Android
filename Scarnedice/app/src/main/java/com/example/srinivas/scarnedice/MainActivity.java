package com.example.srinivas.scarnedice;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int u_total,u_turn,c_total,c_turn;
    Button b1,b2,b3;
    ImageView i;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(this);
        b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(this);
        b3=(Button)findViewById(R.id.button3);
        b3.setOnClickListener(this);
        i=(ImageView)findViewById(R.id.imageView2);
        t=(TextView)findViewById(R.id.textView);

        b2.setEnabled(false);
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        public void computer() {
            c_turn = 0;
            while(true) {

                Random r1 = new Random();
                int ran1 = r1.nextInt(6);
                if (ran1 != 0){
                    c_turn = c_turn + ran1 + 1;


                    t.setText("computer: " + c_total + " This turn score " + c_turn);
                    int s[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
                    i.setImageResource(s[ran1]);
                    if (c_turn > 20) {
                        c_total = c_total + c_turn;
                        t.setText("User score :" + u_total + " Computer score : " + c_total);
                        b1.setEnabled(true);
                        b2.setEnabled(true);
                        return;
                    }



                } else {
                    int s[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
                    i.setImageResource(s[ran1]);
                    t.setText("User score :" + u_total + " Computer score : " + c_total);
                    b1.setEnabled(true);
                    b2.setEnabled(true);
                    return;
                }
                if (c_total > 100) {
                    t.setText("Computer wins");
                    u_turn = 0;
                    u_total = 0;
                    c_total = 0;
                    c_turn = 0;
                    return;
                }

            }
        }

    @Override
    public void onClick(View v) {

        if (v == b1) {
            Random r = new Random();
            int ran = r.nextInt(6);
            if (ran != 0) {
                b2.setEnabled(true);
                u_turn = u_turn+ran + 1;

                t.setText("user: " + u_total + " This turn score " + u_turn+" Computer turn : "+c_total);
                int s[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
                i.setImageResource(s[ran]);
            } else {

                int s[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
                i.setImageResource(R.drawable.dice1);
                t.setText("user: " + u_total);
                b1.setEnabled(false);
                b2.setEnabled(false);
                u_turn=0;
                computer();


            }

        }
        if (v == b2) {
            u_total=u_total+u_turn;
            if(u_total>100)
            {
                t.setText("User wins");
                u_turn=0;
                u_total=0;
                c_total=0;
                c_turn=0;
                return;
            }
            t.setText("user : " + u_total + " Computer : "+c_total);
            b1.setEnabled(false);
            b2.setEnabled(false);
            computer();
            u_turn=0;
            b2.setEnabled(false);
        }

        if (v == b3) {

            t.setText("Start!!!");
            b1.setEnabled(true);
            b2.setEnabled(true);
            u_turn=0;
            u_total=0;
            c_total=0;
            c_turn=0;
            b2.setEnabled(false);
        }
    }





}
