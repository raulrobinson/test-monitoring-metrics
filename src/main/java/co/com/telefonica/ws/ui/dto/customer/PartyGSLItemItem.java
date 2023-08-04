package co.com.telefonica.ws.ui.dto.customer;

import lombok.Data;

import java.util.List;

@Data
public class PartyGSLItemItem{
	private Object postalCodeAddress;
	private String formattedRespAddress;
	private AddressInformationItem addressInformationItem;
	private List<AddressPurposeItemItem> addressPurposeItem;
}
