package ve.com.tracking.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.model.Countries;
import ve.com.tracking.model.TipoCambio;
import ve.com.tracking.repository.TipoCambioRepository;

@Service
@Transactional
public class TipoCambioServiceImpl implements TipoCambioService {

	@Autowired
	TipoCambioRepository tipoCambioRepository;

	@Autowired
	CountriesService countriesService;

	public long countAllTipoCambios() {
		return tipoCambioRepository.count();
	}

	public void deleteTipoCambio(TipoCambio tipoCambio) {
		tipoCambioRepository.delete(tipoCambio);
	}

	public TipoCambio findTipoCambio(Long id) {
		return tipoCambioRepository.findOne(id);
	}

	public List<TipoCambio> findAllTipoCambios() {
		return tipoCambioRepository.findAll();
	}

	public List<TipoCambio> findTipoCambioEntries(int firstResult,
			int maxResults) {
		return tipoCambioRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	public void saveTipoCambio(TipoCambio tipoCambio) {
		tipoCambioRepository.save(tipoCambio);
	}

	public TipoCambio updateTipoCambio(TipoCambio tipoCambio) {
		return tipoCambioRepository.save(tipoCambio);
	}

	@Override
	public BigDecimal getExchangeRate(BigDecimal dollars, TipoCambio tipoCambio) {
		BigDecimal valorDollar = tipoCambio.getValorDolar();
		return dollars.multiply(valorDollar);
	}

	@Override
	public TipoCambio findTipoCambioByCountry(String codeCountry) {
		Countries country = null;
		if (codeCountry != null && !codeCountry.equals("")) {
			country = countriesService.findCountries(codeCountry);
		}
		TipoCambio tipoCambio = null;
		if (country != null) {
			tipoCambio = tipoCambioRepository.findByCountry(country);
		}
		return tipoCambio;
	}
}
