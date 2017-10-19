/**
 * 
 */
package ve.com.tracking.services;

import java.util.List;
import ve.com.tracking.model.Authoritys;

/**
 * 
 * @author Williams Rivas
 *
 * Created 26/03/2014 15:13:39
 */
public interface AuthorityService {

    public abstract long countAllAuthorityses();

    public abstract void deleteAuthoritys(Authoritys authoritys);

    public abstract Authoritys findAuthoritys(Long id);

    public abstract List<Authoritys> findAllAuthorityses();

    public abstract List<Authoritys> findAuthoritysEntries(int firstResult,
	    int maxResults);

    public abstract void saveAuthoritys(Authoritys authoritys);

    public abstract Authoritys updateAuthoritys(Authoritys authoritys);
    
    public abstract List<Authoritys> findAuthoritysNotLike(String name);

}
