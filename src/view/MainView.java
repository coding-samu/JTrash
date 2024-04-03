package view;

import model.UserModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * La classe MainView rappresenta la vista principale dell'applicazione.
 */
public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;
    private UserProfileView userProfileView;
    private SetGameView setGameView;
    private JButton profileButton;
    private JButton gameButton;

    /**
     * Crea una nuova istanza di MainView.
     */
    public MainView() {
        setTitle("JTrash Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Inizializza il pannello di profilo e di gioco
        UserModel userDefault = new UserModel("Umano");
        userProfileView = new UserProfileView(userDefault);
        setGameView = new SetGameView(userDefault);

        // Aggiungi pulsanti per il profilo e il gioco
        profileButton = new JButton("Profilo");
        gameButton = new JButton("Partita");

        // Ascoltatori per i pulsanti
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userProfileView.updateLabels();
                showUserProfile();
            }
        });

        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGameSetter();
            }
        });

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(profileButton);
        buttonPanel.add(gameButton);

        // Aggiungi i pannelli alla finestra principale
        add(buttonPanel, BorderLayout.NORTH);
        add(setGameView, BorderLayout.CENTER);
    }
    
    /**
     * Mostra il JPanel con le informazioni sul profilo del giocatore.
     */
    private void showUserProfile() {
        add(userProfileView, BorderLayout.CENTER);
        setGameView.setVisible(false);
        userProfileView.setVisible(true);
    }
    
    /**
     * Mostra il JPanel con i bottoni per avviare il gioco.
     */
    private void showGameSetter() {
        add(setGameView, BorderLayout.CENTER);
        userProfileView.setVisible(false);
        setGameView.setVisible(true);
    }
}
