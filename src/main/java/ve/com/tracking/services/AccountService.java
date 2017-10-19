/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.services;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.core.Util;
import ve.com.tracking.exceptions.AccountActivationException;
import ve.com.tracking.exceptions.ResourceNotFoundException;
import ve.com.tracking.forms.AccountCreateForm;
import ve.com.tracking.forms.AccountUpdateForm;
import ve.com.tracking.mail.EmailTemplateCreateAccount;
import ve.com.tracking.mail.EmailTemplateResendActivationLink;
import ve.com.tracking.model.ActivationToken;
import ve.com.tracking.model.Authorities;
import ve.com.tracking.model.Authoritys;
import ve.com.tracking.model.Users;
import ve.com.tracking.repository.ActivationTokenRepository;
import ve.com.tracking.repository.AuthoritiesRepository;
import ve.com.tracking.repository.AuthorityRepository;
import ve.com.tracking.repository.EmpresaRepository;
import ve.com.tracking.repository.UsersRepository;
import ve.com.tracking.security.PasswordEncoderSha256;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 26/03/2014 15:12:05
 */
@Service
@Transactional
public class AccountService implements AccountManager {

	@Autowired
	UsersService userService;

	@Autowired
	UsersRepository userRepository;

	@Autowired
	PasswordEncoderSha256 passwordEncoder;

	@Autowired
	AuthoritiesRepository authoritiesRepository;

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	MailService mailService;

	@Autowired
	ActivationTokenRepository activationTokenRepository;

	@Autowired
	EmpresaRepository empresaRepository;

	@Override
	public void createAccount(AccountCreateForm account) {
		Users user = new Users();
		// account create fields parser
		user.setUsername(account.getUsername());
		user.setCiRifPassport(account.getCiRifPassport());
		user.setFirstName(account.getFirstName());
		user.setLastName(account.getLastName());
		user.setPassword(account.getPassword());
		user.setDireccion(account.getDireccion());
		user.setEmail(account.getEmail());
		user.setTelefono(account.getTelefono());
		// cuenta inactiva
		user.setEnabled(false);
		user.setCreated(Calendar.getInstance());
		user.setUpdated(Calendar.getInstance());
		// save user
		userService.saveUsers(user);
		// generate token activacion
		String token = UUID.randomUUID().toString();
		ActivationToken activationToken = new ActivationToken();
		activationToken.setEnabled(true);
		activationToken.setToken(token);
		activationToken.setUsersId(user);
		activationTokenRepository.save(activationToken);
		// default role
		Authoritys roleClient = authorityRepository
				.findByAuthority("ROLE_CLIENT");
		Authorities authorities = new Authorities();
		authorities.setAuthority(roleClient);
		authorities.setUsername(user);
		authoritiesRepository.save(authorities);

		EmailTemplateCreateAccount template = new EmailTemplateCreateAccount(
				user.getNombreCompleto(), user.getEmail(),
				account.getPassword(), getUrlActivation(user, token));
		mailService
				.sendEmail(user.getEmail(), "Activación de cuenta", template, null);
	}

	private String getUrlActivation(Users user, String token) {
		String urlActivation = Util.getBaseUrl()
				+ "account/activate?email=%s&token=%s";
		urlActivation = String.format(urlActivation, user.getEmail(), token);
		return urlActivation;
	}

	@Override
	public void activateAccount(String email, String token)
			throws ResourceNotFoundException, AccountActivationException {
		email = email.trim().toLowerCase();
		ActivationToken activationToken = activationTokenRepository
				.findByToken(token);
		if (activationToken == null) {
			throw new ResourceNotFoundException("Token no encotrado");
		}
		Users user = activationToken.getUsersId();
		String userEmail = user.getEmail().trim().toLowerCase();
		if (!userEmail.equals(email)) {
			throw new ResourceNotFoundException("Email no encotrado");
		}
		if (!activationToken.isEnabled()) {
			throw new AccountActivationException("Token ya fue usado");
		}
		// desabilitar el token
		activationToken.setEnabled(false);
		// activar cuenta usuario
		user.setEnabled(true);
		// save
		activationTokenRepository.save(activationToken);
		user.setUpdated(Calendar.getInstance());
		userRepository.save(user);
	}

	@Override
	public void updateAccount(AccountUpdateForm account)
			throws ResourceNotFoundException {
		Users user = userRepository.findOne(account.getId());
		if (user == null)
			throw new ResourceNotFoundException(
					"Usuario en sesión no encontrado");
		user.setFirstName(account.getFirstName());
		user.setLastName(account.getLastName());
		user.setDireccion(account.getDireccion());
		user.setTelefono(account.getTelefono());
		user.setBbPin(account.getBbPin());
		user.setUpdated(Calendar.getInstance());
		userRepository.save(user);
	}

	@Override
	public void deleteAccount(Users user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resendActivationLink(String email) {
		Users user = userService.getUserByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException("Recurso no encontrado");
		}
		ActivationToken token = activationTokenRepository.findByUsersId(user);
		if (token == null)
			throw new ResourceNotFoundException("Recurso no encontrado");
		if (!token.isEnabled()) {
			throw new AccountActivationException("Token ya fue usado");
		}
		EmailTemplateResendActivationLink template = new EmailTemplateResendActivationLink(
				user.getNombreCompleto(), getUrlActivation(user,
						token.getToken()));
		mailService.sendEmail(user.getEmail(),
				"Reenvío de enlace de Activación de cuenta", template, null);
	}

}
