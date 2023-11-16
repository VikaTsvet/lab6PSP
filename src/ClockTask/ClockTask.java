package ClockTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

class ClockTask {

    public static void main(String[] args) {
        new ClockTask();
    }

    static JFrame frame;
    static JLabel hourLabel, minuteLabel;
    static JLabel alarmLabel;
    static JButton submitButton;
    static JButton startButton;
    static JTextField hourField;
    static JTextField minuteField;
    static ClockFace clockFace;

    private String hour = "-1";
    private String minute = "-1";

    public ClockTask() {

        frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(600, 750);

        alarmLabel = new JLabel("Будильник: не назначен");
        submitButton = new JButton("Навести");
        startButton = new JButton("Старт");
        hourField = new JTextField(9);
        minuteField = new JTextField(9);
        hourLabel = new JLabel("Час:");
        minuteLabel = new JLabel("Минута:");

        submitButton.setSize(100, 25);
        submitButton.setLocation(480, 615);

        hourField.setSize(50, 25);
        hourField.setLocation(70, 615);
        minuteField.setSize(50, 25);
        minuteField.setLocation(200, 615);

        hourLabel.setSize(50, 25);
        hourLabel.setLocation(30, 615);
        minuteLabel.setSize(50, 25);
        minuteLabel.setLocation(140, 615);

        startButton.setSize(500, 200);
        startButton.setLocation(50, 250);

        alarmLabel.setSize(200, 25);
        alarmLabel.setLocation(30, 660);

        frame.add(submitButton);
        frame.add(hourField);
        frame.add(minuteField);
        frame.add(hourLabel);
        frame.add(minuteLabel);
        frame.add(startButton);
        frame.add(alarmLabel);

        startButton.addActionListener(new StartActionListener());
        submitButton.addActionListener(new SubmitActionListener());
    }

    public class StartActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frame.remove(startButton);

            Thread t = new Thread(new Runnable() {
                public void run() {
                    clockFace = new ClockFace();
                    clockFace.setSize(600, 600);
                    clockFace.setLocation(0, 0);
                    frame.add(clockFace);
                }
            });
            t.start();
        }
    }

    public class SubmitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            hour = hourField.getText();
            minute = minuteField.getText();

            hourField.setEnabled(false);
            minuteField.setEnabled(false);
            submitButton.setEnabled(false);
            alarmLabel.setText("Будильник: " + hour + ":" + minute);
        }
    }

    public class ClockFace extends JPanel {

        public ClockFace() {
            this.startClock();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();

            LocalTime now = LocalTime.now();
            int seconds = now.getSecond();
            int minutes = now.getMinute();
            int hours = now.getHour();

            if(hours == Integer.parseInt(hour) && minutes == Integer.parseInt(minute)){
                hour = "-1";
                minute = "-1";
                JOptionPane.showMessageDialog(frame, "Сработал будильник!");
            }
            
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 600, 600);
            g2d.setColor(Color.WHITE);
            g2d.translate(300, 300);

            //Drawing the hour markers
            for (int i = 0; i < 12; i++) {

                g2d.drawLine(0, -260, 0, -300);
                g2d.rotate(Math.PI / 6);

            }

            g2d.rotate(seconds * Math.PI / 30);
            g2d.drawLine(0, 0, 0, -290);

            g2d.rotate(2 * Math.PI - seconds * Math.PI / 30);
            g2d.rotate(minutes * Math.PI / 30);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(0, 0, 0, -250);

            g2d.rotate(2 * Math.PI - minutes * Math.PI / 30);
            g2d.rotate(hours * Math.PI / 6);
            g2d.setStroke(new BasicStroke(6));
            g2d.drawLine(0, 0, 0, -200);

            g2d.dispose();
        }

        public void startClock() {
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    repaint();
                }
            });
            timer.start();
        }
    }
}