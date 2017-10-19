package ve.com.tracking.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.tracking.model.Countries;
import ve.com.tracking.repository.CountriesRepository;

@Service
@Transactional
public class CountriesServiceImpl implements CountriesService {

	@Autowired
    CountriesRepository countriesRepository;

	public long countAllCountrieses() {
        return countriesRepository.count();
    }

	public void deleteCountries(Countries countries) {
        countriesRepository.delete(countries);
    }

	public Countries findCountries(String id) {
        return countriesRepository.findOne(id);
    }

	public List<Countries> findAllCountrieses() {
        return countriesRepository.findAll();
    }

	public List<Countries> findCountriesEntries(int firstResult, int maxResults) {
        return countriesRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveCountries(Countries countries) {
        countriesRepository.save(countries);
    }

	public Countries updateCountries(Countries countries) {
        return countriesRepository.save(countries);
    }
}
