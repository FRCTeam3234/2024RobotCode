// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.autonomous.AutoAction;
import frc.robot.autonomous.DoNothingAutoAction;
import frc.robot.autonomous.MoveInlineAutoAction;
import frc.robot.autonomous.RotationAutoAction;
import frc.robot.autonomous.ShootAutoAction;

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
  private final Components components = new Components();
  private final ComponentsControl componentsControl = new ComponentsControl();
  private final IntakeRotationController intakeRotationController = new IntakeRotationController();

  //Variable Initiation
  private String modeSelected;
  private final String modeTele = "Normal Mode";
  private final String modeService = "Service Mode";
  private SendableChooser<String> mode_chooser = new SendableChooser<String>();

  //Auto Variable Initiation
  private String autoSelected;
  private final String autoModeNull = "Do Nothing";
  private final String autoLeave = "Leave";
  private final String autoTurnTest = "Turn Test";
  private final String autoSpeaker = "Shoot n Scoot";
  private final String autoSpeakerShoot2 = "Speaker Shoot 2";
  private final String autoSpeakerAngled = "Angled Shoot n Scoot";
  private ArrayList<AutoAction> autonomousSequence;
  private SendableChooser<String> auto_chooser = new SendableChooser<String>();

  @Override
  public void robotInit() {
    //Drivetrain setup
    driveTrain.resetEncoders();

    //Camera Setup
    CameraServer.startAutomaticCapture("Main Camera", 0);

    //Mode Chooser
    mode_chooser.addOption(modeTele, modeTele);
    mode_chooser.addOption(modeService, modeService);
    mode_chooser.setDefaultOption(modeTele, modeTele);

    SmartDashboard.putData("Mode Chooser", mode_chooser);

    //Auto Chooser
    auto_chooser.addOption(autoModeNull, autoModeNull);
    auto_chooser.addOption(autoLeave, autoLeave);
    //auto_chooser.addOption(autoTurnTest, autoTurnTest);
    auto_chooser.addOption(autoSpeaker, autoSpeaker);
    //auto_chooser.addOption(autoSpeakerShoot2, autoSpeakerShoot2);
    auto_chooser.addOption(autoSpeakerAngled, autoSpeakerAngled);
    auto_chooser.setDefaultOption(autoModeNull, autoModeNull);

    SmartDashboard.putData("Auto Chooser", auto_chooser);
  }

  @Override
  public void robotPeriodic() {
    sensorInputs.readSensors();
  }

  @Override
  public void autonomousInit() {
    driveTrain.resetEncoders();
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
        autonomousSequence.add(new ShootAutoAction(3.0, 1.0));
        autonomousSequence.add(new MoveInlineAutoAction(5.0, 80.0, 2.0));
        autonomousSequence.add(new DoNothingAutoAction());
      case autoSpeakerShoot2:
        autonomousSequence.add(new ShootAutoAction(3.0, 1.0));
        autonomousSequence.add(new MoveInlineAutoAction(2.0, 26.0, 2.0));
        autonomousSequence.add(new DoNothingAutoAction());
      case autoSpeakerAngled:
        autonomousSequence.add(new ShootAutoAction(3.0, 1.0));
        autonomousSequence.add(new MoveInlineAutoAction(2.0, 60.0, 2.0));
        autonomousSequence.add(new RotationAutoAction(0.0));
        autonomousSequence.add(new MoveInlineAutoAction(3.0, 80.0, 2.0));
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
    } else {
      driveTrain.mecanumDrive(0, 0, 0, driveTrain.defaultRotation2d);
    }
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    modeSelected = mode_chooser.getSelected();

    controlInputs.readControls(componentsControl, modeSelected);
    sensorInputs.readSensors();
    componentsControl.runComponents(components, controlInputs, sensorInputs, intakeRotationController);

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
