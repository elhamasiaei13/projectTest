package com.parvanpajooh.cargoarchive.model.dto;

public class ManifestSaveUpdateDto {

	private Long id;
	private String manifestNumber;
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
	 * @return the manifestNumber
	 */
	public String getManifestNumber() {
		return manifestNumber;
	}
	/**
	 * @param manifestNumber the manifestNumber to set
	 */
	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ManifestSaveUpdateDto [id=");
		builder.append(id);
		builder.append(", manifestNumber=");
		builder.append(manifestNumber);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

	
}
