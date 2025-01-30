import Authentification.Conn;
import Authentification.HomeScreen;
import Authentification.Login;
import iStore.iStore;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeScreen homeScreen = new HomeScreen();
            homeScreen.setVisible(true);
        });

        //iStore iStore = new iStore();

    }
}