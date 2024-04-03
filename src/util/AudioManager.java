package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * La classe AudioManager gestisce la riproduzione degli effetti sonori del gioco.
 */
public class AudioManager {
    private static AudioManager instance;
    private Clip backgroundMusic;

    private AudioManager() {
        // Inizializza l'AudioManager e carica l'audio di background.
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("audio/background_music.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ottiene l'istanza dell'AudioManager.
     * @return L'istanza dell'AudioManager.
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * Avvia la riproduzione dell'audio di background.
     */
    public void playBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isRunning()) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Ferma la riproduzione dell'audio di background.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }
    
    /**
     * Metodo getter per ottenere l'audio di background.
     * @return backgroundMusic
     */
    public Clip getBackgroundMusic() {
    	return backgroundMusic;
    }
    
}
