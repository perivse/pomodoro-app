import javax.swing.*;
import java.awt.*;

public class PomodoroSwing extends JFrame {
    private PomodoroTimer pomodoroTimer;
    private JLabel timeLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;

    public PomodoroSwing() {
        // Configuración de la ventana
        setTitle("Pomodoro Timer");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializar el temporizador (# minutos)
        pomodoroTimer = new PomodoroTimer(10);
        pomodoroTimer.setListener(new PomodoroTimer.TimerListener() {
            @Override
            public void onTimeUpdate(int remainingTime) {
                SwingUtilities.invokeLater(() -> timeLabel.setText(formatTime(remainingTime)));
            }

            @Override
            public void onTimerComplete() {
                SwingUtilities.invokeLater(() -> {
                    timeLabel.setText("¡Tiempo terminado!");
                    JOptionPane.showMessageDialog(null, "¡Pomodoro completado!");
                });
            }
        });

        // Etiqueta para mostrar el tiempo
        timeLabel = new JLabel(formatTime(pomodoroTimer.getRemainingTime()), SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        add(timeLabel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Botón de iniciar
        startButton = new JButton("Iniciar");
        startButton.addActionListener(e -> pomodoroTimer.start());
        buttonPanel.add(startButton);

        // Botón de pausa
        pauseButton = new JButton("Pausar");
        pauseButton.addActionListener(e -> pomodoroTimer.pause());
        buttonPanel.add(pauseButton);

        // Botón de reiniciar
        resetButton = new JButton("Reiniciar");
        resetButton.addActionListener(e -> {
            pomodoroTimer.reset(25 * 60); // Reinicia a 25 minutos
            timeLabel.setText(formatTime(pomodoroTimer.getRemainingTime()));
        });
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PomodoroSwing());
    }
}

