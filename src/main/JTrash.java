package main;

import view.MainView;

/**
 * La classe JTrash rappresenta il punto di ingresso dell'applicazione JTrash.
 */
public class JTrash {
    /**
     * Il metodo main è il punto di ingresso dell'applicazione.
     *
     * @param args Gli argomenti della riga di comando (non utilizzati in questo caso).
     */
    public static void main(String[] args) {
        // Crea la finestra principale dell'applicazione
        MainView mainView = new MainView();
        mainView.setVisible(true);
    }
}
