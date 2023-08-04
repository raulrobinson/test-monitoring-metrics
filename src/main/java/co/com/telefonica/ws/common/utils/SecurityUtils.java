package co.com.telefonica.ws.common.utils;

import co.com.telefonica.ws.persistence.entity.Locations;
import co.com.telefonica.ws.persistence.entity.User;
import co.com.telefonica.ws.ui.dto.LocationsResponseDto;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ******************************************************
 * **** NO BORRAR, SE PUEDE ADICIONAR O PERSONALIZAR ****
 * ******************************************************
 * Description: Función que se encarga de cubrir la vulnerabilidad de cross site scripting
 * 				Comentario X para activar el flujo de DevOps.
 * @version 2.1.0
 */
public class SecurityUtils {

	private SecurityUtils() {}

	/**
	 * Blind parameter string.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String blindStr(String value) {
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		return policy.sanitize( value );
	}

	/**
	 * Blind parameter long.
	 *
	 * @param value the value
	 * @return the long
	 */
	public static Long blindParameterLong(long value) {
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		return Long.valueOf(policy.sanitize(String.valueOf(value)));
	}

	/**
	 * Blind parameter int int.
	 *
	 * @param value the value
	 * @return the int
	 */
	public static int blindInt(int value) {
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		return Math.toIntExact(Long.parseLong(policy.sanitize(String.valueOf(value))));
	}

	/**
	 * Blind locations list.
	 *
	 * @param locationsList the locations list
	 * @return the list
	 */
	public static List<Locations> blindLocationsList(List<Locations> locationsList) {
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		List<Locations> sanitizedResponseList = new ArrayList<>();
		for (Locations entity : locationsList) {
			Long sanitizedId = Long.valueOf(policy.sanitize(String.valueOf(entity.getId())));
			String sanitizedRegion = policy.sanitize(entity.getRegion());
			String sanitizedDepartamento = policy.sanitize(entity.getDepartamento());
			String sanitizedMunicipio = policy.sanitize(entity.getMunicipio());
			String sanitizedCodigoDaneDelMunicipio = policy.sanitize(entity.getCodigoDaneDelMunicipio());
			String sanitizedCodigoDaneDelDepartamento = policy.sanitize(entity.getCodigoDaneDelDepartamento());

			Locations sanitizedEntity = new Locations();
			sanitizedEntity.setId(sanitizedId);
			sanitizedEntity.setRegion(sanitizedRegion);
			sanitizedEntity.setDepartamento(sanitizedDepartamento);
			sanitizedEntity.setMunicipio(sanitizedMunicipio);
			sanitizedEntity.setCodigoDaneDelMunicipio(sanitizedCodigoDaneDelMunicipio);
			sanitizedEntity.setCodigoDaneDelDepartamento(sanitizedCodigoDaneDelDepartamento);

			sanitizedResponseList.add(sanitizedEntity);
		}

		return sanitizedResponseList;
	}

	/**
	 * Blind LocationsOutDTO.
	 *
	 * @param req the req
	 * @return the locations out dto
	 */
	public static LocationsResponseDto blindLocations(Optional<Locations> req) {
		LocationsResponseDto sanitizedEntity = new LocationsResponseDto();
		Long resId = null;
		String resRegion = null;
		String resDepartamento = null;
		String resMunicipio = null;
		String resCodigoDaneDelMunicipio = null;
		String resCodigoDaneDelDepartamento = null;
		if (req.isPresent()) {
			resId = req.get().getId();
			resRegion = req.get().getRegion();
			resDepartamento = req.get().getDepartamento();
			resMunicipio = req.get().getMunicipio();
			resCodigoDaneDelDepartamento = req.get().getCodigoDaneDelMunicipio();
			resCodigoDaneDelMunicipio = req.get().getCodigoDaneDelDepartamento();
		}
		sanitizedEntity.setId(resId);
		sanitizedEntity.setRegion(resRegion);
		sanitizedEntity.setDepartamento(resDepartamento);
		sanitizedEntity.setMunicipio(resMunicipio);
		sanitizedEntity.setCodigoDaneDelMunicipio(resCodigoDaneDelMunicipio);
		sanitizedEntity.setCodigoDaneDelDepartamento(resCodigoDaneDelDepartamento);

		return sanitizedEntity;
	}

	public static Iterable<User> blindUsersList(Iterable<User> usersList) {
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		List<User> sanitizedUsersList = new ArrayList<>();
		for (User user : usersList) {
			int id = user.getId();
			String username = policy.sanitize(user.getUsername());
			String password = policy.sanitize(user.getPassword());
			String role = policy.sanitize(user.getRole());
			String email = policy.sanitize(user.getEmail());
			var sanitizeUser = new User();
			sanitizeUser.setId(id);
			sanitizeUser.setUsername(username);
			sanitizeUser.setPassword(password);
			sanitizeUser.setRole(role);
			sanitizeUser.setEmail(email);

			sanitizedUsersList.add(sanitizeUser);
		}

		return sanitizedUsersList;
	}

	public static User blindUsers(Optional<User> req) {
		var sanitizeUser = new User();
		int id = 0;
		String username = null;
		String password = null;
		String role = null;
		String email = null;
		if (req.isPresent()) {
			id = req.get().getId();
			username = req.get().getUsername();
			password = req.get().getPassword();
			role = req.get().getRole();
			email = req.get().getEmail();
		}
		sanitizeUser.setId(id);
		sanitizeUser.setUsername(username);
		sanitizeUser.setPassword(password);
		sanitizeUser.setRole(role);
		sanitizeUser.setEmail(email);

		return sanitizeUser;
	}
}
