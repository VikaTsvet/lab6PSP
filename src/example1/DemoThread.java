package example1;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DemoThread extends JFrame {

    private BufferedImage buffImg1, buffImg2;
    private int buffImg1Width = 80, buffImg1Height = 80, buffImg2Width = 80, buffImg2Height = 80;
    private int buffImg1X, buffImg1Y, buffImg2X, buffImg2Y;
    private Random randomBuffImg1, randomBuffImg2;

    public DemoThread() {
        setTitle("Demo app");
        setSize(new Dimension(480, 440));
        setLocationRelativeTo(null);
        setVisible(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout());
        setContentPane(content);

        content.add(new Img1(), BorderLayout.WEST);
        content.add(new Img2(), BorderLayout.EAST);
    }

    private class Img1 extends JPanel {
        public Img1() {
            setPreferredSize(new Dimension(240, 220));
            try {
                buffImg1 = ImageIO.read(new File("D:\\1.png"));
            } catch (IOException exc) {
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (buffImg1Width != 0) {
                        buffImg1Width--;
                        buffImg1Height--;
                        repaint();
                        try {
                            Thread.sleep(150);
                        } catch (Exception exc) {
                        }
                        ;
                    }
                }
            }).start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            randomBuffImg1 = new Random();
            buffImg1X = randomBuffImg1.nextInt(getWidth() - buffImg1Width);
            buffImg1Y = randomBuffImg1.nextInt(getHeight() - buffImg1Height);
            graphics2D.drawImage(buffImg1, buffImg1X, buffImg1Y, buffImg1Width, buffImg1Height, this);
        }
    }

    private class Img2 extends JPanel {
        public Img2() {
            setPreferredSize(new Dimension(240, 220));
            try {
                buffImg2 = ImageIO.read(new File("D:\\2.png"));
            } catch (IOException exc) {
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (buffImg2Width != 0) {
                        buffImg2Width -= 2;
                        buffImg2Height -= 2;
                        repaint();
                        try {
                            Thread.sleep(250);
                        } catch (Exception exc) {
                        }
                        ;
                    }
                }
            }).start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            randomBuffImg2 = new Random();
            buffImg2X = randomBuffImg2.nextInt(getWidth() - buffImg2Width);
            buffImg2Y = randomBuffImg2.nextInt(getHeight() - buffImg2Height);
            graphics2D.drawImage(buffImg2, buffImg2X, buffImg2Y, buffImg2Width, buffImg2Height, this);
        }
    }

    public static void main(String[] args) {
        new DemoThread().setVisible(true);
    }
}
