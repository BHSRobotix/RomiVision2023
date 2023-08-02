// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  PhotonCamera m_camera;
  
  final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(4);
  final double TARGET_HEIGHT_METERS = Units.inchesToMeters(32);
  // Angle between horizontal and the camera.
  final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(35);
  
  private double rangeToTarget;
  private double yawToTarget;
  private boolean hasTarget;
  
  public Vision() {
    m_camera = new PhotonCamera("Microsoft_LifeCam_HD-3000");
    
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    displayOnDashboard();
  }
  
  private void displayOnDashboard() {
    var result = m_camera.getLatestResult();
    hasTarget = result.hasTargets();
    SmartDashboard.putBoolean("vision_targets", hasTarget);
    if (hasTarget) {
      double range = PhotonUtils.calculateDistanceToTargetMeters(
      CAMERA_HEIGHT_METERS,
      TARGET_HEIGHT_METERS,
      CAMERA_PITCH_RADIANS,
      Units.degreesToRadians(result.getBestTarget().getPitch()));

      double yaw = result.getBestTarget().getYaw();
      
      SmartDashboard.putNumber("vision_range_M", range);
      SmartDashboard.putNumber("vision_yaw", yaw);
      
      rangeToTarget = range;
      yawToTarget = yaw;
    }
  }
  
  public double getRange() {
    return rangeToTarget;
  }

  public double getYaw() {
    return yawToTarget;
  }

  public boolean hasTarget() {
    return hasTarget;
  }
  
}
