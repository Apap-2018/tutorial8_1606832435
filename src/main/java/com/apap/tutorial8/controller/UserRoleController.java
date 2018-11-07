package com.apap.tutorial8.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	private String changeUserPassword(Principal principal, 
									@RequestParam("password") String password, 
									@RequestParam("oldpassword") String oldPassword,
									@RequestParam("passConfirm") String confirmPass,
									Model model) {
		String username = principal.getName();
		
		if (!userService.checkPassword(username, oldPassword)) {
			model.addAttribute("updateResponse", "Password Salah!");
		} else if (!password.equalsIgnoreCase(confirmPass)) {
			model.addAttribute("updateResponse", "Password Baru dan Konfirmasinya Harus Sama!");
		} else {
			model.addAttribute("updateResponse", "Password berhasil diubah");
			userService.updatePassword(username, password);
		}

		return "home";
	}
}
