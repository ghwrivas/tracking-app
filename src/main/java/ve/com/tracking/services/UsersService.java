package ve.com.tracking.services;

import java.util.List;

import org.springframework.mail.MailException;

import ve.com.tracking.exceptions.ClientException;
import ve.com.tracking.exceptions.ResourceNotFoundException;
import ve.com.tracking.forms.PasswordChangeForm;
import ve.com.tracking.model.Users;
import ve.com.tracking.security.User;

public interface UsersService {

	public abstract long countAllUserses();

	public abstract void deleteUsers(Users users);

	public abstract Users findUsers(Long id);

	public abstract List<Users> findAllUserses();

	public abstract List<Users> findUsersEntries(int firstResult, int maxResults);

	public abstract void saveUsers(Users users);

	public abstract Users updateUsers(Users users);

	/**
	 * Busca un usuario en la base de datos. El parametro searchString puede
	 * representar el username, email o ci_rif_passport
	 * 
	 * @param searchString
	 * @return
	 */
	public User findUserForAuthentication(String searchString);

	/**
	 * Busca el usuario en la base de datos de acuerdo al que este authenticado.
	 * 
	 * @param searchString
	 * @return
	 */
	public Users findUsersFromAuthentication(String searchString);

	/**
	 * Genera un password para el email del usuario pasado como parametro
	 * 
	 * @param email
	 * @throws ResourceNotFoundException
	 *             si no encuentra un usuario para el email pasado como
	 *             parametro
	 * @throws SendEmailException
	 *             si se produce un error enviando el email
	 */
	public void resetPassword(String email) throws IllegalArgumentException,
			ResourceNotFoundException, MailException;

	/**
	 * Genera un password aleatorio.
	 * 
	 * @param lenth
	 *            cantidad de caracteres para el password. Minimo 6 y Maximo 10
	 * @return
	 * @throws IllegalArgumentException
	 *             si el parametro pasado como parametro es incorrecto
	 */
	public String generateRandomPassword(int length)
			throws IllegalArgumentException;

	/**
	 * Busca si existe algun usuario en la base de datos con al menos uno de los
	 * datos pasados como parametros.
	 * 
	 * @param username
	 * @param ciRifPassport
	 * @param email
	 * @return
	 */
	public Users findUsers(String username, String ciRifPassport, String email);

	/**
	 * Cambia el password del usuario en session. Se verifica que el password
	 * actual sea el mismo del usuario en sesion y que los demas passwords
	 * coincida
	 * 
	 * @param passwordChangeForm
	 * @throws ClientException
	 *             error en las reglas de negocio
	 */
	public void changePassword(PasswordChangeForm passwordChangeForm)
			throws ClientException;

	/**
	 * Retorna el usuario authenticado en el sistema.
	 * 
	 * @return
	 */
	public Users getPrincipal();

	public Users getUserByEmail(String email);

	public List<Users> searchUsers(String searchString, int firstResult,
			int maxResults);

	public List<Users> searchUsers(String searchString);

	public Long countSearchUsers(String searchString);
	
	public boolean hasRole(String role);

}
