import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {
    private int remainingTime; // Tiempo restante en segundos
    private boolean isRunning = false;
    private Timer timer;
    private TimerListener listener;

    public PomodoroTimer(int durationInSeconds) {
        this.remainingTime = durationInSeconds;
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (remainingTime > 0) {
                        remainingTime--;
                        if (listener != null) {
                            listener.onTimeUpdate(remainingTime);
                        }
                    } else {
                        stop();
                        if (listener != null) {
                            listener.onTimerComplete();
                        }
                    }
                }
            }, 0, 1000);
        }
    }

    public void stop() {
        if (timer != null) {
            timer.cancel(); // Detiene el temporizador
            isRunning = false; // Marca que ya no está corriendo
        }
    }


    public void pause() {
        if (isRunning) {
            timer.cancel();
            isRunning = false;
        }
    }

    public void reset(int durationInSeconds) {
        pause();
        remainingTime = durationInSeconds;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    public interface TimerListener {
        void onTimeUpdate(int remainingTime);

        void onTimerComplete();
    }
}
