package ve.com.tracking.repository;

import java.util.List;

import ve.com.tracking.model.Users;

/**
 * 
 * @author Williams Rivas
 *
 * Created 26/03/2014 15:52:04
 */
public interface UsersRepositoryCustom {
    
    public List<Users> searchUsers(String searchString, int firstResult,
	    int maxResults);

    public List<Users> searchUsers(String searchString);

    public Long countSearchUsers(String searchString);
}
