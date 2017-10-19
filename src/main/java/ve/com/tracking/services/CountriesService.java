package ve.com.tracking.services;
import java.util.List;
import ve.com.tracking.model.Countries;

public interface CountriesService {

	public abstract long countAllCountrieses();


	public abstract void deleteCountries(Countries countries);


	public abstract Countries findCountries(String id);


	public abstract List<Countries> findAllCountrieses();


	public abstract List<Countries> findCountriesEntries(int firstResult, int maxResults);


	public abstract void saveCountries(Countries countries);


	public abstract Countries updateCountries(Countries countries);

}
