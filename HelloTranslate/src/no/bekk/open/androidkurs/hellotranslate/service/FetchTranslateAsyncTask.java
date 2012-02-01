package no.bekk.open.androidkurs.hellotranslate.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;

public class FetchTranslateAsyncTask extends AsyncTask<String, JSONObject, Integer> {

	AsyncTaskDelegate<JSONObject> delegate;

	public FetchTranslateAsyncTask(AsyncTaskDelegate<JSONObject> delegate) {
		this.delegate = delegate;
	}

	@Override
	protected Integer doInBackground(String... urls) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(urls[0]); 
        HttpResponse response;
        InputStream instream = null;
        int httpStatusCode = -1;
        try {
            response = httpclient.execute(httpget);
            if((httpStatusCode = response.getStatusLine().getStatusCode()) == 200){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    instream = entity.getContent();
                    JSONObject jsonObject = new JSONObject(convertStreamToString(instream)); 
                    publishProgress(jsonObject);
                }
            }else{
            	
            }
        }catch(Exception ex){
        	//do fail
        }finally{
        	if(instream != null){
        		try {
					instream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
		return httpStatusCode;
	}

	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	@Override
	protected void onProgressUpdate(JSONObject... items) {
		delegate.publishItem(items[0]);
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (result == 200) {
			delegate.didFinishProsess();
		} else {
			delegate.didFailWithError("Something went wrong when fetching data from google");
		}
	}

}
