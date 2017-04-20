package in.nisb.nisbapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ExtraFunctions.setSBColor(getWindow(), Color.parseColor("#95a5a6"));


        Button btnsubmit = (Button) findViewById(R.id.contact_submit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub = ((EditText) findViewById(R.id.contact_subject)).getText().toString();
                String body = ((EditText) findViewById(R.id.contact_body)).getText().toString();

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:nisb.ieee@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, "nisb.ieee@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, sub);
                intent.putExtra(Intent.EXTRA_TEXT,body);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent,1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            Toast.makeText(getApplicationContext(),"We will get back to you! ",Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.contact_subject)).setText("");
            ((EditText) findViewById(R.id.contact_body)).setText("");
        }
    }
}
