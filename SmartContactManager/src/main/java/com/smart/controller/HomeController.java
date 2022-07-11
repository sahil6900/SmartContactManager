package com.smart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.repository.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String home(Model model) {
		
		model.addAttribute("title", "Home-Smart Contact Manager");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About-Smart Contact Manager");
		
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register-Smart Contact Manager");
		model.addAttribute("user", new User());
		
		return "signup";
	}
	
	
	@RequestMapping(value = "/do_register",method = RequestMethod.POST)
	public String userRegister(@ModelAttribute("user") User user,@RequestParam(value = "agreement",defaultValue = "false") boolean agreement,Model model,HttpSession session) {
		
		try {
			if(!agreement) {
				System.out.println("Terms and conditions are not checked");
				throw new Exception("Terms and conditions are not checked");
			
			}
			
			user.setRole("User_Role");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			System.out.println("Agreement"+agreement);
			System.out.println("user"+user);
			
			User result = this.userRepository.save(user);
			model.addAttribute("user", new User());
			model.addAttribute("message",new Message("Registered Successfully", "alert-success"));
			return "signup";
		} catch (Exception e) {
			session.setAttribute("message",new Message("Registration failed something went wrong", "alert-danger"));
			e.printStackTrace();
			return "signup";
		}
		
	}
}
