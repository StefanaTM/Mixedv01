package com.game.mixed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SetNumberCardsActivity extends AppCompatActivity {

    private Button btnNext;
    private TextView nrCards;
    private int count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_number_cards);

        nrCards=findViewById(R.id.nrCards);
        btnNext=findViewById(R.id.nrbtnNext);

        //open player name activity
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlayerName();
            }
        });

    }

    //increment number of cards
    public void increment(View view) {
        if(count>=10)
            count=10;
        else
            count++;
            nrCards.setText(""+count);
        }

    //decrement number of cards
    public void decrement(View view) {
        if(count<=1)
            count=1;
        else
            count--;
        nrCards.setText(""+count);
    }

    //functionality open player name activity
    public void openPlayerName(){
        Intent intent=new Intent(this, PlayerNameActivity.class);
        startActivity(intent);
    }
}