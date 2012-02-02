package no.bekk.open.flightdata.android.fragment;

import no.bekk.open.flightdata.android.FlightDataActivity;
import no.bekk.open.flightdata.android.R;
import no.bekk.open.flightdata.android.domain.Flight;
import no.bekk.open.flightdata.android.service.AsyncTaskDelegate;
import no.bekk.open.flightdata.android.service.GetFlightsDataTask;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FlightListFragment extends Fragment implements AsyncTaskDelegate<Flight>{

	private TableLayout flightTable;
	private GetFlightsDataTask getFlights;
	private String type;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle bundle) {
		View view = inflater.inflate(R.layout.arrival_fragment, container, false);
		flightTable = (TableLayout)view.findViewById(R.id.arrival_table);
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		this.type = getArguments().getString("type");
		getFlights = new GetFlightsDataTask(this);
		String iata = ((FlightDataActivity)getActivity()).getCurrentAirportIata();
		getFlights.execute(iata, type);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (getFlights != null)
			getFlights.cancel(true);
	}

	@Override
	public void publishItem(Flight flight) {
		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TableRow tableRow = (TableRow)inflater.inflate(R.layout.flight_row, null);
		TextView city = (TextView)tableRow.findViewById(R.id.flight_row_city);
		TextView dateTime = (TextView)tableRow.findViewById(R.id.flight_row_date_time);
		city.setText(flight.getCity());
		dateTime.setText(flight.getDateTime());
		flightTable.addView(tableRow);
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
