package com.maxminmajcdg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

		@GetMapping("/home")
		public String home() {
			return "home";
		}
		
		@GetMapping("/leaflet")
		public String leaflet() {
			return "leaflet";
		}
}
