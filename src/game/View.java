package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static game.Constants.window_SIZE;

/**
 * Created by Jack on 15/11/2016.
 */
public class View {

    private JFrame frame;
    private Canvas canvas;
    private JPanel panel;

    private String title;
    private int width, height;

    public View(String title, int width, int height) {

        this.title = title;
        this.width = width;
        this.height = height;


        createFrame();
    }

    private void createFrame() {

        frame = new JFrame(title);
        panel = new JPanel();

        getPanel().setPreferredSize(window_SIZE);
        getPanel().setMaximumSize(window_SIZE);
        getPanel().setMinimumSize(window_SIZE);
        frame.setSize(width, height);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);


        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        canvas = new Canvas();
        getCanvas().setPreferredSize(window_SIZE);
        getCanvas().setMaximumSize(window_SIZE);
        getCanvas().setMinimumSize(window_SIZE);
        getCanvas().setFocusable(false);



        canvas.setVisible(true);
        getPanel().setVisible(false);
        frame.add(getCanvas());


        frame.pack();
    }


    public JFrame getFrame(){
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JPanel getPanel() {
        return panel;
    }
}
