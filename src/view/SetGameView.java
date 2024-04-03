package view;

import model.GameModel;
import model.UserModel;
import util.AudioManager;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.GameController;

/**
 * La classe SetGameView rappresenta la vista per la selezione del numero di giocatori.
 */
public class SetGameView extends JPanel {
	
    private static final long serialVersionUID = 1L;
    private int playersNumber;
    private UserModel user;
    private AudioManager audioManager = AudioManager.getInstance();
    private JSlider volumeSlider;
    private JButton playButton;
    private JButton stopButton;

    /**
     * Crea una nuova istanza di SetGameView.
     *
     * @param user L'utente corrente.
     */
    public SetGameView(UserModel user) {
        this.user = user;
        setLayout(new GridLayout(4, 2));

        JButton twoPlayersButton = new JButton("Due giocatori");
        JButton threePlayersButton = new JButton("Tre giocatori");
        JButton fourPlayersButton = new JButton("Quattro giocatori");
        
        add(twoPlayersButton);
        add(threePlayersButton);
        add(fourPlayersButton);

        twoPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playersNumber = 2;
                createGamePanel();
            }
        });
        
        threePlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playersNumber = 3;
                createGamePanel();
            }
        });
        
        fourPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playersNumber = 4;
                createGamePanel();
            }
        });
        
        audioManager.playBackgroundMusic();
        
        JPanel audio = new JPanel();
        audio.setLayout(new GridLayout(0,2));
        
        // Aggiungi il pulsante di riproduzione
        playButton = new JButton("Avvia audio");
        audio.add(playButton);

        // Aggiungi il pulsante di interruzione
        stopButton = new JButton("Interrompi audio");
        audio.add(stopButton);

        // Aggiungi uno slider per regolare il volume
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(1);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        audio.add(volumeSlider);
        add(audio);
        // Aggiungi azioni per i pulsanti
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioManager.playBackgroundMusic();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	audioManager.stopBackgroundMusic();
            }
        });

	     // Aggiungi azione per lo slider di volume
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int volume = volumeSlider.getValue();
                float volumeFloat = volume / 100.0f; // Converti il valore dello slider in un valore float tra 0.0 e 1.0

                // Ottieni l'AudioManager
                AudioManager audioManager = AudioManager.getInstance();
                // Ottieni il Clip dell'audio di background
                Clip backgroundMusic = audioManager.getBackgroundMusic();

                if (backgroundMusic != null) {
                    // Ottieni il controllo del volume
                    FloatControl volumeControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);

                    // Calcola il gain (volume) in base al valore dello slider
                    float min = volumeControl.getMinimum();
                    float max = volumeControl.getMaximum();
                    float range = max - min;
                    float gain = (range * volumeFloat) + min;

                    // Imposta il volume del Clip
                    volumeControl.setValue(gain);
                }
            }
        });
    }

    /**
     * Crea il pannello del gioco all'interno di una nuova finestra.
     */
    private void createGamePanel() {
        JFrame gameFrame = new JFrame("JTrash Game");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setSize(800, 600);
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
        
        GameController controller = new GameController();
        
        GameModel game = new GameModel(playersNumber, user, controller);
        
        controller.setGameModel(game);

        GameView gameView = new GameView(playersNumber, user, controller);
        
        controller.setGameView(gameView);
        
        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stopGame();
                gameFrame.dispose();
            }
        });
        
        gameFrame.add(gameView);
        gameFrame.setVisible(true);
    }
}