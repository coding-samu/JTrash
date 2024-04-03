package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.UserModel;

/**
 * La classe PlayerInfoView rappresenta la vista delle informazioni del giocatore.
 */
public class PlayerInfoView extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Crea una nuova istanza di PlayerInfoView associata a un utente specifico.
     *
     * @param user L'utente associato a questa vista delle informazioni del giocatore.
     */
    public PlayerInfoView(UserModel user) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(80, 160)); // Imposta le dimensioni del componente
        setBackground(new Color(128, 128, 0));
        AvatarView avatarView = new AvatarView(user);
        avatarView.setPreferredSize(new Dimension(80, 80));
        avatarView.setBackground(new Color(153, 153, 255));

        // Etichetta con il nome del giocatore
        JLabel playerNameLabel = new JLabel(user.getNickname());
        playerNameLabel.setBackground(new Color(153, 153, 255));
        playerNameLabel.setOpaque(true);
        playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerNameLabel.setPreferredSize(new Dimension(80, 40));

        // Etichetta con il livello del giocatore
        JLabel playerLevelLabel = new JLabel("Livello: " + user.getLevel());
        playerLevelLabel.setBackground(new Color(153, 153, 255));
        playerLevelLabel.setOpaque(true);
        playerLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerLevelLabel.setPreferredSize(new Dimension(80, 40));

        add(avatarView, BorderLayout.CENTER);
        add(playerNameLabel, BorderLayout.NORTH);
        add(playerLevelLabel, BorderLayout.SOUTH);
    }
}
