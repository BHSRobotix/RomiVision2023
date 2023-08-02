// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class DriveVisionFollow extends CommandBase {
  private Drivetrain m_drive;
  private Vision m_vision;
  private Supplier<Double> m_speedSupplier;
  private Supplier<Double> m_rotateSupplier;
  
  private PIDController m_turnPID;
  private PIDController m_rangePID;
  
  /**
  * Creates a new DriveDistance. This command will drive your your robot for a desired distance at
  * a desired speed.
  *
  * @param speed The speed at which the robot will drive
  * @param inches The number of inches the robot will drive
  * @param drive The drivetrain subsystem on which this command will run
  */
  public DriveVisionFollow(Drivetrain drive, Vision vision,
  Supplier<Double> speedSupplier,
  Supplier<Double> rotateSupplier) {
    
    m_speedSupplier = speedSupplier;
    m_rotateSupplier = rotateSupplier;
    m_drive = drive;
    m_vision = vision;
    
    addRequirements(drive);
    
    m_turnPID = new PIDController(Constants.kPTurn, 0, 0);
    m_turnPID.setSetpoint(0);
    m_turnPID.setTolerance(2);
    SmartDashboard.putData("dv_turnPID", m_turnPID);
    
    m_rangePID = new PIDController(Constants.kPRange, 0,0);
    m_rangePID.setSetpoint(Constants.GOAL_RANGE_METERS);
    m_rangePID.setTolerance(.1);
    SmartDashboard.putData("dv_rangePID", m_rangePID);
    
    SmartDashboard.putNumber("dv_GOAL_RANGE_M", Constants.GOAL_RANGE_METERS);
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("DriveVision init");
    SmartDashboard.putString("dv_enable", "true");
    m_drive.arcadeDrive(0, 0);
    m_drive.resetEncoders();
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = m_speedSupplier.get();
    double rotate = m_rotateSupplier.get();
    
    if (m_vision.hasTarget()) {
      double speed_output = m_rangePID.calculate(m_vision.getRange(), Constants.GOAL_RANGE_METERS);
      SmartDashboard.putNumber("dv_pid_range_output", speed_output);
      SmartDashboard.putNumber("dv_pid_range_error", m_rangePID.getPositionError());
      SmartDashboard.putBoolean("dv_pid_range_at_setpoint", m_rangePID.atSetpoint());
      
      double range_speed = Math.abs(speed_output);
      if (speed_output > 0.0) {
        // Going backward, want to backup faster because can lose sight of target when too close
        range_speed += Constants.RANGE_SPEED_MOD;
        speed = MathUtil.clamp(range_speed, Constants.MIN_SPEED_MOVE, Constants.RANGE_SPEED_REV_MAX);
        speed = -speed;
      } else {
        // Going forward, creep up on the target all sneaky like a ninja
        range_speed += Constants.RANGE_SPEED_MOD;
        speed = MathUtil.clamp(range_speed, Constants.MIN_SPEED_MOVE, Constants.RANGE_SPEED_FWD_MAX);
      }
      
      double rot_output = m_turnPID.calculate(m_vision.getYaw(), 0);
      SmartDashboard.putNumber("dv_pid_turn_output", rot_output);
      SmartDashboard.putNumber("dv_pid_turn_error", m_turnPID.getPositionError());
      SmartDashboard.putBoolean("dv_pid_turn_at_setpoint", m_turnPID.atSetpoint());
      rotate = rot_output;
      
      if (m_rangePID.atSetpoint() == false) { 
        //m_drive.arcadeDrive(speed, rotate);    
        speed = speed;
      } else {
        speed = m_speedSupplier.get();
      }
      if (m_turnPID.atSetpoint() == false) {
        rotate = Math.abs(rot_output) + Constants.ROT_SPEED_MOD;
        rotate = MathUtil.clamp(rotate, rotate,.2);
        if (rot_output < 0.0) {
          rotate = -rotate;
        }
        //rotate = 0;
        //rotate = m_rotateSupplier.get();
      } else {
        // TODO: keep joystick responsive if target is seen and at setpoint?
        //speed = m_speedSupplier.get();
        rotate = m_rotateSupplier.get();  
        // speed = 0.0;
        // rotate = 0.0;
      }
    } else {
      // no target
      speed = m_speedSupplier.get();
      rotate = m_rotateSupplier.get();  
      //m_drive.arcadeDrive(speed, rotate);
    } 
    m_drive.arcadeDrive(speed, rotate);
    SmartDashboard.putNumber("dv_speed", speed);
    SmartDashboard.putNumber("dv_rot", rotate);
    
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("DriveVision done");
    SmartDashboard.putString("dv_enable", "false");
    m_drive.arcadeDrive(0, 0);
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Compare distance travelled from start to desired distance
    //return Math.abs(m_drive.getAverageDistanceInch()) >= m_distance;
    return false;
  }
}
