// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.lang.Math;

public class PrototypeAutonomousDrive extends SequentialCommandGroup {
  /**
   * Creates a new Autonomous Drive based on distance. This will drive out for a specified distance,
   * turn around and drive back.
   * Our autonomous command.
   * @param drivetrain The drivetrain subsystem on which this command will run
   */
  private double sqr = 15;
  public PrototypeAutonomousDrive(Drivetrain drivetrain) {
    addCommands(
      new GyroTurn(45, drivetrain),
      new DriveDistance(0.5, sqr, drivetrain),
      new GyroTurn(-90, drivetrain),
      new DriveDistance(0.5, sqr, drivetrain),
      new DriveDistance(-0.5, sqr, drivetrain),
      new GyroTurn(135, drivetrain),
      new DriveDistance(0.5, Math.sqrt(1.5)*sqr, drivetrain),
      new GyroTurn(-45, drivetrain),
      new DriveDistance(0.5, sqr/2, drivetrain),
      new GyroTurn(-80, drivetrain),
      new DriveDistance(0.5, 2*sqr, drivetrain),
      new DriveDistance(-0.5, 2*sqr, drivetrain),
      new GyroTurn(90, drivetrain),
      new DriveDistance(0.5, 1.5*sqr, drivetrain),
      new GyroTurn(-90, drivetrain),
      new DriveDistance(0.5, 2*sqr, drivetrain),
      new DriveDistance(-0.5, sqr, drivetrain),
      new GyroTurn(90, drivetrain),
      new DriveDistance(0.5, sqr*.75, drivetrain)
    );
  }
}