/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.services;

import ve.com.tracking.exceptions.AccountActivationException;
import ve.com.tracking.exceptions.ResourceNotFoundException;
import ve.com.tracking.forms.AccountCreateForm;
import ve.com.tracking.forms.AccountUpdateForm;
import ve.com.tracking.model.Users;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 26/03/2014 15:11:48
 */
public interface AccountManager {

    public void createAccount(AccountCreateForm account);

    public void updateAccount(AccountUpdateForm account)
	    throws ResourceNotFoundException;

    public void activateAccount(String email, String token)
	    throws ResourceNotFoundException, AccountActivationException;
    
    public void deleteAccount(Users user);
    
    public void resendActivationLink(String email);
}
