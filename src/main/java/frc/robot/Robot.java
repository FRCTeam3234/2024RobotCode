// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.AutoAction;
import frc.robot.auto.DoNothingAutoAction;
import frc.robot.auto.MoveInlineAutoAction;
import frc.robot.auto.RotationAutoAction;
import frc.robot.auto.ShootAutoAction;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  //Class Initiation
  private final DriveTrain driveTrain = new DriveTrain();
  private final ControlInputs controlInputs = new ControlInputs();
  private final SensorInputs sensorInputs = new SensorInputs();
  private Components components = new Components();
  private final ComponentsControl componentsControl = new ComponentsControl();
  private final IntakeRotationControl intakeRotationControl = new IntakeRotationControl();

  //Variable Initiation

  //Auto Variable Initiation
  private String autoSelected;
  private final String autoModeNull = "Do Nothing";
  private final String autoLeave = "Leave";
  private final String autoTurnTest = "Turn Test";
  private final String autoSpeaker = "Speaker n Leave";
  private ArrayList<AutoAction> autonomousSequence;
  private SendableChooser<String> auto_chooser = new SendableChooser<String>();

  @Override
  public void robotInit() {
    //Drivetrain setup
    driveTrain.resetEncoders();

    //Auto Chooser
    auto_chooser.addOption(autoModeNull, autoModeNull);
    auto_chooser.addOption(autoLeave, autoLeave);
    auto_chooser.addOption(autoTurnTest, autoTurnTest);
    auto_chooser.addOption(autoSpeaker, autoSpeaker);
    auto_chooser.setDefaultOption(autoModeNull, autoModeNull);

    SmartDashboard.putData("Auto Chooser", auto_chooser);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    autonomousSequence = new ArrayList<AutoAction>();
    autoSelected = auto_chooser.getSelected();
    switch (autoSelected) {
      case autoLeave:
        autonomousSequence.add(new MoveInlineAutoAction(5.0, 80.0, 2.0));
        autonomousSequence.add(new DoNothingAutoAction());
        break;
      case autoTurnTest:
        autonomousSequence.add(new RotationAutoAction(45.0));
        autonomousSequence.add(new RotationAutoAction(-45.0));
        autonomousSequence.add(new DoNothingAutoAction());
        break;
      case autoSpeaker:
        autonomousSequence.add(new ShootAutoAction(5.0, 1.0));
        autonomousSequence.add(new MoveInlineAutoAction(5.0, 80.0, 2.0));
        autonomousSequence.add(new DoNothingAutoAction());
      default:
        autonomousSequence.add(new DoNothingAutoAction());
        break;
    }
    autonomousSequence.get(0).init(driveTrain, components, sensorInputs);
  }

  @Override
  public void autonomousPeriodic() {
    if (autonomousSequence.size() > 0) {
      SmartDashboard.putString("Current Auto Action",
         autonomousSequence.get(0).toString());
      sensorInputs.readSensors();
      if (autonomousSequence.get(0).execute(driveTrain, components, sensorInputs)) {
        autonomousSequence.get(0).finalize(driveTrain, components, sensorInputs);
        autonomousSequence.remove(0);
        if (autonomousSequence.size() > 0) {
          autonomousSequence.get(0).init(driveTrain, components, sensorInputs);
        }
      }
    }
    else {
      driveTrain.mecanumDrive(0, 0, 0, driveTrain.defaultRotation2d);
    }
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    controlInputs.readControls(componentsControl);
    sensorInputs.readSensors();
    componentsControl.runComponents(components, controlInputs, sensorInputs);
    intakeRotationControl.runRotation(components, controlInputs, sensorInputs);

    driveTrain.mecanumDrive(controlInputs.driveStickX, controlInputs.driveStickY, controlInputs.driveStickZrotation, sensorInputs.drivetrainRotation);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
