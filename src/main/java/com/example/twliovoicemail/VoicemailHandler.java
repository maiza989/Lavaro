package com.example.twliovoicemail;



import com.twilio.Twilio;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Hangup;
import com.twilio.twiml.voice.Pause;
import com.twilio.twiml.voice.Record;
import com.twilio.twiml.voice.Say;
import com.twilio.twiml.voice.Play;
import com.twilio.type.PhoneNumber;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
/**
 * A class that contain HTTP endpoints 
 * @author Maitham Alghamgham
 *
 */
@RestController("TwiML")
public class VoicemailHandler {
	
	/**
	 * For send SMS message for the voice mail.
	 */
	  static {
	        Twilio.init(
	            System.getenv("TWILIO_ACCOUNT_SID"),
	            System.getenv("TWILIO_AUTH_TOKEN"));
	    }
	
	/**
	 * Here we are Answering calls and redirecting incoming calls using Twilio service 
	 * 
	 * Inside our class VoicemailHandler A method 'initialAnswer' will handle returning TwiML(Our annotation for RestController)
	 * for answering the calls and redirecting your cell phone.
	 */
	 private final String TWILIO_NUMBER = System.getenv("TWILIO_NUMBER");
	 //private final String MY_CELLPHONE_NUMBER = "+18592471335";
	 private final String MY_CELLPHONE_NUMBER = System.getenv("MY_CELLPHONE_NUMBER");
	 private final int ANSWERPHONE_TIMEOUT_SECONDS = 5;

	 /**
	  * Here we are using @GetMapping annotation to tell SpringBoot ot use this method for HTTP GET request to the /answer endpoint 
	  * @return
	  */
	    @GetMapping(value = "answer", produces = "application/xml")
	    
	    public String initialAnswer(){
	    	/**
	    	 * Here we are building up the TwiML
	    	 */
	    	
	        return new VoiceResponse.Builder()
	                .dial(new Dial.Builder()// dial job is: connect out to another number it takes a number of attributes: A 'Number' to transfer the incoming call to, and 'Timeout' which decide when the call is unanswered.
	                .number(MY_CELLPHONE_NUMBER)
	                .timeout(ANSWERPHONE_TIMEOUT_SECONDS)
	                .action("/handle-unanswered-call")// tell Twilio where to look for the next set of TwiML instruction
	                .method(HttpMethod.GET)// tell Twilio where to look for the next set of TwiML instruction
	                .build())
	                .build().toXml(); //.toXml() method call at the end creates a String of TwiML. 
	    }
	    
	    /**
	     *  annotated method for /handle-unanswered-call
	     * @param dialCallStatus
	     * @return voicemailTwiml ( voicemail message ) 
	     */
	    @GetMapping(value = "/handle-unanswered-call", produces = "application/xml")
	    /**
	     * Here we are checking the statues of the call through voicemailTwiml method if its busy or no answer if not we return null
	     * @param dialCallStatus
	     * @return
	     */
	    public String handleUnansweredCall(@RequestParam("DialCallStatus") String dialCallStatus){

	        if ("busy".equals(dialCallStatus) || "no-answer".equals(dialCallStatus)){
	            return voicemailTwiml();
	        }

	        return null;
	    }
	    
	    
	    /**
	     * Give a message to the caller and start the recording.
	     * @return
	     */
	    
	    private String voicemailTwiml() 
	    {
	    	
	        return new VoiceResponse.Builder()
	        		
	            .pause(new Pause.Builder().length(2).build())
	            /**
	             * could also use a simple string instead of a recording your own voice using "Say" command from Twillio API like the following to replace line 80
	             * .say(new Say.Builder("Sorry I can't take your call at the moment, please leave a message").build())
	             */
	            .say(new Say.Builder("Sorry I can't take your call at the moment, please leave a message").build())
	            //.play(new Play.Builder("/message.m3").build())
	            .record(new Record.Builder()
	                .playBeep(true)
	                .maxLength(10)
	                .action("/recordings")
	                .build())
	                .build().toXml();
	    }
	    
	    /**
	     * Here we are handling recordings. We have an endpoint "/recordings" is used/called after someone lefts a voice mail and send and SMS with a link of the recording.
	     * @param requestUrl   uses "RecordingUrl" to download the recording in .mp3 format.
	     * @param MY_CELLPHONE_NUMBER uses "From" which gets the caller number
	     * @param TWILIO_NUMBER uses "To" goes to my twilio number
	     * @throws FileNotFoundException 
	     * 
	     */
	    @PostMapping("/recordings")
	    public void handleRecording(
	    		
	        @RequestParam("RecordingUrl") String requestUrl,
	        @RequestParam("From") String callerNumber,
	        @RequestParam("To") String TWILIO_NUMBER) throws FileNotFoundException{
	    	
	    	

	        String mp3RecordingUrl = requestUrl + ".mp3";

	        String smsNotification = String.format("You got a voicemail message from %s - listen here: %s", callerNumber, mp3RecordingUrl);

	        Message.creator(
	            new PhoneNumber(MY_CELLPHONE_NUMBER),
	            new PhoneNumber(TWILIO_NUMBER),
	            smsNotification)
	            .create();
	        
	        try {
				try {
					String c = "\n" +smsNotification;
					String s = "\n------------------------------------------";
					File file = new File("./twlio-voicemail/src/main/resources/static/voicemail.html");
					FileWriter fw = new FileWriter(file, true);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(c);
					bw.write(s);
					bw.close();
			  
					  
				  } catch (Exception e) {
					  System.out.println("File does not exist");
					e.printStackTrace();
				  }
			} catch (Exception e) {
				//TODO: handle exception
			}
	        			
	        
	    }// end of handleRecording
	    
	    
}// end of VoiceHandler class
