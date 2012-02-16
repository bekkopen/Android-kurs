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
        
        currentAirportName = "";/* OPG3 */
        currentAirportIata = "";/* OPG3 */
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        /* OPG3 set name */
        addActionBarTabs(actionBar);
        
    }
    
    private void addActionBarTabs(ActionBar actionBar) {
    	Tab tab = actionBar.newTab()
        		.setText("Arrivaal")
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