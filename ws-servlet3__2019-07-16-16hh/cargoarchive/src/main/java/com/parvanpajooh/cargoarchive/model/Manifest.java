package com.parvanpajooh.cargoarchive.model;

import java.time.LocalDateTime;

import com.parvanpajooh.cargoarchive.model.dto.*;

public class Manifest {
	private Long id;
	private LocalDateTime createDate;
	private Long createUserId;
	private LocalDateTime updateDate;
	private Long updateUserId;
	private String manifestNumber;
	private String originalFileName;
	private String relFilePathName;
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
	
	/**
	 * 
	 * @return
	 */
	public ManifestViewDto toDto() {
		ManifestViewDto dto = new ManifestViewDto();
		
		dto.setId(this.id);
		dto.setCreateDate(this.createDate);
		dto.setCreateUserId(this.createUserId);
		dto.setUpdateDate(this.updateDate);
		dto.setUpdateUserId(this.updateUserId);
		dto.setManifestNumber(this.manifestNumber);
		dto.setOriginalFileName(this.originalFileName);
		dto.setRelFilePathName(this.relFilePathName);
		dto.setDescription(this.description);
		
		return dto;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Manifest [id=");
		builder.append(id);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", createUserId=");
		builder.append(createUserId);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", updateUserId=");
		builder.append(updateUserId);
		builder.append(", waybillNumber=");
		builder.append(manifestNumber);
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
