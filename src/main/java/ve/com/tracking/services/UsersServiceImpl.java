package ve.com.tracking.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.exceptions.ClientException;
import ve.com.tracking.exceptions.ResourceNotFoundException;
import ve.com.tracking.forms.PasswordChangeForm;
import ve.com.tracking.mail.EmailTemplateResetPassword;
import ve.com.tracking.model.Authorities;
import ve.com.tracking.model.Users;
import ve.com.tracking.repository.AuthoritiesRepository;
import ve.com.tracking.repository.UsersRepository;
import ve.com.tracking.security.PasswordEncoderSha256;
import ve.com.tracking.security.Role;
import ve.com.tracking.security.User;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 17/04/2014 11:06:35
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	PasswordEncoderSha256 passwordEncoder;

	@Autowired
	AuthoritiesRepository authoritiesRepository;

	@Autowired
	MailService mailService;

	public long countAllUserses() {
		return usersRepository.count();
	}

	public void deleteUsers(Users users) {
		usersRepository.delete(users);
	}

	public Users findUsers(Long id) {
		return usersRepository.findOne(id);
	}

	public List<Users> findAllUserses() {
		return usersRepository.findAll();
	}

	public List<Users> findUsersEntries(int firstResult, int maxResults) {
		return usersRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	public void saveUsers(Users users) {
		Calendar date = Calendar.getInstance();
		users.setCreated(date);
		users.setUpdated(date);
		users.setPassword(passwordEncoder.encodeSha256Password(users
				.getPassword()));
		usersRepository.save(users);
	}

	public Users updateUsers(Users users) {
		Calendar date = Calendar.getInstance();
		users.setUpdated(date);
		users.setPassword(passwordEncoder.encodeSha256Password(users
				.getPassword()));
		return usersRepository.save(users);
	}

	@Override
	public User findUserForAuthentication(String searchString) {
		Users users = usersRepository.findByUsername(searchString);
		if (users == null) {
			users = usersRepository.findByEmail(searchString);
		}
		if (users == null) {
			users = usersRepository.findByCiRifPassport(searchString);
		}
		if (users == null) {
			return null;
		}
		List<Role> roles = new ArrayList<Role>();
		List<Authorities> authorities = authoritiesRepository
				.findByUsername(users);
		if (authorities != null) {
			for (Authorities authoritie : authorities) {
				roles.add(new Role(authoritie.getAuthority().getAuthority()));
			}
		}
		return new User(users, roles);
	}

	@Override
	public void resetPassword(String email) throws IllegalArgumentException,
			ResourceNotFoundException, MailException {
		if (email == null) {
			throw new IllegalArgumentException(
					"No se ha pasado ningun email como parametro");
		}
		Users user = usersRepository.findByEmail(email.trim());
		if (user == null) {
			throw new ResourceNotFoundException("Usuario no encontrado");
		}
		String newPassword = generateRandomPassword(10);
		String newPasswordEncoded = passwordEncoder
				.encodeSha256Password(newPassword);
		EmailTemplateResetPassword template = new EmailTemplateResetPassword(
				user.getNombreCompleto(), user.getUsername(), newPassword);
		mailService.sendEmail(email.trim(), "Generación de Nuevo Password",
				template, null);
		user.setPassword(newPasswordEncoded);
		usersRepository.saveAndFlush(user);
	}

	@Override
	public String generateRandomPassword(int length)
			throws IllegalArgumentException {
		if (length < 6 || length > 10) {
			throw new IllegalArgumentException(
					"La longitud para el password debe ser mayor o igual a 6 y menor o igual a 10 caracteres");
		}
		return RandomStringUtils.randomAlphanumeric(length);
	}

	@Override
	public Users findUsers(String username, String ciRifPassport, String email) {
		return usersRepository.findByUsernameOrCiRifPassportOrEmail(username,
				ciRifPassport, email);
	}

	@Override
	public Users findUsersFromAuthentication(String searchString) {
		Users users = usersRepository.findByUsername(searchString);
		if (users == null) {
			users = usersRepository.findByEmail(searchString);
		}
		if (users == null) {
			users = usersRepository.findByCiRifPassport(searchString);
		}
		return users;
	}

	@Override
	public void changePassword(PasswordChangeForm passwordChangeForm)
			throws ClientException {
		Users user = usersRepository.findOne(passwordChangeForm.getId());
		if (user == null)
			throw new ClientException(
					"Usuario no encontrado. Password no modificado");
		String currentPasswordEncoded = passwordEncoder
				.encodeSha256Password(passwordChangeForm.getCurrentPassword());
		if (!currentPasswordEncoded.equals(user.getPassword()))
			throw new ClientException("Current password invalido");
		String newPassword = passwordChangeForm.getNewPassword().trim();
		String confirmNewPassword = passwordChangeForm.getConfirmNewPassword()
				.trim();
		if (!newPassword.equals(confirmNewPassword))
			throw new ClientException("Nuevos passwords no coinciden");
		String newPasswordEncoded = passwordEncoder
				.encodeSha256Password(newPassword);
		if (newPasswordEncoded.equals(currentPasswordEncoded))
			throw new ClientException(
					"El nuevo password es igual al actual. Password no cambiado");
		user.setPassword(newPasswordEncoded);
		usersRepository.saveAndFlush(user);
	}

	@Override
	public Users getPrincipal() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		return findUsersFromAuthentication(auth.getName());
	}

	@Override
	public Users getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	@Override
	public List<Users> searchUsers(String searchString, int firstResult,
			int maxResults) {
		return usersRepository.searchUsers(searchString, firstResult,
				maxResults);
	}

	@Override
	public List<Users> searchUsers(String searchString) {
		return usersRepository.searchUsers(searchString);
	}

	@Override
	public Long countSearchUsers(String searchString) {
		return usersRepository.countSearchUsers(searchString);
	}

	@Override
	public boolean hasRole(String role) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		for (GrantedAuthority grantedAuthority : roles) {
			if(grantedAuthority.getAuthority().equals(role)){
				return true;
			}
		}
		return false;
	}
}
