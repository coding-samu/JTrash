package view;

import model.UserModel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * La classe UserProfileView rappresenta la vista del profilo utente.
 */
public class UserProfileView extends JPanel {
    private static final long serialVersionUID = 1L;
    private UserModel user;
    private JTextField nicknameField;
    private JTextField avatarField;
    private JButton saveButton;
    private JLabel gamesPlayedLabel;
    private JLabel gamesWonLabel;
    private JLabel gamesLostLabel;
    private JLabel levelLabel;
    private AvatarView avatarImage;

    /**
     * Crea una nuova istanza di UserProfileView associata a un utente specifico.
     *
     * @param user L'utente associato a questa vista del profilo utente.
     */
    public UserProfileView(UserModel user) {
        this.user = user;
        setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Nickname:");
        nicknameField = new JTextField(user.getNickname());
        JLabel avatarLabel = new JLabel("Avatar:");
        avatarField = new JTextField(user.getAvatar());
        saveButton = new JButton("Save");

        // Crea le JLabel qui nel costruttore
        gamesPlayedLabel = new JLabel();
        gamesWonLabel = new JLabel();
        gamesLostLabel = new JLabel();
        levelLabel = new JLabel();

        setLabels();

        avatarImage = new AvatarView(user);

        add(nameLabel);
        add(nicknameField);
        add(levelLabel);
        add(avatarLabel);
        add(avatarField);
        add(avatarImage);
        add(gamesPlayedLabel);
        add(gamesWonLabel);
        add(gamesLostLabel);
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUserProfile();
                avatarImage.repaint();
            }
        });
    }

    private void saveUserProfile() {
        String newNickname = nicknameField.getText();
        String newAvatar = avatarField.getText();

        // Verifica che i campi non siano vuoti
        if (newNickname.isEmpty() || newAvatar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nickname e Avatar devono essere compilati", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Aggiorna il profilo utente con i nuovi valori
        user.setNickname(newNickname);
        user.setAvatar(newAvatar);

        // Aggiorna la visualizzazione
        JOptionPane.showMessageDialog(this, "Profilo utente salvato con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Aggiorna i testi delle JLabel.
     */
    public void updateLabels() {
        setLabels(); // Aggiorna i testi delle JLabel
        repaintLabels(); // Ridisegna le JLabel
    }
    
    /**
     * Ristampa le JLabel.
     */
    private void repaintLabels() {
        levelLabel.repaint();
        gamesPlayedLabel.repaint();
        gamesWonLabel.repaint();
        gamesLostLabel.repaint();
    }
    
    /**
     * Imposta le JLabel.
     */
    private void setLabels() {
        gamesPlayedLabel.setText("Numero partite giocate: " + user.getGamesPlayed());
        gamesWonLabel.setText("Numero partite vinte: " + user.getGamesWon());
        gamesLostLabel.setText("Numero partite perse: " + user.getGamesLost());
        levelLabel.setText("Livello: " + user.getLevel());
    }
}
