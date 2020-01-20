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
import org.springframework.security.core.userdetails.UserDetails;
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
import com.parvanpajooh.cargoarchive.model.Manifest;
import com.parvanpajooh.cargoarchive.model.Waybill;
import com.parvanpajooh.cargoarchive.model.dto.ManifestSaveUpdateDto;
import com.parvanpajooh.cargoarchive.model.dto.ManifestViewDto;
import com.parvanpajooh.cargoarchive.model.dto.WaybillSaveUpdateDto;
import com.parvanpajooh.cargoarchive.model.dto.WaybillViewDto;
import com.parvanpajooh.cargoarchive.service.ManifestService;
import com.parvanpajooh.cargoarchive.utils.Config;
import com.parvanpajooh.cargoarchive.utils.StreamUtil;

@RequestMapping("/manifests")
@Controller
public class ManifestController {

	static final Logger LOG = LoggerFactory.getLogger(ManifestController.class);

	String message = "please login";// TODO

	@Autowired
	ManifestService manifestService;
	

	/**
	 * @RequestMapping("/new/bulk")
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/new/bulk")
	public ModelAndView newBulkManifest(Model model, HttpSession session, RedirectAttributes redirectAttributes)
	{ // TODO
		model.addAttribute("newManifestCmd", new Manifest());
		return new ModelAndView("manifest-new-bulk");
	}
	
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/new")
	public ModelAndView newManifest(Model model, HttpSession session, 
			RedirectAttributes redirectAttributes,
			@ModelAttribute("messageType") String messageType,
			@ModelAttribute("mMSUD") ManifestSaveUpdateDto mMSUD)
	{ // TODO
		Manifest manifest=new Manifest();
		model.addAttribute("newManifestCmd",manifest);
		if (mMSUD != null ) {
			manifest.setManifestNumber(mMSUD.getManifestNumber());
			manifest.setDescription(mMSUD.getDescription());
		}
//		model.addAttribute("newManifestCmd",mMSUD);
 		redirectAttributes.addFlashAttribute("messageType",messageType);
		return new ModelAndView("manifest-new");
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
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public String saveNew(
			Model model, 
			@RequestParam("file") CommonsMultipartFile file, 
			HttpSession session,
			RedirectAttributes redirectAttributes, 
			@ModelAttribute("currentUser") MyUserPrincipal currentUser,
			@ModelAttribute("manifest") ManifestSaveUpdateDto mMSUD) throws Exception {
		try {
			// save
			manifestService.save(currentUser, mMSUD, file);
			// findBy

			String term = null;
			Integer page = 0;
			Integer size = 10;
			String sort = null;
			redirectAttributes.addFlashAttribute("messageType","SUCCESS");
			redirectAttributes.addFlashAttribute("message","Manifest [" + mMSUD.getId() + "] successfully save");
			return ("redirect:/manifests/");

		} catch (Exception e) {
			LOG.error("Error occurred while saving manifest", e);
			redirectAttributes.addFlashAttribute("message","Manifest failed save");
			redirectAttributes.addFlashAttribute("messageType","FAILED");
			redirectAttributes.addFlashAttribute("mMSUD", mMSUD);
			return ("redirect:new");
		}

	}
	
	
	/**
	 * 
	 * @param model
	 * @param file
	 * @param session
	 * @param redirectAttributes
	 * @param mMSUD
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bulk", method=RequestMethod.POST)
	public void saveNewBulk(
			Model model, 
			@RequestParam("file") CommonsMultipartFile file, 
			HttpSession session,
			RedirectAttributes redirectAttributes, 
			@ModelAttribute("currentUser") MyUserPrincipal currentUser,
			@ModelAttribute("manifest") ManifestSaveUpdateDto mMSUD) throws Exception {
		
		try {
			
			// save
			manifestService.save(currentUser, mMSUD, file);
			// findBy
			String term = null;
			Integer page = 0;
			Integer size = 10;
			String sort = null;
//			return ("redirect:/manifests/");
			
		} catch (Exception e) {
			LOG.error("Error occurred while saving manifest", e);
			redirectAttributes.addFlashAttribute("message", "Failed");
			redirectAttributes.addFlashAttribute("messageType", "FAILED");
			redirectAttributes.addFlashAttribute("model", model);
//			return ("redirect:/manifests/new/bulk");
		}
	}
	

	/**
	 * 
	 * @param manifestNumber
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{manifestID}")
	public ModelAndView viewmanifest(
			@PathVariable long manifestID, 
			Model model, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		ManifestViewDto viewManifest = manifestService.get(manifestID);
		model.addAttribute("viewManifestcmd", viewManifest);
		return new ModelAndView("manifest-view");
	}
	

	/**
	 * 
	 * @param manifestNumber
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{manifestID}/update")
	public ModelAndView editForm(
			@PathVariable long manifestID, Model model, 
			HttpSession session,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("messageType") String messageType,
			@ModelAttribute("mMSUD") ManifestSaveUpdateDto mMSUD) throws Exception {
		
		ManifestViewDto editManifest = manifestService.get(manifestID);
		redirectAttributes.addFlashAttribute("messageType", messageType);
		if (mMSUD != null && mMSUD.getId() != null) {
			
			editManifest.setManifestNumber(mMSUD.getManifestNumber());
			editManifest.setDescription(mMSUD.getDescription());
		}
		model.addAttribute("command", editManifest);

		return new ModelAndView("manifest-update");
	}
	
		
	/**
	 * 
	 * @param model
	 * @param file
	 * @param session
	 * @param redirectAttributes
	 * @param manifest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{manifestID}", method = RequestMethod.POST)
	public ModelAndView updateForm(Model model,
			@RequestParam("file") CommonsMultipartFile file,
			@ModelAttribute("currentUser") MyUserPrincipal currentUser,
			HttpSession session,
			RedirectAttributes redirectAttributes, 
			@ModelAttribute("manifest") ManifestSaveUpdateDto mMSUD) throws Exception {
		
		try {
			// update
			manifestService.update(currentUser,mMSUD, file);
			redirectAttributes.addFlashAttribute("messageType","SUCCESS");
			redirectAttributes.addFlashAttribute("message","Manifest [" + mMSUD.getId() + "] successfully edit");
			return new ModelAndView("redirect:/manifests/");

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message","Manifest [" + mMSUD.getId() + "] failed edit");
			redirectAttributes.addFlashAttribute("messageType", "FAILED");
			redirectAttributes.addFlashAttribute("mMSUD", mMSUD);
			return new ModelAndView("redirect:/manifests/"+mMSUD.getId()+"/update");

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
			// get manifest object
			ManifestViewDto viewManifest = manifestService.get(id);

			LOG.debug("viewmanifest loaded. [{}]", viewManifest);
			
			// update
			String repoBasePath = Config.getProperty(Config.REPO_BASE_PATH);
			File file = new File(repoBasePath, viewManifest.getRelFilePathName());
			InputStream in = new FileInputStream(file);
			
			OutputStream out = response.getOutputStream();
			StreamUtil.copy(in, out);
		
		} catch (Exception e) {
			LOG.error("Error occurred while saving manifest", e);
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
	public String view(Model model,ModelMap map, HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
			@RequestParam(required = false) String term,
			@RequestParam(required = false) String sort
			,@ModelAttribute("termsearch") String termsearch,
			@ModelAttribute("message") String message,
		    @ModelAttribute("messageType") String messageType) throws Exception {
		if(termsearch!= null){
		    term=termsearch; 
		    System.out.println("termsearch"+termsearch);
		}
		
		List<ManifestViewDto> manifestDtos = manifestService.findBy(term, page, size, sort);
		model.addAttribute("seamanifestCmd", new Manifest());
		map.addAttribute("message", message);
		map.addAttribute("messageType", messageType);
		model.addAttribute("manifests", manifestDtos);
		return "manifests";
	}
	

	/**
	 * 
	 * @param manifestNumber
	 * @param session
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/delete/{manifestNumber}")
	public String deleteManifest(@PathVariable Long manifestNumber, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			String page = "newForm";
			manifestService.delete(manifestNumber);
			redirectAttributes.addFlashAttribute("messageType","SUCCESS");
			redirectAttributes.addFlashAttribute("message","Manifest [" + manifestNumber + "] successfully deleted");
			return "redirect:/manifests/";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("messageType","FAILED");
			redirectAttributes.addFlashAttribute("message","Failed to delete manifet [" + manifestNumber + "]");
			return "redirect:/manifests/";
		}
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/multiManifests")
    public String inputProduct(Model model) {
        return "manifest-multi";
    }
	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(Model model, HttpSession session,
			RedirectAttributes redirectAttributes, @ModelAttribute("manifest") ManifestSaveUpdateDto manifest)
					throws Exception {
		try {
			
			String searchterm= manifest.getManifestNumber();
			redirectAttributes.addFlashAttribute("termsearch", searchterm);
			return("redirect:/manifests/") ;
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "failed");
			return ("redirect:/manifests/");
		}
	}
}
