package frc.robot.commands;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.susbsystems.Intake;
import frc.robot.susbsystems.Shooter;
import frc.robot.susbsystems.TejuinoBoard;

public class Test extends Command{

    Intake intake;
    Shooter shooter;
    TejuinoBoard tejuinoBoard = new TejuinoBoard(0);
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    boolean finished = false;

    public Test(Intake intake, Shooter shooter){
        this.intake = intake;
        this.shooter = shooter;
    }

    @Override
    public void initialize() {
        intake.take();
        shooter.chargeLauncher();
        tejuinoBoard.all_led_control(1, 255, 0, 0);
        scheduler.schedule(() -> {
            // Code to run after the delay
            intake.stop();
            shooter.stopLauncher();
            shooter.reload();
            tejuinoBoard.all_led_control(1, 0, 255, 0);

            scheduler.schedule(() -> {
                // Code to run after the delay
                shooter.stopReloader();
                shooter.setAngle(new Rotation2d(Math.PI/4));
                tejuinoBoard.all_led_control(1, 0, 0, 255);
            }, 2, TimeUnit.SECONDS);
        }, 2, TimeUnit.SECONDS);

        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished; // Adjust this condition based on when you want the command to finish
    }
}
