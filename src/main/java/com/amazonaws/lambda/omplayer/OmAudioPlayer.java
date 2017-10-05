package com.amazonaws.lambda.omplayer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioItem;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioPlayer;
import com.amazon.speech.speechlet.interfaces.audioplayer.PlayBehavior;
import com.amazon.speech.speechlet.interfaces.audioplayer.Stream;
import com.amazon.speech.speechlet.interfaces.audioplayer.directive.PlayDirective;
import com.amazon.speech.speechlet.interfaces.audioplayer.directive.StopDirective;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackFailedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackFinishedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackNearlyFinishedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackStartedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackStoppedRequest;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

public class OmAudioPlayer implements AudioPlayer, Speechlet {
	private static final Logger log = LoggerFactory.getLogger(OmAudioPlayer.class);

	@Override
	public SpeechletResponse onPlaybackFailed(SpeechletRequestEnvelope<PlaybackFailedRequest> requestEnvelope) {
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackFinished(SpeechletRequestEnvelope<PlaybackFinishedRequest> requestEnvelope) {
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackNearlyFinished(
			SpeechletRequestEnvelope<PlaybackNearlyFinishedRequest> requestEnvelope) {
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackStarted(SpeechletRequestEnvelope<PlaybackStartedRequest> requestEnvelope) {
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackStopped(SpeechletRequestEnvelope<PlaybackStoppedRequest> requestEnvelope) {
		return null;
	}

	@Override
	public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {

	}

	@Override
	public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
		return audioPlay();
	}

	@Override
	public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
		Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;
        
        if ("PlayIntent".equals(intentName)) {
        		log.info("PlayIntent called");
        		return audioPlay();
        }else if ("AMAZON.HelpIntent".equals(intentName)) {
        		PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("Welcome to My Meditation. This would play a serene musical meditation Owm chants in the background with a positive aural space. Music credit: IndiaWAV");
            SimpleCard card = new SimpleCard();
            card.setTitle("My Meditation");
            card.setContent("Welcome to My Meditation. This would play a serene musical meditation Owm chants in the background with a positive aural space. Music credit: IndiaWAV (https://indiawav.com");
            return SpeechletResponse.newTellResponse(outputSpeech, card);
        }else if ("AMAZON.StopIntent".equals(intentName) || "AMAZON.CancelIntent".equals(intentName)) {
        		log.info("Stop or cancel Intent requested");
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("Goodbye. See you real soon");
            SimpleCard card = new SimpleCard();
            card.setTitle("My Meditation");
            card.setContent("Good Bye !!");
            
            StopDirective sd = new StopDirective();
            List<Directive> list = new ArrayList<>();
    			list.add(sd);
    			SpeechletResponse sr= SpeechletResponse.newTellResponse(outputSpeech, card);
    			sr.setDirectives(list);
    			return sr;

        }else {
            throw new SpeechletException("Invalid Intent");
        }
	}
	
	private SpeechletResponse audioPlay() {
		Stream s = new Stream();
		s.setUrl("https://d1z3wi4yj1che.cloudfront.net/Om-Meditation.mp3");
		//s.setUrl("https://archive.org/download/OmChanting/om2.wav_64kb.mp3");
		//s.setUrl("https://feeds.soundcloud.com/stream/309340878-user-652822799-episode-010-building-an-alexa-skill-with-flask-ask-with-john-wheeler.mp3");
		s.setToken("0");
		s.setOffsetInMilliseconds(0);
		s.setExpectedPreviousToken(null);
		AudioItem a = new AudioItem();
		a.setStream(s);
		PlayDirective pd = new PlayDirective();
		pd.setAudioItem(a);
		pd.setPlayBehavior(PlayBehavior.REPLACE_ALL);
		List<Directive> list = new ArrayList<>();
		list.add(pd);
		SpeechletResponse sr = new SpeechletResponse();
		SimpleCard card = new SimpleCard();
        card.setTitle("My Meditation");
        card.setContent("Start focusing on the music !!");
        sr.setCard(card);
		sr.setDirectives(list);
		sr.setShouldEndSession(true);
		log.info(sr.toString());
		return sr;
	}

	@Override
	public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
		// TODO Auto-generated method stub

	}

}
