package co.com.telefonica.ws.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UProductItemDto {
	@JsonProperty("dniNumber")
	private String identificationNumber;
	private String product;
	private String serviceNumber;
	private String clientId;
	private String paymentReference;
	private String namePlan;
	@JsonProperty("dniType")
	private String identificationType;
	@JsonProperty("accountId")
	private String nmAcc;
	@JsonProperty("codePlan")
	private String cdPlan;
	private String productType;
	@JsonProperty("email")
	private String stCont;
	private String status;
	private String digitalInvoice;
}