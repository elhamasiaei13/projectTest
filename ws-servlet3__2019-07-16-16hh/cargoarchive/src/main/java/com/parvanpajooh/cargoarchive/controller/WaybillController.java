package com.parvanpajooh.cargoarchive.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.parvanpajooh.cargoarchive.config.MyUserPrincipal;
import com.parvanpajooh.cargoarchive.dao.WaybillDao;
import com.parvanpajooh.cargoarchive.model.Waybill;
import com.parvanpajooh.cargoarchive.model.dto.WaybillSaveUpdateDto;
import com.parvanpajooh.cargoarchive.model.dto.WaybillViewDto;
import com.parvanpajooh.cargoarchive.service.WaybillService;
import com.parvanpajooh.cargoarchive.utils.Config;
import com.parvanpajooh.cargoarchive.utils.StreamUtil;

@RequestMapping("/waybills")
@Controller

public class WaybillController {
	static final Logger LOG = LoggerFactory.getLogger(WaybillController.class);

	String message = "please login";// TODO

	@Autowired
	WaybillService waybillService;
	

	/**
	 * 
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	
	@RequestMapping("/new/bulk")
	public ModelAndView newBulkWaybill(Model model, 
			HttpSession session, 
			RedirectAttributes redirectAttributes) 
	{ // TODO
		model.addAttribute("newWaybillCmd", new Waybill());
		return new ModelAndView("waybill-new-bulk");
		
	}
	
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/new")
	public ModelAndView newWaybill(Model model, 
			HttpSession session, RedirectAttributes redirectAttributes,
			@ModelAttribute("messageType") String messageType,
			@ModelAttribute("wWSUD") WaybillSaveUpdateDto wWSUD) { // TODO
		// throws???
		Waybill waybill=new Waybill();
		model.addAttribute("newWaybillCmd", waybill);
		
		if (wWSUD != null && wWSUD.getId() != null) {
			waybill.setWaybillNumber(wWSUD.getWaybillNumber());
			waybill.setDescription(wWSUD.getDescription());
		}
		/*
		 * model.addAttribute("newWaybillCmd",wWSUD);
		 */ 		
 		redirectAttributes.addFlashAttribute("messageType",messageType);
		return new ModelAndView("waybill-new");
	}

	/**
	 * 
	 * @param model
	 * @param file
	 * @param session
	 * @param redirectAttributes
	 * @param wWSUD
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String saveNew(
			Model model,
			@RequestParam("file") CommonsMultipartFile file,
			HttpSession session,
			RedirectAttributes redirectAttributes, 
			@ModelAttribute("currentUser") MyUserPrincipal currentUser,		
			@ModelAttribute("waybill") WaybillSaveUpdateDto wWSUD)
			throws Exception {
		try {

			LOG.debug("Entering saveNew(WaybillSaveUpdateDto)", wWSUD);
			// save
			waybillService.save(currentUser,wWSUD, file);
			// findBy
			String term = null;
			Integer page = 0;
			Integer size = 10;
			String sort = null;
			redirectAttributes.addFlashAttribute("messageType","SUCCESS");
			redirectAttributes.addFlashAttribute("message","Waybill [" + wWSUD.getId() + "] successfully save");
			return ("redirect:/waybills/");
			
		} catch (Exception e) {
			LOG.error("Error occurred while saving waybill", e);
			redirectAttributes.addFlashAttribute("messageType","FAILED");	
			redirectAttributes.addFlashAttribute("message","Waybill [" + wWSUD.getId() + "] failed save");
			redirectAttributes.addFlashAttribute("wWSUD", wWSUD);
			return ("redirect:new");
		}
	}
	
	
	/**
	 * 
	 * @param model
	 * @param file
	 * @param session
	 * @param redirectAttributes
	 * @param wWSUD
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bulk", method = RequestMethod.POST)
	public void saveNewBulk(Model model,
			@RequestParam("file") CommonsMultipartFile file,
			HttpSession session,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("currentUser") MyUserPrincipal currentUser,
			@ModelAttribute("waybill") WaybillSaveUpdateDto wWSUD)
					throws Exception {
		try {
			
			LOG.debug("Entering saveNew(WaybillSaveUpdateDto)", wWSUD);
			// save
			waybillService.save(currentUser,wWSUD, file);
			// findBy
			String term = null;
			Integer page = 0;
			Integer size = 10;
			String sort = null;
//			return ("");
		} catch (Exception e) {
			LOG.error("Error occurred while saving waybill", e);
			redirectAttributes.addFlashAttribute("messageType", "FAILED");
			redirectAttributes.addFlashAttribute("model", model);
//			redirectAttributes.addFlashAttribute("model", model);
//			return ("redirect:/waybills/new/bulk");
		}
	}
	
	

	/**
	 * 
	 * @param waybillNumber
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{waybillID}")
	public ModelAndView viewwaybill(
			@PathVariable long waybillID,
			Model model, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		WaybillViewDto viewWaybill = waybillService.get(waybillID);
		model.addAttribute("viewWaybilcmd", viewWaybill);
		return new ModelAndView("waybill-view");
	}
	
	
	/**
	 * 
	 * @param waybillNumber
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{waybillID}/update")
	public ModelAndView editForm(@PathVariable long waybillID,
			Model model, HttpSession session,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("messageType") String messageType,
			@ModelAttribute("wWSUD") WaybillSaveUpdateDto wWSUD) throws Exception {
		
		WaybillViewDto waybillView = waybillService.get(waybillID);
		redirectAttributes.addFlashAttribute("messageType", messageType);
		if (wWSUD != null && wWSUD.getId() != null) {
			waybillView.setWaybillNumber(wWSUD.getWaybillNumber());
			waybillView.setDescription(wWSUD.getDescription());
		}
		model.addAttribute("waybillView", waybillView);
		
		return new ModelAndView("waybill-update");
	}
	
	
/**
 * 
 * @param model
 * @param file
 * @param session
 * @param redirectAttributes
 * @param waybill
 * @return
 * @throws Exception
 */
	@RequestMapping(value = "/{waybillID}", method = RequestMethod.POST)
	public ModelAndView update(Model model, 
			@RequestParam("file") CommonsMultipartFile file,
			@ModelAttribute("currentUser") MyUserPrincipal currentUser,
			HttpSession session,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("wWSUD") WaybillSaveUpdateDto wWSUD) throws Exception {
		
		try {
			// update
			waybillService.update(currentUser,wWSUD, file);
			redirectAttributes.addFlashAttribute("messageType","SUCCESS");
			redirectAttributes.addFlashAttribute("message","Manifest [" + wWSUD.getId() + "] successfully edit");
			
			return new ModelAndView("redirect:/waybills/");
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message","Manifest [" + wWSUD.getId() + "] failed edit");
			redirectAttributes.addFlashAttribute("messageType", "FAILED");
			redirectAttributes.addFlashAttribute("wWSUD",wWSUD);
			return new ModelAndView("redirect:/waybills/"+wWSUD.getId()+"/update");
		}
	}
	
	
	/**
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/{id}/image")
	public void getImage(
			@PathVariable long id, 
			HttpServletResponse response) throws Exception {

		LOG.debug("Entering getImage(id)", id);
		
		try {
			// get waybill object
			WaybillViewDto viewWaybill = waybillService.get(id);

			LOG.debug("viewWaybill loaded. [{}]", viewWaybill);
			
			// update
			String repoBasePath = Config.getProperty(Config.REPO_BASE_PATH);
			File file = new File(repoBasePath, viewWaybill.getRelFilePathName());
			InputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			StreamUtil.copy(in, out);
		
		} catch (Exception e) {
			LOG.error("Error occurred while saving waybill", e);
		}
	}	

	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @param page
	 * @param size
	 * @param term
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String view(Model model,ModelMap map, HttpSession session, 
			RedirectAttributes redirectAttributes,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) String term,
			@RequestParam(required = false) String sort,
			@ModelAttribute("termsearch") String termsearch,
			@ModelAttribute("message") String message,
		    @ModelAttribute("messageType") String messageType) throws Exception {
			System.out.println("termsearch"+ termsearch);
			if(termsearch!= null){
			    term=termsearch; 
			    System.out.println("termsearch"+termsearch);
				}

		List<WaybillViewDto> waybillDtos = waybillService.findBy(term, page, size, sort);
		model.addAttribute("newWaybillCmd", new Waybill());
		model.addAttribute("waybills", waybillDtos);
		map.addAttribute("message", message);
		map.addAttribute("messageType", messageType);	
		return"waybills";
	}
	

	/**
	 * 
	 * @param waybillNumber
	 * @param session
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete/{waybillID}")
	public String newWaybill(@PathVariable Long waybillID, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			String page = "newForm";
			waybillService.delete(waybillID);
			redirectAttributes.addFlashAttribute("messageType","SUCCESS");
			redirectAttributes.addFlashAttribute("message","Manifest [" + waybillID + "] successfully deleted");
			return "redirect:/waybills/";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			redirectAttributes.addFlashAttribute("messageType","FAILED");
			redirectAttributes.addFlashAttribute("message","Failed to delete manifet [" + waybillID + "]");
			return "redirect:/waybills/";
		}
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @param waybill
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(Model model,ModelMap map, HttpSession session,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("waybill") WaybillSaveUpdateDto waybill)
					throws Exception {
		try {
			String searchterm= waybill.getWaybillNumber();
			redirectAttributes.addFlashAttribute("termsearch", searchterm);
			
			return("redirect:/waybills/") ;
			
		} catch (Exception e) {
			
			return ("redirect:/waybills/");
		}
	}
	
	
	
}
