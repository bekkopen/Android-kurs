package no.bekk.open.androidkurs.hellotranslate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelloTranslateActivity extends Activity {
    
    
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button translateButton = (Button)findViewById(R.id.translatebutton);
        
        translateButton.setOnClickListener(
        		new OnClickListener() {

					@Override
					public void onClick(View v) {
						TextView translatdText = (TextView)findViewById(R.id.translatedText);
						translatdText.setText("Hello again");
					}
				}
        );
	}
}