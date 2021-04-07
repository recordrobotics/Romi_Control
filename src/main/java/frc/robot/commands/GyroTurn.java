// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.lang.Math;

public class GyroTurn extends CommandBase {
  private final Drivetrain m_drive;
  private final double m_degrees;
  private double m_speed;

  /**
   * Creates a new GyroTurn. This command will turn your robot for a desired rotation (in
   * degrees) and rotational speed.
   *
   * @param degrees Degrees to turn. Leverages encoders to compare distance.
   * @param drive The drive subsystem on which this command will run
   */
  public GyroTurn(double degrees, Drivetrain drive) {
    m_degrees = degrees; //The robot turns 60 degrees when degrees is 45
    m_drive = drive;
    
    addRequirements(drive);
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Set motors to stop, read encoder values for starting point
    m_drive.arcadeDrive(0, 0);
    m_drive.resetGyro();
    m_speed = 0.5;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_speed = 1;
    m_drive.arcadeDrive(0,m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Math.abs(getTurningDistance()-m_degrees) <= 0.5){
    return  true;
  }
    else{
      return false;
    }
  }

  private double getTurningDistance() {
    return m_drive.getGyroAngleX();
  }
}
