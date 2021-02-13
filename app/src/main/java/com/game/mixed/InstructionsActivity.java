package com.game.mixed;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class InstructionsActivity extends AppCompatActivity {
    TextView instructions, enjoy;
    Typeface chelsea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        TextView instructions=(TextView) findViewById(R.id.textInstructions);
        instructions.setText(Html.fromHtml(getString(R.string.instructions)));

        TextView enjoy=(TextView) findViewById(R.id.enjoy);

        chelsea=Typeface.createFromAsset(getAssets(), "chelsea.ttf");
        instructions.setTypeface(chelsea);
        enjoy.setTypeface(chelsea);
    }
}