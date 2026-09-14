package com.business.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.business.entities.Product;
import com.business.loginCredentials.AdminLogin;
import com.business.entities.Admin;
import com.business.entities.User;
import com.business.services.AdminServices;
import com.business.services.ProductServices;
import com.business.services.UserServices;

@Controller
public class HomeController 
{
	@Autowired
	private ProductServices productServices;
	@Autowired
	private UserServices userServices;
	@Autowired
	private AdminServices adminServices;
	@GetMapping("/")
	public String root()
	{
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String home()
	{
		return "Home";
	}

	@GetMapping("/products")
	public String products( Model model)
	{ 
		List<Product> allProducts = this.productServices.getAllProducts();
		model.addAttribute("products", allProducts);
		return "Products";
	}

	@GetMapping("/location")
	public String location()
	{
		return "Locate_us";
	}

	@GetMapping("/about")
	public String about()
	{
		return "About";
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "registered", required = false) String registered, Model model)
	{
		model.addAttribute("adminLogin",new AdminLogin());
		if (registered != null)
		{
			model.addAttribute("registrationMessage", "Your " + registered + " account was created. You can sign in now.");
		}
		return "Login";
	}

	@GetMapping({"/register", "/register.html"})
	public String registrationPage()
	{
		return "Registration";
	}

	@PostMapping("/register/user")
	public String registerUser(@ModelAttribute User user, @RequestParam("passwordConfirm") String passwordConfirm, Model model)
	{
		user.setUemail(user.getUemail().trim().toLowerCase());
		if (!user.getUpassword().equals(passwordConfirm))
		{
			model.addAttribute("userError", "Passwords do not match.");
			return "Registration";
		}
		if (userServices.getUserByEmail(user.getUemail()) != null)
		{
			model.addAttribute("userError", "An account with this email already exists.");
			return "Registration";
		}
		userServices.addUser(user);
		return "redirect:/login?registered=user";
	}

	@PostMapping("/register/admin")
	public String registerAdmin(@ModelAttribute Admin admin, @RequestParam("passwordConfirm") String passwordConfirm, Model model)
	{
		admin.setAdminEmail(admin.getAdminEmail().trim().toLowerCase());
		if (!admin.getAdminPassword().equals(passwordConfirm))
		{
			model.addAttribute("adminError", "Passwords do not match.");
			return "Registration";
		}
		if (adminServices.getAll().stream().anyMatch(existing -> existing.getAdminEmail().equalsIgnoreCase(admin.getAdminEmail())))
		{
			model.addAttribute("adminError", "An admin account with this email already exists.");
			return "Registration";
		}
		adminServices.addAdmin(admin);
		return "redirect:/login?registered=admin";
	}
}
