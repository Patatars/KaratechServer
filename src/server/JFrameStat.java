package server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class JFrameStat extends JFrame {
    Container cont = getContentPane();
    public JFrameStat() throws IOException {
        cont.setLayout(new GridLayout(3,3));
        BufferedImage image = ImageIO.read(new File("server/img/tatami.jpg"));
        JLabel label = new JLabel(new ImageIcon(image ));
        cont.add(label);
    }
}
