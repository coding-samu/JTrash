package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import model.UserModel;

/**
 * La classe AvatarView rappresenta la vista dell'avatar di un utente.
 */
public class AvatarView extends JPanel {
    private static final long serialVersionUID = 1L;
    private final UserModel user;

    /**
     * Crea una nuova istanza di AvatarView associata a un utente specifico.
     *
     * @param user L'utente associato a questa vista dell'avatar.
     */
    public AvatarView(UserModel user) {
        this.user = user;
        setPreferredSize(new Dimension(100, 100)); // Imposta le dimensioni del componente
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Disegna l'immagine dell'avatar
        Image avatarImage = Toolkit.getDefaultToolkit().getImage(user.getAvatar());
        g.drawImage(avatarImage, 0, 0, 100, 100, this);
    }
}
