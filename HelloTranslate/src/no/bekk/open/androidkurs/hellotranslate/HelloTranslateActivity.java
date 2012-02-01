package no.bekk.open.androidkurs.hellotranslate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class HelloTranslateActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button translateButton = (Button) findViewById(R.id.translatebutton);
		translateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText editField = (EditText) findViewById(R.id.translateText);
				String translateText = editField.getText().toString();
				
				Intent intent = new Intent(HelloTranslateActivity.this, TranslatedWordsListActivity.class);
				intent.putExtra(TranslatedWordsListActivity.TRANSLATE_TEXT_PARAM, translateText);
				startActivity(intent);
			}
		});
	}


}