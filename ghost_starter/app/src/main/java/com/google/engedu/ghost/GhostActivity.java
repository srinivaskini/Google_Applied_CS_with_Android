/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    TextView GhostView,GameSatus;
    private ArrayList<String> arr;
    String str="";
    char c;
    String computeString="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        arr=new ArrayList<>();
        GhostView=(TextView)findViewById(R.id.ghostText);
        GameSatus=(TextView)findViewById(R.id.gameStatus);

        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary =new SimpleDictionary(inputStream);

            }

        catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

                onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        userTurn = true;
        label.setText(USER_TURN);
        str = GhostView.getText().toString();
        if(dictionary.isWord(str.toLowerCase()))
            GameSatus.setText("YOU win!!its a complete word");
        else
        {
            computeString=dictionary.getAnyWordStartingWith(str);
            if(computeString!=null)
            {
                c=computeString.charAt(str.length());
                str+=c;
                GhostView.setText(str.toLowerCase());
                GameSatus.setText(USER_TURN);
            }
            else
            {
                GameSatus.setText("Dont bluff me!!,you lose");
                str="";
                computeString="";
            }
        }

    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        char enteredCharater =(char)event.getUnicodeChar();//retreiveing single character
        if(Character.isLetter(enteredCharater))
        {
            str=GhostView.getText().toString();
            str+=enteredCharater;
            GhostView.setText(str.toLowerCase());
            if(dictionary.isWord(str.toLowerCase()))
            {
                GameSatus.setText("YOU lose!!its a complete word");
                return true;
            }

            computerTurn();

            return true;
        }


        return super.onKeyUp(keyCode, event);

    }


    public void onchallenge(View view) {



        if(dictionary.isWord(str))
        {
            GameSatus.setText("YOU win!!its a complete word");
        }
        else
        {
            computeString=dictionary.getAnyWordStartingWith(str);
            if(computeString!=null)
            {
                GhostView.setText(computeString);
                GameSatus.setText("YOU lose!! a word can be formed");
            }

        }
    }
}
