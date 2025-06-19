package src.view;

import javax.swing.*;
import java.awt.*;

public class ProgressBar extends JDialog {

    public ProgressBar(Frame owner) {
        super(owner, "Processing...", true); 
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        setLayout(new BorderLayout(5, 5));
        add(new JLabel("Operation is in progress..."), BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); 
        setSize(440, 140);
        setLocationRelativeTo(owner); 
        setResizable(false);
    }
}

