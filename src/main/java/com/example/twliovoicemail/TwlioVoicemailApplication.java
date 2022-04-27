package com.example.twliovoicemail;



import java.awt.GridLayout;

import javax.swing.JFrame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@SpringBootApplication
public class TwlioVoicemailApplication {
	
	public static void main(String[] args) {
		 SpringApplication.run(TwlioVoicemailApplication.class, args);
		
		
	}
	
}
