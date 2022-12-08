package client;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.DimensionUIResource;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.awt.*;

public class Client {

    public static void main(String[] args) throws Exception{
        
        final File[] fileEnvoyer =new File[1];


        JFrame fenetre = new JFrame("Clients");
        fenetre.setSize(500,500);
        fenetre.setLayout(new BoxLayout(fenetre.getContentPane(),BoxLayout.Y_AXIS));
        fenetre.setDefaultCloseOperation(fenetre.EXIT_ON_CLOSE);


        JLabel fichier = new JLabel("choisir un fichier");
        fichier.setBorder(new EmptyBorder(20,0,10,0));
        fichier.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel btns = new JPanel();
        btns.setBorder(new EmptyBorder(75,0,10,0));


        JButton envoiFichier = new JButton("envoyer");
        envoiFichier.setPreferredSize(new Dimension(150, 75));

        JButton choixFichier = new JButton("choisir fichier");
        choixFichier.setPreferredSize(new Dimension(150,75));

        btns.add(choixFichier);
        btns.add(envoiFichier);

        choixFichier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JFileChooser fileChooser = new JFileChooser();
                
                if (fileChooser.showOpenDialog(null) == fileChooser.APPROVE_OPTION) {
                    fileEnvoyer[0] = fileChooser.getSelectedFile();
                    fichier.setText("le fichier que vous avez choisie est: " + fileEnvoyer[0].getName());
                }
            }
        });


        envoiFichier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (fileEnvoyer[0] == null) {
                    fichier.setText("choisissez d'abord un fichier");
                }
                else{
                try {
                FileInputStream fileInputStream = new FileInputStream(fileEnvoyer[0].getAbsoluteFile());
                Socket socket = new Socket("localhost",9999);
                
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                byte[] fileNameBytes = fileEnvoyer[0].getName().getBytes(); 
                byte[] sizeContent = new byte[(int)fileEnvoyer[0].length()];
                
                sizeContent = Files.readAllBytes(fileEnvoyer[0].toPath());

                dataOutputStream.writeInt(fileNameBytes.length);
                dataOutputStream.write(fileNameBytes);
                
                dataOutputStream.writeInt(sizeContent.length);
                dataOutputStream.write(sizeContent);
                    
            } catch (IOException error) {
                error.printStackTrace();
            }
            }}});

            fenetre.add(fichier);
            fenetre.add(btns);
            fenetre.setVisible(true);
            


    }

}
