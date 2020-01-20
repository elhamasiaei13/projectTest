package com.parvanpajooh.cargoarchive.model.dto;

import java.time.LocalDateTime;

public class WaybillViewDto {

	private Long id;
	private LocalDateTime createDate;
	private Long createUserId;
	private String createUserName;
	private LocalDateTime updateDate;
	private Long updateUserId;
	private String updateUserName;
	private String waybillNumber;
	private String originalFileName; // 53720641212.jpg
	private String relFilePathName;  // 20190620/53720641212.jpg
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
	 * @return the createDate
	 */
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}
	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * @return the updateDate
	 */
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the updateUserName
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}
	
	/**
	 * @param updateUserName the updateUserName to set
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	/**
	 * @return the updateUserId
	 */
	public Long getUpdateUserId() {
		return updateUserId;
	}
	/**
	 * @param updateUserId the updateUserId to set
	 */
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
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
	 * @return the originalFileName
	 */
	public String getOriginalFileName() {
		return originalFileName;
	}
	/**
	 * @param originalFileName the originalFileName to set
	 */
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	/**
	 * @return the relFilePathName
	 */
	public String getRelFilePathName() {
		return relFilePathName;
	}
	/**
	 * @param relFilePathName the relFilePathName to set
	 */
	public void setRelFilePathName(String relFilePathName) {
		this.relFilePathName = relFilePathName;
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
		builder.append("WaybillViewDto [id=");
		builder.append(id);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", createUserName=");
		builder.append(createUserName);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", updateUserName=");
		builder.append(updateUserName);
		builder.append(", waybillNumber=");
		builder.append(waybillNumber);
		builder.append(", originalFileName=");
		builder.append(originalFileName);
		builder.append(", relFilePathName=");
		builder.append(relFilePathName);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	} 
	
}
