package com.PhonebookPrototype.firstprototype;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class ControllerLogin {
//	@RequestMapping(path = "main/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public static String main(@PathVariable("id") long id) {
//		return "redirect:/main/";
//	}
	
	@RequestMapping(path = "main", method = RequestMethod.GET)
	public String main() {
		return "index";
	}
	
	@RequestMapping(path = "dashboard", params={"user"}, method = RequestMethod.GET)
	public String dashboard(@RequestParam("user") long uid) {
//		System.out.println("@Controllers");
//		System.out.println(uid);
		return "dashboard";
	}
	
}
