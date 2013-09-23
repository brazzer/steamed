package com.example.paulsapp;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
    public final static String EXTRA_MESSAGE = "com.example.paulsapp.MESSAGE";

    public final static int ACTION_SEARCH = R.id.action_search;
    
    public final static int ACTION_SETINGS = R.id.action_settings;
    
    private final static int
    CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    // Define a DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment {
		// Global field to contain the error dialog
		private Dialog mDialog;
		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
		    super();
		    mDialog = null;
		}
		// Set the dialog to display
		public void setDialog(Dialog dialog) {
		    mDialog = dialog;
		}
		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		    return mDialog;
		}
	}
	
	/*
	* Handle results returned to the FragmentActivity
	* by Google Play services
	*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {
		    case CONNECTION_FAILURE_RESOLUTION_REQUEST :
		    /*
		     * If the result code is Activity.RESULT_OK, try
		     * to connect again
		     */
		        switch (resultCode) {
		            case Activity.RESULT_OK :
		            /*
		             * Try the request again
		             */
		            break;
		        }
		}
	}
	
	private boolean servicesConnected(ConnectionResult connectionResult) {
		// Check that Google Play services is available
		int resultCode =
		        GooglePlayServicesUtil.
		                isGooglePlayServicesAvailable(this);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
		    // In debug mode, log the status
		    Log.d("Location Updates",
		            "Google Play services is available.");
		    // Continue
		    return true;
		// Google Play services was not available for some reason
		} else {
		    // Get the error code
		    int errorCode = connectionResult.getErrorCode();
		    // Get the error dialog from Google Play services
		    Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
		            errorCode,
		            this,
		            CONNECTION_FAILURE_RESOLUTION_REQUEST);
		
		    // If Google Play services can provide an error dialog
		    if (errorDialog != null) {
		        // Create a new DialogFragment for the error dialog
		        ErrorDialogFragment errorFragment =
		                new ErrorDialogFragment();
		        // Set the dialog in the DialogFragment
		        errorFragment.setDialog(errorDialog);
		        // Show the error dialog in the DialogFragment
		        errorFragment.show(getFragmentManager(),
		                "Location Updates");
		    }
		}
		return false;
	}
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

     // Define a listener that responds to location updates
     LocationListener locationListener = new LocationListener() {
         public void onLocationChanged(Location location) {
           // Called when a new location is found by the network location provider.
           makeUseOfNewLocation(location);
         }

         public void onStatusChanged(String provider, int status, Bundle extras) {}

         public void onProviderEnabled(String provider) {}

         public void onProviderDisabled(String provider) {}
       };

     // Register the listener with the Location Manager to receive location updates
     locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    
    public void makeUseOfNewLocation(Location location) {
        // "Latitude " + 
    	//location.getLatitude() + " " + " Longitude" + location.getLongitude());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
            if( ACTION_SEARCH == item.getItemId()) {
                //openSearch();
                return true;
            }
            else if (ACTION_SETINGS == item.getItemId()) {
                //openSettings();
                return true;
            }
            else
                return super.onOptionsItemSelected(item);
    }
    
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }

}
