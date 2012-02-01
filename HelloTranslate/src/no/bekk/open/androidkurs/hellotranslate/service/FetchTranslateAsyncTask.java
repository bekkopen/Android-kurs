package no.bekk.open.androidkurs.hellotranslate.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class FetchTranslateAsyncTask extends AsyncTask<String, String, String> {
	private final static String MICROSOFT_API_KEY = "CE992C875E291DA55801C3B7E3FAF1125A7829B8";
	AsyncTaskDelegate<String> delegate;
	private String[] languages = {"en","sv", "ar", "bg", "ca", "cs", "da", "nl", 
			"et", "fi", "fr", "de", "el", "ht", "he", "hi", "hu", "id", "it",
			"ja", "ko", "lv", "lt", "pl", "pt", "ro", "ru", "sk", "sl", "es",
			 "th", "tr", "uk", "vi" };

	
	public FetchTranslateAsyncTask(AsyncTaskDelegate<String> delegate) {
		this.delegate = delegate;
	}

	@Override
	protected String doInBackground(String... norwegianWord) {
		Arrays.sort(languages);
		try {
			for (String language : languages) {
				String translatedText = getTranslationForLanguage(norwegianWord[0], language);
				publishProgress(language+"  "+translatedText);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			cancel(true);
		}

		return "Its all done now";
	}

	/**
	 * Translates a Norwegian word to a given language
	 * 
	 * @param norwegianWord
	 * @param language
	 * @return
	 */
	private String getTranslationForLanguage(String norwegianWord,
			String language) {
		String url = String
				.format("http://api.microsofttranslator.com/v2/Http.svc/Translate?appId=%s&text=%s&from=no&to=%s",
						MICROSOFT_API_KEY, norwegianWord, language);
		Log.w("", "Fetching some from url " + url);
		BufferedReader in = null;
		String responseText = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Error translating the text "
						+ norwegianWord + " to language " + language);
			}
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			String xml = in.readLine();
			responseText = (xml.split("<[a-zA-Z0-9 =\":/.]*>")[1]);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseText;
	}

	@Override
	protected void onProgressUpdate(String... items) {
		Log.w(this.getClass().getCanonicalName(), items[0]);
		delegate.publishItem(items[0]);
	}

	@Override
	protected void onCancelled() {
		delegate.didFailWithError("Feil ved oversetting");
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(String result) {
		delegate.didFinishProsess(result);
		super.onPostExecute(result);
	}

}
