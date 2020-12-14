package com.zuckonit;

import javax.swing.*;
import java.awt.*;

public class LoginSuccess extends JFrame {

    public LoginSuccess(){
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setTitle("Login Success");
        setLayout(new GridLayout(1, 1));
        JLabel label = new JLabel("Login Success");
        add(label);
        this.setSize(300,300);
        this.setVisible(true);
    }
}
