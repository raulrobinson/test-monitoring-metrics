package co.com.telefonica.ws.ui.dto.customer;

import lombok.Data;

import java.util.List;

@Data
public class CustomerInformationGSLItem{
	private Object interactionStatusBusinessInteraction;
	private String iDCustomer;
	private String nameCustomer;
	private Object customerRankCustomer;
	private String customerSegment;
	private String customerStatusTime;
	private String endDateTimeTimePeriod;
	private String startDateTimeTimePeriod;
	private String customerSubSegment;
	private Object nameGSLItem;
	private List<PropertyHWItemItem> propertyHWItem;
	private String statusCustomer;
}
