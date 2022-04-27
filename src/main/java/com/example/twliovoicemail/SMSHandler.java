package com.example.twliovoicemail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.io.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
public class SMSHandler {

	private final String TWILIO_NUMBER = System.getenv("TWILIO_NUMBER");
	private final String MY_CELLPHONE_NUMBER = System.getenv("MY_CELLPHONE_NUMBER");
	
	
	  @GetMapping(value = "/sendSMS")
    /**
     * A method that send a text to a real phone from my twilio number
     * 
     * It also print to a file the number of twilio and the traget number if a messeage is successful
     * 
     * change void to ResponseEntity<String> to show message in a new browser tab.
     * public ResponseEntity<String> sendSMS() throws FileNotFoundException
     * @return
     * @throws FileNotFoundException
     */
	  public void sendSMS() throws FileNotFoundException {

		  try {
        String c = "\nA message has been made from: "+ TWILIO_NUMBER + " to " + MY_CELLPHONE_NUMBER;
        String s = "\n------------------------------------------";
        File file = new File("./twlio-voicemail/src/main/resources/static/text.html");
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(c);
        bw.write(s);
        bw.close();
  
          
      } catch (Exception e) {
        e.printStackTrace();
      }
			
          Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
         
          
          Message.creator(new PhoneNumber(MY_CELLPHONE_NUMBER),
                          new PhoneNumber(TWILIO_NUMBER), "Hello, How are you doing?").create();

          //stream.println("A message has been made from: "+ TWILIO_NUMBER + " to " + MY_CELLPHONE_NUMBER);
          //stream.println("----------------------------------------------");
          //return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
  }
	  
}
