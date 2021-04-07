// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.lang.Math;
import java.util.ArrayList;


public class GyroTurn extends CommandBase {
  private final Drivetrain m_drive;
  private final double m_degrees;
  private double m_speed;
  private double integral = 0, error = 0, deriv = 0;
  private double kp = 0.2, ki = 0, kd = 0;
  private final double time = 0.02;
  private ArrayList<Double> errorlist = new ArrayList<Double>();

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
  
  public double control(double value) {

    double output = 0;

    error = m_degrees - value;
    errorlist.add(error);

    //factor in the p-term
    output += kp * error;
    //factor in the i-term
    integral += error*time;
    output += ki * integral;

    if (errorlist.size() >= 10){
        updateDeriv();
    }

    //factor in the d-term
    output += kd * deriv;

    return output;
}
private void updateDeriv(){
  double sumx = 0, sumy = 0, sumxy = 0, sumxsq = 0;

  for (int i = 0; i < errorlist.size(); i++){
      sumx += errorlist.get(i);
      sumy += 0.2 * i;
      sumxy += (errorlist.get(i) * 0.2 * i);
      sumxsq += errorlist.get(i) * errorlist.get(i);
  }

  double m = (sumxy - sumx * sumy)/(sumxsq - sumx * sumx);
  deriv = m;

  errorlist.clear();
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
