package co.com.telefonica.ws.ui.dto.customer;

import lombok.Data;

@Data
public class FormatedAddressItem{
	private String formattedRespAddressSubsidy;
	private String formattedRespAddress;
	private String formattedRespSplitAddress;
	private String streetViaSuffix;
	private String townIdGeographicAddress;
	private String stateOrProvinceGeographicAddress;
	private String suburbNameGeographicAddress;
	private String nameCountry;
	private String formattedSubAddress;
	private String suburbRespCodeGeographicAddress;
	private String localityUrbanPropertyAddress;
	private String formattedRespGeoAddress;
}