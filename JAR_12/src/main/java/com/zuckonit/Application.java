package com.zuckonit;

import sun.security.util.Password;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Application extends JFrame {


    private JLabel label;
    JLabel label1 ;
    TextField login ;
    JLabel label2 ;
    JPasswordField passwordField;



    Application() {
        this.initUI();
    }

    private String readOuterTextFile(String path) {
        String data = "";
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine()+"\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    private boolean login(String login, String pass){
        boolean result = false;
        String config = readOuterTextFile("config.txt");
        String[] parseConfig = config.split("\n");
        String port = "";
        String dbName = "";
        String user = "";
        String password = "";
        //для каждой строки методом парс находим ключ значение
        for(int i=0; i<parseConfig.length; i++){
            String[] val = parseConfig[i].split(":");

            //проверяем по ключам и заполняем значения
            switch (val[0]){
                case "port": port = val[1]; break;
                case "user": user = val[1]; break;
                case "dbName": dbName = val[1]; break;
                case "password": password = val[1]; break;
                default: break;
            }
        }

        //формируем строку подключения.
        try{
            String url2 = "jdbc:mysql://127.0.0.1:3307/library";
            String url = "jdbc:mysql://localhost:"+port+"/"+dbName;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(url,user,password);
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from users");
            while(rs.next())
                if(rs.getString(4).equals(login) && rs.getString(5).equals(pass))
                    result = true;
            con.close();
        }catch(Exception e){ e.printStackTrace();}

        return result;
    }

    private void initUI() {

        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setTitle("Login Window");
        setLayout(new GridLayout(4, 1));

        label1 = new JLabel("Login");
        login = new TextField();
        label2 = new JLabel("Password");
        passwordField = new JPasswordField();

        //label.setText(readFromDB());
        label = new JLabel();
        label.setText("Error login or password");
        label.setVisible(false);

        setSize(400, 400);

        JButton btn = new JButton("Login");
        btn.addActionListener(new ListenerAction());
        add(label1);
        add(login);
        add(label2);
        add(passwordField);
        add(label);
        add(btn);
        setVisible(true);
    }

    class ListenerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(login.getText());
            System.out.println(passwordField.getText());
            if(login(login.getText(),passwordField.getText())) {
                LoginSuccess ls;
                ls = new LoginSuccess();
            }
            else
                label.setVisible(true);
        }
    }

    //основная программа
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Application ps = new Application();
                ps.setVisible(true);
            }
        });
    }

}
