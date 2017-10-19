package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.Countries;
import ve.com.tracking.model.TipoCambio;

@Repository
public interface TipoCambioRepository extends JpaSpecificationExecutor<TipoCambio>, JpaRepository<TipoCambio, Long> {
	TipoCambio findByCountry(Countries country);
}
