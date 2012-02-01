package no.bekk.open.androidkurs.hellotranslate;

import no.bekk.open.androidkurs.hellotranslate.service.AsyncTaskDelegate;
import no.bekk.open.androidkurs.hellotranslate.service.FetchTranslateAsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HelloTranslateActivity extends Activity implements
		AsyncTaskDelegate<String> {

	private TableLayout tableLayout;
	private ScrollView translateScrollview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tableLayout = (TableLayout) findViewById(R.id.translateTableLayout);
		translateScrollview = (ScrollView) findViewById(R.id.translateScrollview);
		Button translateButton = (Button) findViewById(R.id.translatebutton);

		translateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tableLayout.removeAllViews();
				FetchTranslateAsyncTask task = new FetchTranslateAsyncTask(
						HelloTranslateActivity.this);
				EditText editField = (EditText) findViewById(R.id.translateText);
				task.execute(editField.getText().toString());
			}
		});
	}

	/*
	 * AsyncTaskDelegate methods used by the AsyncTask for fetching translations 
	 */

	@Override
	public void publishItem(String string) {
		TextView textView = new TextView(this);
		TableRow row = new TableRow(this);
		row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		textView.setText(string);
		row.addView(textView);
		tableLayout.addView(row);
		translateScrollview.scrollTo(0, translateScrollview.getHeight());

	}

	@Override
	public void didFailWithError(String errorMessage) {
		Toast.makeText(this, "It failed because of, " + errorMessage,
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void didFinishProsess(String result) {
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
	}
}