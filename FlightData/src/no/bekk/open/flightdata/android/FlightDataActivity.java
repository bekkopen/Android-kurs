package no.bekk.open.flightdata.android;

import no.bekk.open.flightdata.android.fragment.FlightListFragment;
import no.bekk.open.flightdata.android.fragment.TabListener;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;

public class FlightDataActivity extends Activity {
    public static final String SELECTED_AIRPORT_IATA = "selected_airport_iata";
	public static final String SELECTED_AIRPORT_NAME = "selected_airport_name";
    private String currentAirportName;
    private String currentAirportIata;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        currentAirportName = getIntent().getExtras().getString(SELECTED_AIRPORT_NAME);
        currentAirportIata = getIntent().getExtras().getString(SELECTED_AIRPORT_IATA);
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        getActionBar().setTitle(currentAirportName);
        getActionBar().setDisplayShowTitleEnabled(true);
        addActionBarTabs(actionBar);
        
    }
    
    private void addActionBarTabs(ActionBar actionBar) {
    	Tab tab = actionBar.newTab()
        		.setText("Arrival")
        		.setTabListener(new TabListener<FlightListFragment>(this, "A", FlightListFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText("Departure")
        		.setTabListener(new TabListener<FlightListFragment>(this, "D", FlightListFragment.class));
        actionBar.addTab(tab);
    }
    
    public String getCurrentAirportIata() {
    	return currentAirportIata;
    }
}