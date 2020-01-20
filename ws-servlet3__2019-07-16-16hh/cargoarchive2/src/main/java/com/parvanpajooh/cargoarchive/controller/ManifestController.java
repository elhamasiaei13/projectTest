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
import com.parvanpajooh.cargoarchive.model.Manifest;
import com.parvanpajooh.cargoarchive.model.dto.ManifestSaveUpdateDto;
import com.parvanpajooh.cargoarchive.model.dto.ManifestViewDto;
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
	public ModelAndView newManifest(Model model, HttpSession session, RedirectAttributes redirectAttributes) 
	{ // TODO
		model.addAttribute("newManifestCmd", new Manifest());
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
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView saveNew(Model model, @RequestParam("file") CommonsMultipartFile file, HttpSession session,
			RedirectAttributes redirectAttributes, @ModelAttribute("manifest") ManifestSaveUpdateDto wWSUD)
			throws Exception {
		try {
			// save
			manifestService.save(wWSUD, file);
			// findBy

			String term = null;
			Integer page = 0;
			Integer size = 10;
			String sort = null;

			return new ModelAndView("redirect:/manifests/");

		} catch (Exception e) {
			LOG.error("Error occurred while saving manifest", e);
			redirectAttributes.addFlashAttribute("message", "Failed!!!!!");
			return new ModelAndView("redirect:/manifests/");
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
	@RequestMapping(value = "/{manifestNumber}")
	public ModelAndView viewmanifest(@PathVariable long manifestNumber, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		ManifestViewDto viewManifest = manifestService.get(manifestNumber);
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
	@RequestMapping(value = "/{manifestNumber}/update")
	public ModelAndView editForm(@PathVariable long manifestNumber, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		ManifestViewDto editManifest = manifestService.get(manifestNumber);
		model.addAttribute("command", editManifest);
		String page = "newForm";

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
	@RequestMapping(value = "/{manifestNumber}", method = RequestMethod.POST)
	public ModelAndView updateForm(Model model, @RequestParam("file") CommonsMultipartFile file, HttpSession session,
			RedirectAttributes redirectAttributes, @ModelAttribute("manifest") ManifestSaveUpdateDto manifest)
			throws Exception {
		try {
			// update
			manifestService.update(manifest, file);

			return new ModelAndView("redirect:/manifests/");

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Failed!!!!!");
			return new ModelAndView("redirect:/manifests/");
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
	public String view(Model model, HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
			@RequestParam(required = false) String term, @RequestParam(required = false) String sort) throws Exception {

		List<ManifestViewDto> manifestDtos = manifestService.findBy(term, page, size, sort);

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
	public String newManifest(@PathVariable Long manifestNumber, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		String page = "newForm";
		manifestService.delete(manifestNumber);
		return "redirect:/waybills/";
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
	
}
