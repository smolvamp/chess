package main;


import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(1000,1000));
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(0, 0, 0));
        frame.setLocationRelativeTo(null);

        Board board= new Board();
        frame.add(board);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

