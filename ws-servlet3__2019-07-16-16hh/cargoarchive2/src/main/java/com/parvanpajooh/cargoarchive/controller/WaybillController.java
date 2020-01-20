package com.parvanpajooh.cargoarchive.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public ModelAndView newBulkWaybill(Model model, HttpSession session, RedirectAttributes redirectAttributes) 
	{ // TODO
		model.addAttribute("newWaybillCmd", new Waybill());
		return new ModelAndView("waybill-new-bulk");

	}
	
	@RequestMapping("/test")
	public ModelAndView testnewBulkWaybill(Model model, HttpSession session, RedirectAttributes redirectAttributes) 
	{ // TODO
		model.addAttribute("newWaybillCmd", new Waybill());
		return new ModelAndView("testModal");
		
	}
	
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/new")
	public ModelAndView newWaybill(Model model, HttpSession session, RedirectAttributes redirectAttributes) { // TODO
		// throws???
		
		model.addAttribute("newWaybillCmd", new Waybill());
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
	public ModelAndView saveNew(Model model, @RequestParam("file") CommonsMultipartFile file, HttpSession session,
			RedirectAttributes redirectAttributes, @ModelAttribute("waybill") WaybillSaveUpdateDto wWSUD)
			throws Exception {
		try {

			LOG.debug("Entering saveNew(WaybillSaveUpdateDto)", wWSUD);
			
			// save
			waybillService.save(wWSUD, file);
			// findBy

			String term = null;
			Integer page = 0;
			Integer size = 10;
			String sort = null;
			return new ModelAndView("redirect:/waybills/");

		} catch (Exception e) {
			LOG.error("Error occurred while saving waybill", e);
			redirectAttributes.addFlashAttribute("message", "Failed!!!!!");
			return new ModelAndView("redirect:/waybills/");
		}

	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/multiWaybils")
    public String inputProduct(Model model) {
        return "waybill-multi";
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
	@RequestMapping(value = "/{waybillNumber}")
	public ModelAndView viewwaybill(@PathVariable long waybillNumber, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		WaybillViewDto viewWaybill = waybillService.get(waybillNumber);
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

	@RequestMapping(value = "/{waybillNumber}/update")
	public ModelAndView editForm(@PathVariable long waybillNumber, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		WaybillViewDto editWaybill = waybillService.get(waybillNumber);
		model.addAttribute("command", editWaybill);
		String page = "newForm";

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
	@RequestMapping(value = "/{waybillNumber}", method = RequestMethod.POST)
	public ModelAndView updateForm(Model model, @RequestParam("file") CommonsMultipartFile file, HttpSession session,
			RedirectAttributes redirectAttributes, @ModelAttribute("waybill") WaybillSaveUpdateDto waybill)
			throws Exception {
		try {
			// update
			waybillService.update(waybill, file);

			return new ModelAndView("redirect:/waybills/");

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Failed!!!!!");
			return new ModelAndView("redirect:/waybills/");
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
	public String view(Model model, HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
			@RequestParam(required = false) String term, @RequestParam(required = false) String sort) throws Exception {

		List<WaybillViewDto> waybillDtos = waybillService.findBy(term, page, size, sort);

		model.addAttribute("waybills", waybillDtos);

		return "waybills";
	}
	

	/**
	 * 
	 * @param waybillNumber
	 * @param session
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete/{waybillNumber}")
	public String newWaybill(@PathVariable Long waybillNumber, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		String page = "newForm";
		waybillService.delete(waybillNumber);
		return "redirect:/waybills/";
	}

}
