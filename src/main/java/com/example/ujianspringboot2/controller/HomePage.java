package com.example.ujianspringboot2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.ujianspringboot2.entity.UjianSpringBoot2Model;
import com.example.ujianspringboot2.interfaces.UjianSpringBoot2Repository;
import com.example.ujianspringboot2.util.FileUploadUtil;

@Controller
public class HomePage {
	
	@Autowired
	UjianSpringBoot2Repository ujianSpringBoot2Repository;

	@GetMapping("/")
	public String viewIndex(Model model) {
		model.addAttribute("UjianSpringBoot2Model", new UjianSpringBoot2Model());
		return "index.html";
	}
	
	@PostMapping("/addCV")
	public String addCV(@RequestParam("fullname") String fullname, @RequestParam("email") String email,
			@RequestParam("platform") String platform, @RequestParam("cv") MultipartFile file, Model model) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		UjianSpringBoot2Model ujianSpringBoot2Model  = new UjianSpringBoot2Model(0, fullname, email, platform, fileName);
		ujianSpringBoot2Model.setCv(fileName);
		this.ujianSpringBoot2Repository.save(ujianSpringBoot2Model);

		String uploadDir = "C:/cvupload/" + fileName;
		try {
			FileUploadUtil.saveFile(uploadDir, fileName, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

}
