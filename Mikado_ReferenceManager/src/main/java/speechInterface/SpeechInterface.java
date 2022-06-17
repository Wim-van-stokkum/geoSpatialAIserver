package speechInterface;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.microsoft.cognitiveservices.speech.AudioDataStream;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

public class SpeechInterface {
	private SpeechConfig speechConfig;
	private AudioConfig audioConfig;
	private SpeechSynthesizer synthesizer;
	private Port mySpeaker;
	


	// sleutel 1 5d2fe936bb684cdf9f6e4ff3911c392e
	// sleutel 2 6b364d061bcd464bb4365edd8aef6664
	// end point https://planspacespeech.cognitiveservices.azure.com/
	// locatie westeurope

	private static SpeechInterface stdSpeechInterface;

	public static SpeechInterface getInstance() {
		if (stdSpeechInterface == null) {
			// Create Loghandler
			stdSpeechInterface = new SpeechInterface();
			stdSpeechInterface.connectSpeech();

		}

		return stdSpeechInterface;
	}

	private void initSpeaker() throws LineUnavailableException {
		// specifying the audio format
		AudioFormat _format = new AudioFormat(8000.F, // Sample Rate
				16, // Size of SampleBits
				1, // Number of Channels
				true, // Is Signed?
				false // Is Big Endian?
		);

		if (AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
			try {
				mySpeaker = (Port) AudioSystem.getLine(Port.Info.SPEAKER);
			} finally {

			}
		}

		// creating the DataLine Info for the speaker format
		DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, _format);

		// getting the mixer for the speaker

		mySpeaker.open();
	}

	public void connectSpeech() {

		this.speechConfig = SpeechConfig.fromSubscription("5d2fe936bb684cdf9f6e4ff3911c392e", "westeurope");
		speechConfig.setSpeechRecognitionLanguage("nl-NL");

		/* Speak to file */
		audioConfig = AudioConfig.fromWavFileOutput("C:\\Users\\wvans\\Documents\\planspace.wav");

		audioConfig = AudioConfig.fromWavFileOutput("C:\\Users\\wvans\\Documents\\planspace.wav");
		synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);

		/* Speak to speaker */
		// synthesizer = new SpeechSynthesizer(speechConfig, null);

		try {
			initSpeaker();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String listenToMic() {
		String theResult;

		AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
		SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);

		System.out.println("Wat wil je nu eigenlijk precies zeggen...zeg maar\n\n");
		Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
		SpeechRecognitionResult result;
		theResult = "";
		try {
			result = task.get();
			theResult = result.getText();
			System.out.println("RECOGNIZED: Text=" + theResult);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		recognizer.close();

		return theResult;

	}

	public void speakOut(String aTextToSpeakout) {
		String ssml;

		/* Speak to file */
		// synthesizer.SpeakText(aTextToSpeakout);

		// String ssml = xmlToString("ssml.xml");

		ssml = "<speak version=\"1.0\" xmlns=\"https://www.w3.org/2001/10/synthesis\" xml:lang=\"en-US\">\n" +
		// " <voice name=\"en-GB-George-Apollo\">\n" +
				" <voice name=\"nl-NL-FennaNeural\">\n" + aTextToSpeakout + "\n" + "  </voice>\n" + "</speak>";

		ssml.trim();

		SpeechSynthesisResult result = synthesizer.SpeakSsml(ssml);
		AudioDataStream stream = AudioDataStream.fromResult(result);
		stream.saveToWavFile("C:/Users/wvans/Documents/planspace2.wav");

		sound();

	}

	public void sound() {

		// nl-NL-ColetteNeural
		// nl-NL-MaartenNeural

		String pad;
		Long duration;

		pad = "C:/Users/wvans/Documents/planspace2.wav";

		// You could also get the sound file with a URL
		File soundFile = new File(pad);
		try ( // Open an audio input stream.
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

				// Get a sound clip resource.
				Clip clip = AudioSystem.getClip()) {

			// Open audio clip and load samples from the audio input stream.
			System.out.println("now playing");

			clip.open(audioIn);
			duration = clip.getMicrosecondLength();
			System.out.println("now playing: " + duration);
			clip.start();
			try {
				Thread.sleep(duration / 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	
}
