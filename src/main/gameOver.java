package main;

import javax.swing.*;
import java.io.IOException;

public class gameOver {

    public gameOver(String resultString) throws InterruptedException, IOException {




        // Create a frame (main window)
        JFrame frame = new JFrame("GAME OVER!!!");

        // Set default behavior on close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Show an Information dialog (pop-up)
        JOptionPane.showMessageDialog(frame, resultString,
                "GAME OVER!!!", JOptionPane.INFORMATION_MESSAGE);



        // Show a Confirmation dialog (Yes/No)
        int response = JOptionPane.showConfirmDialog(frame,
                "Do you want to Restart?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            ProcessBuilder pb = new ProcessBuilder("java","src/main/Main.java");
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } else {
            JOptionPane.showMessageDialog(frame, "You chose No!");
        }

        // Show an Error dialog
        /*
        JOptionPane.showMessageDialog(frame, "An error occurred!",
                "Error", JOptionPane.ERROR_MESSAGE);
        */
        // Make the frame visible
        //frame.setVisible(true);
    }
}
