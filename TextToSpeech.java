import com.sun.speech.freetts.*;

public class TextToSpeech {
    static Voice voice;

    public static void speak(String text) {
        if (voice == null) {
            voice = VoiceManager.getInstance().getVoice("kevin16");
            if (voice != null) {
                voice.allocate();
            } else {
                System.out.println("Voice not found!");
                return;
            }
        }
        voice.speak(text);
    }
}
