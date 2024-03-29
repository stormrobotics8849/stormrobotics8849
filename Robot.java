// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.TelescopingArm;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private Manipulator manip;
  private TelescopingArm arm;
  private double currArmTarget;

  private XboxController xbox;

  private boolean manualLock = false;



  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    // Initialize subsystems
    manip = new Manipulator();
    arm = new TelescopingArm();
    currArmTarget = manip.kARM_START_POS;

    
    xbox = new XboxController(1);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run(); 
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {


    arm.pull_l(xbox.getLeftY()*0.2);
    arm.pull_r(xbox.getRightY()*0.2);



    if (xbox.getRightTriggerAxis() > 0.1) {
            manip.shoot(-.75);
    }
    else{
      manip.shoot((0));
    }

    if (xbox.getRightBumper()) {
      manip.intake(-0.7);
    } else if (xbox.getLeftBumper()){
      manip.intake(0.5);
    } else {
      manip.intake(0);
    }



    /* Manual Arm */
    if (xbox.getPOV(0) == 0) {
        manip.move_arm(0.2); // Up


    } else if (xbox.getPOV(0) == 180) {
        manip.move_arm(-0.2); // Down


    } else {
        manip.move_arm(0);
    }
    SmartDashboard.putNumber("Arm Target", currArmTarget);
    SmartDashboard.putNumber("Arm", manip.get_arm_enc());


  
  }
  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}
}




    


