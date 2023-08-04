package co.com.telefonica.ws.common.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ************************************
 * **** NO BORRAR, NO MODIFICAR!!! ****
 * ************************************
 * SWAGGER OPENAPI TELEFONICA
 * Author: Microservices Governance
 * By: Arquitectura - @Telefonica 2023
 * Version: 2.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "openapi.properties")
public class OpenApiProperties {
	private String projectName;
	private String projectShortDescription;
	private String developerName;
	private String developerMail;
	private String projectTosMsg;
	private String projectTosLink;
	private String projectLicenceMsg;
	private String projectLicenceLink;
	private String organizationUrl;
}
