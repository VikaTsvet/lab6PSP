package example2;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DemoThread extends JFrame {

    private BufferedImage buffImg1, buffImg2;
    private int cloudWidth = 1000, cloudHeight = 150, snowBankWidth = 1000, snowBankHeight = 200, snowBankY = 400, snowflakeX = 200, snowflakeY = 50,
            snowflakeX1 = 500, snowflakeY1 = 505, snowflakeX2 = 550, snowflakeY2 = 505, snowflakeX3 = 640, snowflakeY3 = 415, snowflakeX4 = 752, snowflakeY4 = 420,
            snowflakeX5 = 670, snowflakeY5 = 385, snowflakeX6 = 480, snowflakeY6 = 5;

    private static Image background;
    private static Image snowflake;
    private static Image snowbank;
    private static Image cloud;
    JButton bt;

    public DemoThread() {
        setTitle("Demo app");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        //  Background() - панель, для которой переопределен paintComponent с фоном
        setContentPane(new Background()); // панель устанавливается как contentPane в наследнике JFrame
        Container content = getContentPane(); //теперь все элементы интерфейса будут на этой панели.

        bt = new JButton("Старт");
        bt.setPreferredSize(new Dimension(1000, 50));
        bt.setBackground(Color.white);
        bt.setForeground(Color.BLACK);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bt.setVisible(false);
                Thread snowMove = new Thread(new SnowThread());
                snowMove.start();
                Thread snowMove1 = new Thread(new SnowFlakeThread());
                snowMove1.start();

            }
        });
        content.add(bt);
        content.add(new CloudSnowbank());
    }

    private static class Background extends JPanel { // отрисовка нового фона

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                background = ImageIO.read(new File("background.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(background, 0, 0, null);
        }
    }

    private class CloudSnowbank extends JPanel {

        public CloudSnowbank() {
            setOpaque(false);
            setPreferredSize(new Dimension(1000, 600));
            try {
                cloud = ImageIO.read(new File("cloud.png"));
                snowbank = ImageIO.read(new File("snowbank.png"));
                snowflake = ImageIO.read(new File("flicks.png"));
            } catch (IOException exc) {
            }
            ;

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.drawImage(cloud, 0, 0, cloudWidth, cloudHeight, this);
            graphics2D.drawImage(snowbank, 0, snowBankY, snowBankWidth, snowBankHeight, this);

            graphics2D.drawImage(snowflake, snowflakeX, snowflakeY, 25, 25, this);
            graphics2D.drawImage(snowflake, snowflakeX1, snowflakeY1, 55, 55, this);
            graphics2D.drawImage(snowflake, snowflakeX2, snowflakeY2, 35, 35, this);
            graphics2D.drawImage(snowflake, snowflakeX3, snowflakeY3, 45, 45, this);
            graphics2D.drawImage(snowflake, snowflakeX4, snowflakeY4, 35, 35, this);
            graphics2D.drawImage(snowflake, snowflakeX5, snowflakeY5, 45, 45, this);
            graphics2D.drawImage(snowflake, snowflakeX6, snowflakeY6, 25, 25, this);
        }
    }

    public class SnowThread implements Runnable {
        @Override
        public void run() {
            while (cloudHeight > 0) {
                cloudHeight -= 2;
                snowBankHeight += 4;
                snowBankY -= 4;
                repaint();
                try {
                    Thread.sleep(130);
                } catch (Exception exc) {
                }
                ;
            }
        }
    }

    public class SnowFlakeThread implements Runnable {
        @Override
        public void run() {
            while (cloudHeight > 0) {
                snowflakeX = ThreadLocalRandom.current().nextInt(0, cloudWidth + 1);
                snowflakeY = ThreadLocalRandom.current().nextInt(cloudHeight, snowBankY + 1);

                snowflakeX1 = ThreadLocalRandom.current().nextInt(0, cloudWidth + 1);
                snowflakeY1 = ThreadLocalRandom.current().nextInt(cloudHeight, snowBankY + 1);

                snowflakeX2 = ThreadLocalRandom.current().nextInt(0, cloudWidth + 1);
                snowflakeY2 = ThreadLocalRandom.current().nextInt(cloudHeight, snowBankY + 1);

                snowflakeX3 = ThreadLocalRandom.current().nextInt(0, cloudWidth + 1);
                snowflakeY3 = ThreadLocalRandom.current().nextInt(cloudHeight, snowBankY + 1);

                snowflakeX4 = ThreadLocalRandom.current().nextInt(0, cloudWidth + 1);
                snowflakeY4 = ThreadLocalRandom.current().nextInt(cloudHeight, snowBankY + 1);

                snowflakeX5 = ThreadLocalRandom.current().nextInt(0, cloudWidth + 1);
                snowflakeY5 = ThreadLocalRandom.current().nextInt(cloudHeight, snowBankY + 1);

                snowflakeX6 = ThreadLocalRandom.current().nextInt(0, cloudWidth + 1);
                snowflakeY6 = ThreadLocalRandom.current().nextInt(cloudHeight, snowBankY + 1);
                try {
                    Thread.sleep(150);
                } catch (Exception exc) {
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new DemoThread().setVisible(true);
    }
}

