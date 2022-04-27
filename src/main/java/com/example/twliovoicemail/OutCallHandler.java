package com.example.twliovoicemail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.CallCreator;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Number;

@RestController
public class OutCallHandler {

	private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
	private static final String TWILIO_NUMBER = System.getenv("TWILIO_NUMBER");
	private static final String MY_CELLPHONE_NUMBER = System.getenv("MY_CELLPHONE_NUMBER");
	
	@GetMapping(value = "/Call")
	public  void makeCall() throws URISyntaxException, FileNotFoundException {
		try {
			String c = "\nCall has been made from: "+ TWILIO_NUMBER + " to " + MY_CELLPHONE_NUMBER;
			String s = "\n------------------------------------------";
			File file = new File("./twlio-voicemail/src/main/resources/static/CallsOut.html");
			//PrintStream stream = new PrintStream(file);
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(c);
			bw.write(s);
			bw.close();

				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		 String from = TWILIO_NUMBER;
	     String to = MY_CELLPHONE_NUMBER;
	     
	     Call call = Call.creator(new PhoneNumber(to),new PhoneNumber(from),
	        		URI.create("https://demo.twilio.com/welcome/voice/")).create();
	    
	   
		//stream.println("----------------------------------------------");
	    // return new ResponseEntity<String>("A call has been made successfully", HttpStatus.OK);
	     
	     
	}
}// end of OutCallHandler
