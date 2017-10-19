package ve.com.tracking.services;
import java.math.BigDecimal;
import java.util.List;

import ve.com.tracking.model.TipoCambio;

public interface TipoCambioService {

	public abstract long countAllTipoCambios();


	public abstract void deleteTipoCambio(TipoCambio tipoCambio);


	public abstract TipoCambio findTipoCambio(Long id);


	public abstract List<TipoCambio> findAllTipoCambios();


	public abstract List<TipoCambio> findTipoCambioEntries(int firstResult, int maxResults);


	public abstract void saveTipoCambio(TipoCambio tipoCambio);


	public abstract TipoCambio updateTipoCambio(TipoCambio tipoCambio);
	
	public BigDecimal getExchangeRate(BigDecimal dollars, TipoCambio tipoCambio);
	
	public TipoCambio findTipoCambioByCountry(String codeCountry);
}
