package com.parvanpajooh.cargoarchive.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.parvanpajooh.cargoarchive.dao.UserDao;
import com.parvanpajooh.cargoarchive.dao.ManifestDao;
import com.parvanpajooh.cargoarchive.model.User;
import com.parvanpajooh.cargoarchive.model.Manifest;
import com.parvanpajooh.cargoarchive.model.dto.ManifestSaveUpdateDto;
import com.parvanpajooh.cargoarchive.model.dto.ManifestViewDto;
import com.parvanpajooh.cargoarchive.utils.Config;
import com.parvanpajooh.cargoarchive.utils.StreamUtil;

@Component
public class ManifestService {
	
	static final Logger LOG = LoggerFactory.getLogger(ManifestService.class);
	
	@Autowired
	ManifestDao dao;
	
	@Autowired
	UserDao userDao;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ManifestViewDto get(Long id) throws Exception {
		Manifest wb = dao.get(id);
//		org.apache.catalina.User us =userDao.getFindUser(wb.getUpdateUserId());
		
   
		ManifestViewDto wbDto = wb.toDto();
		Long createUserId = wb.getCreateUserId();
		
		if(createUserId !=null) {
			User user = userDao.get(wb.getCreateUserId());
			LOG.debug("create user:[{}]", user);
			
			if (user != null) {
				wbDto.setCreateUserName(user.getUserName());
			}
		 }

		Long updateId=wbDto.getUpdateUserId();
		if(updateId !=null) {
			User user = userDao.get(wbDto.getUpdateUserId());
			LOG.debug("update user:[{}]", user);
			
			if (user != null) {
				wbDto.setUpdateUserName(user.getUserName());
			}
		}
		
		return wbDto;
	}
	
	/**
	 * 
	 * @param term
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<ManifestViewDto> findBy(String term, Integer page, Integer size, String sort) throws Exception {

		LOG.debug("Entering findBy (term: {}, page: {}, size:{}, sort:{})", term, page, size, sort);
		
		// page
		if (page == null || page <= 0) {
			page = 0;
			
		} else {
			page = page - 1;
		}
		
		// size
		if (size == null || size <= 0) {
			size = 25;
			
		} else if (size > 100){
			size = 100;
		}
		
		if (sort == null) {
			sort = "ASC";
		}
		
		// query
		List<Manifest> list = dao.findBy(term, page, size, sort);
		
		LOG.debug("found [{}] record(s).", list.size());
		
		// to DTO
		List<ManifestViewDto> dtos = new ArrayList<>(list.size());
		for (Manifest thisWb : list) {
			LOG.debug("manifest:[{}]", thisWb);
		  
			ManifestViewDto thisDto = thisWb.toDto();
			
			Long createUserId = thisWb.getCreateUserId();
			if(createUserId !=null) {
				User user = userDao.get(thisWb.getCreateUserId());
				LOG.debug("create user:[{}]", user);
				
				if (user != null) {
					thisDto.setCreateUserName(user.getUserName());
				}
			 }

			Long updateId=thisWb.getUpdateUserId();
			if(updateId !=null) {
				User user = userDao.get(thisWb.getUpdateUserId());
				LOG.debug("update user:[{}]", user);
				
				if (user != null) {
					thisDto.setUpdateUserName(user.getUserName());
				}
			}
			
			//LOG.debug("manifestViewDtouser:[{}]", user);
			
//			 thisDto.setCreateUserName(user.getUserName());
			 
			
			 
			dtos.add(thisDto);
			
			LOG.debug("manifestViewDto:[{}]", thisDto);
		}

		return dtos;
	}

	/**
	 * 
	 * @param wsuDto
	 * @param file
	 * @throws Exception
	 */
	public void save(ManifestSaveUpdateDto wsuDto, CommonsMultipartFile file) throws Exception {
		try {

			LOG.debug("Entering save(wsuDto:{}, file:{})", wsuDto);
			
			Manifest manifest=new Manifest();
			// fileName
			String fileName = file.getOriginalFilename();
			// ManifestNumber
			String manifestNumber = wsuDto.getManifestNumber();
			if (manifestNumber != null) {
				manifestNumber = manifestNumber.trim();

				if (manifestNumber.length() == 0) {
					int index = fileName.lastIndexOf('.');
					if (index > 0) {
						manifestNumber = fileName.substring(0, index);
					}
				}
			}
			// createDate
			LocalDateTime createDate = LocalDateTime.now();
			DateTimeFormatter formatterCreateDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String createDateStr = createDate.format(formatterCreateDate);
			manifest.setCreateDate(createDate);
			
			// fileContent
			String repoBasePath = Config.getProperty(Config.REPO_BASE_PATH);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			String subfolderName = LocalDateTime.now().format(formatter);
			File outputDirectory = new File(repoBasePath, subfolderName);
			if (!outputDirectory.exists()) {
				outputDirectory.mkdirs();
			}
			File outFile = new File(outputDirectory, manifestNumber);
			FileOutputStream fos = new FileOutputStream(outFile);
			StreamUtil.copy(file.getInputStream(), fos);
			System.out.println("outFile" + outFile.toString());
			// file relativePath
			StringJoiner stringJoiner = new StringJoiner("/");
			stringJoiner.add(subfolderName);
			stringJoiner.add(manifestNumber);
			String fileRelPath = stringJoiner.toString();
			manifest.setRelFilePathName(fileRelPath);
			//createUserName
//			dao.findUser()
			// populate object
			manifest.setManifestNumber(manifestNumber);
			//description
			manifest.setDescription(wsuDto.getDescription());
		
			manifest.setRelFilePathName(fileRelPath);
			manifest.setCreateDate(createDate);
//			 manifest.setCreateUser(session.getAttribute("username").toString());
			manifest.setOriginalFileName(fileName);
			
			Long createuserId=2l;
			manifest.setCreateUserId(createuserId);
			dao.save(manifest);
			
			LOG.debug("manifest sved");
			
		} catch (Exception e) {
			throw new Exception("Error occurred while save", e);
		}
	}
	
	
	
	/**
	 * 
	 * @param wsuDto
	 * @param file
	 * @throws Exception
	 */
	public void update(ManifestSaveUpdateDto wsuDto, CommonsMultipartFile file) throws Exception {
		try {
			LOG.debug("update (wsuDto:{})", wsuDto.toString());

			// get current manifest
			Long id = wsuDto.getId();
			Manifest currWb = dao.get(id);
			
			String manifestNumber = wsuDto.getManifestNumber();
			String description = wsuDto.getDescription();
			
			// set file
			if (file.getSize() != 0) {
				// fileName
				String fileName = file.getOriginalFilename();

				// manifestNumber
				if (manifestNumber != null) {
					manifestNumber = manifestNumber.trim();

					if (manifestNumber.length() == 0) {
						int index = fileName.lastIndexOf('.');
						if (index > 0) {
							manifestNumber = fileName.substring(0, index);
						}
					}

				}
				
				// fileContent
				String repoBasePath = Config.getProperty(Config.REPO_BASE_PATH);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
				String subfolderName = LocalDateTime.now().format(formatter);
				File outputDirectory = new File(repoBasePath, subfolderName);
				System.out.println(outputDirectory);
				if (!outputDirectory.exists()) {
					outputDirectory.mkdirs();
				}
				File outFile = new File(outputDirectory, manifestNumber);
				FileOutputStream fos = new FileOutputStream(outFile);
				StreamUtil.copy(file.getInputStream(), fos);
				// file relativePath
				StringJoiner stringJoiner = new StringJoiner("/");
				stringJoiner.add(subfolderName);
				stringJoiner.add(manifestNumber);
				String fileRelPath = stringJoiner.toString();
				currWb.setRelFilePathName(fileRelPath);
				currWb.setOriginalFileName(fileName);
			}
			
			// set Manifest
			currWb.setManifestNumber(manifestNumber);
			
			// set description
			currWb.setDescription(description);

			// updateDate
			LocalDateTime updateDate = LocalDateTime.now();
			currWb.setUpdateDate(updateDate);
			
			//updateUserId
			Long updateuserId=2l;
			currWb.setUpdateUserId(updateuserId);
			

			dao.update(currWb);
			
		} catch (Exception e) {
			throw new Exception("Error occurred while edit", e);
		}
		
	}

	public void delete(Long id) throws Exception {
		try {
			dao.delete(id);

		} catch (Exception e) {
			throw new Exception("Error occurred while delete", e);
		
		}	}
	
}
