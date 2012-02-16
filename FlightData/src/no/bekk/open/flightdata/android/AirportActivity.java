package no.bekk.open.flightdata.android;

import java.util.ArrayList;

import no.bekk.open.flightdata.android.domain.Airport;
import no.bekk.open.flightdata.android.service.AsyncTaskDelegate;
import no.bekk.open.flightdata.android.service.GetAirportsDataTask;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AirportActivity extends ListActivity implements AsyncTaskDelegate<Airport>{
	
	private ArrayAdapter<String> airportListAdapter;
	ArrayList<Airport> airportList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.airport_activity);
		airportList = new ArrayList<Airport>();
		
		GetAirportsDataTask getAirports = new GetAirportsDataTask(this);
		getAirports.execute();
		
		airportListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1);
		setListAdapter(airportListAdapter);
	}
	
	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) {
		super.onListItemClick(list, view, position, id);
		Intent airportIntent = new Intent(this, FlightDataActivity.class);
		airportIntent.putExtra(FlightDataActivity.SELECTED_AIRPORT_NAME, airportList.get(position).getAirportName());
		airportIntent.putExtra(FlightDataActivity.SELECTED_AIRPORT_IATA, airportList.get(position).getAirportIata());
		/* OPG3 */
	}

	@Override
	public void publishItem(Airport airport) {
		airportList.add(airport);
		airportListAdapter.add(airport.getAirportName());
	}

	@Override
	public void didFailWithError(String errorMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void didFinishProsess(String message) {
		// TODO Auto-generated method stub
		
	}
}
