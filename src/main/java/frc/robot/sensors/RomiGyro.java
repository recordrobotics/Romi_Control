// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDevice.Direction;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.wpilibj.LinearFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RomiGyro extends SubsystemBase{
  private SimDouble m_simRateX;
  private SimDouble m_simRateY;
  private SimDouble m_simRateZ;
  private SimDouble m_simAngleX;
  private SimDouble m_simAngleY;
  private SimDouble m_simAngleZ;

  private double m_angleXOffset;
  private double m_angleYOffset;
  private double m_angleZOffset;
  private double offset = (90.0/175 + 2.48/90)*0.01;
  private double angle_z;

  LinearFilter smoothing_filter = LinearFilter.singlePoleIIR(0.1, 0.02);

  /** Create a new RomiGyro. */
  public RomiGyro() {
    SimDevice gyroSimDevice = SimDevice.create("Gyro:RomiGyro");
    if (gyroSimDevice != null) {
      gyroSimDevice.createBoolean("init", Direction.kOutput, true);
      m_simRateX = gyroSimDevice.createDouble("rate_x", Direction.kInput, 0.0);
      m_simRateY = gyroSimDevice.createDouble("rate_y", Direction.kInput, 0.0);
      m_simRateZ = gyroSimDevice.createDouble("rate_z", Direction.kInput, 0.0);

      m_simAngleX = gyroSimDevice.createDouble("angle_x", Direction.kInput, 0.0);
      m_simAngleY = gyroSimDevice.createDouble("angle_y", Direction.kInput, 0.0);
      m_simAngleZ = gyroSimDevice.createDouble("angle_z", Direction.kInput, 0.0);
    }
  }

  /**
   * Get the rate of turn in degrees-per-second around the X-axis.
   *
   * @return rate of turn in degrees-per-second
   */
  public double getRateX() {
    if (m_simRateX != null) {
      return m_simRateX.get();
    }

    return 0.0;
  }

  /**
   * Get the rate of turn in degrees-per-second around the Y-axis.
   *
   * @return rate of turn in degrees-per-second
   */
  public double getRateY() {
    if (m_simRateY != null) {
      return m_simRateY.get();
    }

    return 0.0;
  }

  /**
   * Get the rate of turn in degrees-per-second around the Z-axis.
   *
   * @return rate of turn in degrees-per-second
   */
  public double getRateZ() {
    if (m_simRateZ != null) {
      return m_simRateZ.get();
    }

    return 0.0;
  }

  /**
   * Get the currently reported angle around the X-axis.
   *
   * @return current angle around X-axis in degrees
   */
  public double getAngleX() {
    if (m_simAngleX != null) {
      return m_simAngleX.get() - m_angleXOffset;
    }

    return 0.0;
  }

  /**
   * Get the currently reported angle around the X-axis.
   *
   * @return current angle around Y-axis in degrees
   */
  public double getAngleY() { 
    if (m_simAngleY != null) {
      return m_simAngleY.get() - m_angleYOffset;
    }

    return 0.0;
  }

  /**
   * Get the currently reported angle around the Z-axis.
   *
   * @return current angle around Z-axis in degrees
   */
  public double getAngleZ() {
    if (m_simAngleZ != null) {
      return smoothing_filter.calculate(m_simAngleZ.get()) - m_angleZOffset;
    }

    return 0.0;
  }

  @Override
  public void periodic() {

    m_angleZOffset += offset;
    SmartDashboard.putNumber("angle_z", this.getAngleZ());
  }

  /** Reset the gyro angles to 0. */
  public void reset() {
    if (m_simAngleX != null) {
      m_angleXOffset = m_simAngleX.get();
      m_angleYOffset = m_simAngleY.get();
      m_angleZOffset = m_simAngleZ.get();
    }
  }

  public void calibrate(){
    this.offset = 0;
    System.out.println("Initiating calibration: DO NOT MOVE ROBOT!");

    double initAngle = getAngleZ();
    Timer.delay(10);
    double finalAngle = getAngleZ();
    this.offset = (finalAngle - initAngle)/(10.0 * 50.0);
    System.out.println("initAngle: " + initAngle + ". finalAngle: " + finalAngle + ". offset: " + this.offset);
    System.out.println("Done Calibrating!!!");
    reset();
  }
}