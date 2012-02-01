package no.bekk.open.androidkurs.hellotranslate;

import no.bekk.open.androidkurs.hellotranslate.service.AsyncTaskDelegate;
import no.bekk.open.androidkurs.hellotranslate.service.FetchTranslateAsyncTask;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class TranslatedWordsListActivity extends ListActivity implements AsyncTaskDelegate<String> {

	private FetchTranslateAsyncTask fetchTranslateAsyncTask;
	private ArrayAdapter<String> translatedWordsListAdapter;
	
	public static String TRANSLATE_TEXT_PARAM = "translateText";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.translatedwordslistactivity);

		String theText = getIntent().getExtras().getString(TRANSLATE_TEXT_PARAM);
		fetchTranslateAsyncTask = new FetchTranslateAsyncTask(TranslatedWordsListActivity.this);
		fetchTranslateAsyncTask.execute(theText);
		
		translatedWordsListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1);
		setListAdapter(translatedWordsListAdapter);
	}
	
	
	/*
	 * AsyncTaskDelegate methods used by the AsyncTask for fetching translations 
	 */

	@Override
	public void publishItem(String string) {
		translatedWordsListAdapter.add(string);
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
