package no.bekk.open.androidkurs.hellotranslate;

import no.bekk.open.androidkurs.hellotranslate.service.AsyncTaskDelegate;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelloTranslateActivity extends Activity implements AsyncTaskDelegate<JSONObject> {
    
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

	@Override
	public void publishItem(JSONObject object) {
		Log.w(this.getClass()+"",object.toString());
	}

	@Override
	public void didFailWithError(String errorMessage) {
		// TODO Auto-generated method stub
	}

	@Override
	public void didFinishProsess() {
		// TODO Auto-generated method stub		
	}
}