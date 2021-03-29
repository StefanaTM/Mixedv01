package com.game.mixed;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InstructionsActivity extends AppCompatActivity {
    TextView instructions, enjoy;
    Typeface chelsea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        TextView instructions=(TextView) findViewById(R.id.textInstructions);
        TextView enjoy=(TextView) findViewById(R.id.enjoy);

        instructions.setText(Html.fromHtml(getString(R.string.instructions)));

        chelsea=Typeface.createFromAsset(getAssets(), "chelsea.ttf");
        instructions.setTypeface(chelsea);
        enjoy.setTypeface(chelsea);
    }
}