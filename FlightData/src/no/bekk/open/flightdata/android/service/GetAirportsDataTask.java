package no.bekk.open.flightdata.android.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import no.bekk.open.flightdata.android.App;
import no.bekk.open.flightdata.android.domain.Airport;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

public class GetAirportsDataTask extends AsyncTask<Void, Airport, Void>{
	
	private AsyncTaskDelegate<Airport> delegate;
	
	public GetAirportsDataTask(AsyncTaskDelegate<Airport> delegate) {
		this.delegate = delegate;
	}

	@Override
	protected Void doInBackground(Void... params) {
		AssetManager assets = App.getContext().getAssets();
		InputStreamReader airportsStreamReader = null;
		try {
			InputStream airportsStream = assets.open("airports");
			 airportsStreamReader = new InputStreamReader(airportsStream, "UTF-8");
			JsonReader reader = new JsonReader(airportsStreamReader);
			readAirportArray(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				airportsStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private void readAirportArray(JsonReader reader) throws IOException{
		reader.beginArray();
		while (reader.hasNext()) {
			Airport airport = readAirportObject(reader);
			publishProgress(airport);
		}
		reader.endArray();
	}
	
	private Airport readAirportObject(JsonReader reader) throws IOException {
		reader.beginObject();
			reader.nextName();
			Airport airport = new Airport();
			airport.setAirportIata(reader.nextString());
			reader.nextName();
			airport.setAirportName(reader.nextString());
		reader.endObject();
		return airport;
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		Log.w("GetAirportsDataTask", "Stopped AsyncTask");
	}

	@Override
	protected void onProgressUpdate(Airport... values) {
		delegate.publishItem(values[0]);
	}
}
