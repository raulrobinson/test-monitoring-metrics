package co.com.telefonica.ws.ui.dto.customer;

import lombok.Data;

import java.util.List;

@Data
public class CustomerGSLInfoItem{
	private CustomerGSLItem customerGSLItem;
	private Object contactInformationItem;
	private CustomerInformationGSLItem customerInformationGSLItem;
	private CustomerAuxInfoItem customerAuxInfoItem;
	private List<CertificationHWItemItem> certificationHWItem;
	private List<PartyGSLItemItem> partyGSLItem;
}
