package com.example.twliovoicemail;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Pause;
import com.twilio.twiml.voice.Say;

/**
 * This calls handle incoming calls. It leaves a simple message to the use 
 * @author maith
 *
 */
@RestController
public class Error {

	@PostMapping(value = "/error", produces = "application/xml")
	@ResponseBody
	public String handleIncomingCallError(){
	    return new VoiceResponse.Builder()
	    		.pause(new Pause.Builder().length(2).build())
	            .say(new Say.Builder("An error has occured during the call, please call again. GOODBYE").build())
	            .build()
	            .toXml();
	}

}
