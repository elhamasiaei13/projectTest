package com.parvanpajooh.cargoarchive.model.dto;

import javax.management.loading.PrivateClassLoader;

public class WaybillSaveUpdateDto {
	private Long id;
	private String waybillNumber;
	private String description;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the waybillNumber
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}
	/**
	 * @param waybillNumber the waybillNumber to set
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}
	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaybillSaveUpdateDto [id=");
		builder.append(id);
		builder.append(", waybillNumber=");
		builder.append(waybillNumber);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
	
	
}
