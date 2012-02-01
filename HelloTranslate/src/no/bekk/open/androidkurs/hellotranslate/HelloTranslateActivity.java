package no.bekk.open.androidkurs.hellotranslate;

import no.bekk.open.androidkurs.hellotranslate.service.AsyncTaskDelegate;
import no.bekk.open.androidkurs.hellotranslate.service.FetchTranslateAsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HelloTranslateActivity extends Activity implements AsyncTaskDelegate<String> {
    
	private TableLayout tableLayout;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tableLayout = (TableLayout) findViewById(R.id.translateTableLayout);
        Button translateButton = (Button)findViewById(R.id.translatebutton);
        
        translateButton.setOnClickListener(
        		new OnClickListener() {

					@Override
					public void onClick(View v) {
						FetchTranslateAsyncTask task = new FetchTranslateAsyncTask(HelloTranslateActivity.this);
						EditText editField = (EditText) findViewById(R.id.translateText);
						task.execute(editField.getText().toString());
					}
				}
        );
	}

	@Override
	public void publishItem(String string) {
		TextView textView = new TextView(this);
		TableRow row = new TableRow(this);
		textView.setText(string);
		row.addView(textView);
		tableLayout.addView(row);
	}

	@Override
	public void didFailWithError(String errorMessage) {
		Toast.makeText(this, "It failed because of, "+errorMessage, Toast.LENGTH_LONG).show();
	}

	@Override
	public void didFinishProsess(String result) {
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
	}
}