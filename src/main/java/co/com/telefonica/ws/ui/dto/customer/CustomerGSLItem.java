package co.com.telefonica.ws.ui.dto.customer;

import lombok.Data;

import java.util.List;

@Data
public class CustomerGSLItem{
	private Object formOfAddressIndividualName;
	private NameGSDItem nameGSDItem;
	private List<ContactInformationItemItem> contactInformationItem;
	private String iDCustomer;
	private String nameCustomer;
}
