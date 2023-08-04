package co.com.telefonica.ws.ui.dto.customer;

import lombok.Data;

import java.util.List;

@Data
public class IndividualGSLItem{
	private String partyIdParty;
	private String birthday;
	private String genderIndividual;
	private Object education;
	private Object occupation;
	private Object race;
	private Object passportNrPassportIdentification;
	private String endDateTimeTimePeriod;
	private String startDateTimeTimePeriod;
	private Object idTypeNationalIdentityCardIdentification;
	private Object salary;
	private Object religion;
	private Object formOfAddressIndividualName;
	private NameGSDItem nameGSDItem;
	private Object nationalityIndividual;
	private String maritalStatusIndividual;
	private Object languageHWItem;
	private List<Object> propertyHWItem;
	private Object position;
}
