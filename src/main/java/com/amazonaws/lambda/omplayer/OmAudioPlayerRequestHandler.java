package com.amazonaws.lambda.omplayer;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class OmAudioPlayerRequestHandler extends SpeechletRequestStreamHandler {
	private static final Set<String> supportedApplicationIds;

	static {
		/*
		 * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit"
		 * the relevant Alexa Skill and put the relevant Application Ids in this Set.
		 */
		supportedApplicationIds = new HashSet<>();
		supportedApplicationIds.add("amzn1.ask.skill.c19d44cc-56ae-4e63-835a-7c148f8b33dd");
	}
	
	public OmAudioPlayerRequestHandler(Speechlet speechlet, Set<String> supportedApplicationIds) {
		super(speechlet, supportedApplicationIds);
	}
	
	public OmAudioPlayerRequestHandler(SpeechletV2 speechlet, Set<String> supportedApplicationIds) {
		super(speechlet, supportedApplicationIds);
	}
	public OmAudioPlayerRequestHandler() {
		super(((Speechlet) new OmAudioPlayer()), supportedApplicationIds);
	}

}
