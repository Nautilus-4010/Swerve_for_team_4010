// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you c3an modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.susbsystems;

import com.ctre.phoenix6.mechanisms.swerve.utility.PhoenixPIDController;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilidades.Constants;
import frc.robot.utilidades.HardwareMap;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  public Shooter() {
    HardwareMap.shooter_spin_motor.resetEncoders();
  }

  PhoenixPIDController rotatorPIDController = new PhoenixPIDController(1, 0.2, 15);

  public void chargeLauncher(){
    HardwareMap.shooter_launcher.set(1);
  }

  public void reload(){
      HardwareMap.shooter_reloader.set(1);
  }

  public void stopLauncher(){
    HardwareMap.shooter_launcher.set(0);
  }
  
  public void stopReloader(){
    HardwareMap.shooter_reloader.set(0);
  }

  public void stop(){
    stopLauncher();
    stopReloader();
  }

  public double[] getRotatorPosition(){
    return new double[] {HardwareMap.shooter_spin_motor.getPosition(), HardwareMap.shooter_spin_motor_2.getPosition()};
  }

  public Rotation2d[] getRotatorRotation(){
    return new Rotation2d[] {
      new Rotation2d(Constants.TICKS_PER_RADIAN_OF_THE_ROTATOR * getRotatorPosition()[0]),
      new Rotation2d(Constants.TICKS_PER_RADIAN_OF_THE_ROTATOR * getRotatorPosition()[1])};
  }

  public void setAngle(Rotation2d angle){
      HardwareMap.shooter_spin_motor.set(rotatorPIDController.calculate(getRotatorPosition()[0], angle.getRadians() * Constants.TICKS_PER_RADIAN_OF_THE_ROTATOR));
      HardwareMap.shooter_spin_motor_2.set(rotatorPIDController.calculate(getRotatorPosition()[2], angle.getRadians() * Constants.TICKS_PER_RADIAN_OF_THE_ROTATOR));
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
