package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ve.com.tracking.model.Countries;

@Repository
public interface CountriesRepository extends JpaSpecificationExecutor<Countries>, JpaRepository<Countries, String> {
	Countries findCountriesByIsoAlpha2(String code);
}
