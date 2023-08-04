package co.com.telefonica.ws.ui.dto.customer;

import lombok.Data;

@Data
public class CertificationHWItemItem{
	private Object certificationAuthority;
	private String idType;
	private Object validIdCustomerIdentification;
	private String passportNrPassportIdentification;
	private String endDateTimeTimePeriod;
	private String startDateTimeTimePeriod;
	private String idTypeNationalIdentityCardIdentification;
}