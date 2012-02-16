package no.bekk.open.flightdata.android.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import no.bekk.open.flightdata.android.domain.Flight;
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
		String url = String.format("http://mashapp.heroku.com/%S/%S", params[0], params[1]);
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
		URL serviceUrl = new URL(url);
		URLConnection yc = serviceUrl.openConnection();
		return new InputStreamReader(yc.getInputStream());
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
