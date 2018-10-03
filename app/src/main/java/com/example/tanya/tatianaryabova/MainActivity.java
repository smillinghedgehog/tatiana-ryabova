package com.example.tanya.tatianaryabova;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    String getMessage(){
        EditText message = findViewById(R.id.message_text);
        final String messageText = message.getText().toString();
        return messageText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView sendMessage = findViewById(R.id.message_button);
        final String subject = getString(R.string.subject);
        final String sendto = getString(R.string.sendto);
        ImageView telegram = findViewById(R.id.telegram);
        ImageView instagram = findViewById(R.id.instagram);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = getMessage();
                Intent sendEmail = new Intent(Intent.ACTION_SENDTO).setData(Uri.parse(String.format("mailto:%s", sendto)))
                        .putExtra(Intent.EXTRA_SUBJECT, subject)
                        .putExtra(Intent.EXTRA_TEXT, messageText);
                startActivity(sendEmail);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openInst = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/tanya.hedgehog/"));
                startActivity(openInst);
            }
        });

        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openTg = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/kekskekskekskeks"));
                startActivity(openTg);
            }
        });

        LinearLayout mainLayout = findViewById(R.id.main_layout);
        TextView developer = new TextView(this);
        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        developer.setLayoutParams(params);
        developer.setText(getString(R.string.developer));
        developer.setPadding(0, 16, 0, 16);
        mainLayout.addView(developer);
    }
}
