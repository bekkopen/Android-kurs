package no.bekk.open.flightdata.android.service;

public interface AsyncTaskDelegate<T> {

	public void publishItem(T object);
	public void didFailWithError(String errorMessage);
	public void didFinishProsess(String message);
}
