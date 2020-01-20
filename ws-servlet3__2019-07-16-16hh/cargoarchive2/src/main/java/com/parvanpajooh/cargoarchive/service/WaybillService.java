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
import com.parvanpajooh.cargoarchive.dao.WaybillDao;
import com.parvanpajooh.cargoarchive.model.User;
import com.parvanpajooh.cargoarchive.model.Waybill;
import com.parvanpajooh.cargoarchive.model.dto.WaybillSaveUpdateDto;
import com.parvanpajooh.cargoarchive.model.dto.WaybillViewDto;
import com.parvanpajooh.cargoarchive.utils.Config;
import com.parvanpajooh.cargoarchive.utils.StreamUtil;

@Component
public class WaybillService {
	
	static final Logger LOG = LoggerFactory.getLogger(WaybillService.class);
	
	@Autowired
	WaybillDao dao;
	
	@Autowired
	UserDao userDao;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WaybillViewDto get(Long id) throws Exception {
		Waybill wb = dao.get(id);
//		org.apache.catalina.User us =userDao.getFindUser(wb.getUpdateUserId());
		
   
		WaybillViewDto wbDto = wb.toDto();
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
	public List<WaybillViewDto> findBy(String term, Integer page, Integer size, String sort) throws Exception {

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
		List<Waybill> list = dao.findBy(term, page, size, sort);
		
		LOG.debug("found [{}] record(s).", list.size());
		
		// to DTO
		List<WaybillViewDto> dtos = new ArrayList<>(list.size());
		for (Waybill thisWb : list) {
			LOG.debug("waybill:[{}]", thisWb);
		  
			WaybillViewDto thisDto = thisWb.toDto();
			
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
			
			//LOG.debug("WaybillViewDtouser:[{}]", user);
			
//			 thisDto.setCreateUserName(user.getUserName());
			 
			
			 
			dtos.add(thisDto);
			
			LOG.debug("WaybillViewDto:[{}]", thisDto);
		}

		return dtos;
	}

	/**
	 * 
	 * @param wsuDto
	 * @param file
	 * @throws Exception
	 */
	public void save(WaybillSaveUpdateDto wsuDto, CommonsMultipartFile file) throws Exception {
		try {

			LOG.debug("Entering save(wsuDto:{}, file:{})", wsuDto);
			
			Waybill waybill=new Waybill();
			// fileName
			String fileName = file.getOriginalFilename();
			// waybillNumber
			String waybillNumber = wsuDto.getWaybillNumber();
			if (waybillNumber == null) {
				waybillNumber = "";
			}
			if (waybillNumber != null) {
				waybillNumber = waybillNumber.trim();

				if (waybillNumber.length() == 0) {
					int index = fileName.lastIndexOf('.');
					if (index > 0) {
						waybillNumber = fileName.substring(0, index);
					}
				}
			}
			// createDate
			LocalDateTime createDate = LocalDateTime.now();
			DateTimeFormatter formatterCreateDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String createDateStr = createDate.format(formatterCreateDate);
			waybill.setCreateDate(createDate);
			
			// fileContent
			String repoBasePath = Config.getProperty(Config.REPO_BASE_PATH);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			String subfolderName = LocalDateTime.now().format(formatter);
			File outputDirectory = new File(repoBasePath, subfolderName);
			if (!outputDirectory.exists()) {
				outputDirectory.mkdirs();
			}
			File outFile = new File(outputDirectory, waybillNumber);
			FileOutputStream fos = new FileOutputStream(outFile);
			StreamUtil.copy(file.getInputStream(), fos);
			System.out.println("outFile" + outFile.toString());
			// file relativePath
			StringJoiner stringJoiner = new StringJoiner("/");
			stringJoiner.add(subfolderName);
			stringJoiner.add(waybillNumber);
			String fileRelPath = stringJoiner.toString();
			waybill.setRelFilePathName(fileRelPath);
			//createUserName
//			dao.findUser()
			// populate object
			waybill.setWaybillNumber(waybillNumber);
			//description
			waybill.setDescription(wsuDto.getDescription());
		
			waybill.setRelFilePathName(fileRelPath);
			waybill.setCreateDate(createDate);
//			 waybill.setCreateUser(session.getAttribute("username").toString());
			waybill.setOriginalFileName(fileName);
			
			Long createuserId=2l;
			waybill.setCreateUserId(createuserId);
			dao.save(waybill);
			
			LOG.debug("waybill sved");
			
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
	public void update(WaybillSaveUpdateDto wsuDto, CommonsMultipartFile file) throws Exception {
		try {
			LOG.debug("update (wsuDto:{})", wsuDto.toString());

			// get current waybill
			Long id = wsuDto.getId();
			Waybill currWb = dao.get(id);
			
			String waybillNumber = wsuDto.getWaybillNumber();
			String description = wsuDto.getDescription();
			
			// set file
			if (file.getSize() != 0) {
				// fileName
				String fileName = file.getOriginalFilename();

				// waybillNumber
				if (waybillNumber != null) {
					waybillNumber = waybillNumber.trim();

					if (waybillNumber.length() == 0) {
						int index = fileName.lastIndexOf('.');
						if (index > 0) {
							waybillNumber = fileName.substring(0, index);
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
				File outFile = new File(outputDirectory, waybillNumber);
				FileOutputStream fos = new FileOutputStream(outFile);
				StreamUtil.copy(file.getInputStream(), fos);
				// file relativePath
				StringJoiner stringJoiner = new StringJoiner("/");
				stringJoiner.add(subfolderName);
				stringJoiner.add(waybillNumber);
				String fileRelPath = stringJoiner.toString();
				currWb.setRelFilePathName(fileRelPath);
				currWb.setOriginalFileName(fileName);
			}
			
			// set wayBill
			currWb.setWaybillNumber(waybillNumber);
			
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
