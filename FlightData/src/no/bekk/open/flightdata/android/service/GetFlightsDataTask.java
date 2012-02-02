package no.bekk.open.flightdata.android.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import no.bekk.open.flightdata.android.domain.Flight;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

public class GetFlightsDataTask extends AsyncTask<String, Flight, Void>{
	
	private AsyncTaskDelegate<Flight> delegate;
	
	public GetFlightsDataTask(AsyncTaskDelegate<Flight> delegate) {
		this.delegate = delegate;
	}

	@Override
	protected Void doInBackground(String... params) {
		/*String JSONData = "[ {\"city\" : \"Oslo\", \"time\" : \"20:45\" }" +
				", { \"city\" : \"Bergen\", \"time\" : \"20:45\" }" +
				", { \"city\" : \"Bod¿\", \"time\" : \"20:45\" } ]";*/
		
		String url = String.format("http://mashapp.heroku.com/%S/%S", params[0], params[1]);
		Log.w("URL", url);
		InputStreamReader urlReader = null;
		JsonReader reader = null;
		try {
			urlReader = getInputStreamReaderFromUrl(url);
			reader = new JsonReader(urlReader);
			readFlightArray(reader);
		} catch (Exception e) {
			e.printStackTrace();
			cancel(true);
		} finally {
			try { reader.close(); } catch (IOException e) { e.printStackTrace(); }
			try { urlReader.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return null;
	}
	
	private InputStreamReader getInputStreamReaderFromUrl(String url) throws IOException, URISyntaxException {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		request.setURI(new URI(url));
		HttpResponse response = client.execute(request);
		return new InputStreamReader(response.getEntity().getContent());
	}
	
	private void readFlightArray(JsonReader reader) throws IOException{
		reader.beginArray();
		while (reader.hasNext()) {
			Flight flight = readFlightObject(reader);
			publishProgress(flight);
		}
		reader.endArray();
	}
	
	private Flight readFlightObject(JsonReader reader) throws IOException {
		reader.beginObject();
			reader.nextName();
			Flight flight = new Flight();
			flight.setCity(reader.nextString());
			reader.nextName();
			flight.setDateTime(reader.nextString());
		reader.endObject();
		return flight;
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		Log.w("GetFlightsDataTask", "Stopped AsyncTask");
		delegate.didFailWithError("Failed when retrieving flights");
	}

	@Override
	protected void onProgressUpdate(Flight... values) {
		delegate.publishItem(values[0]);
	}
}
