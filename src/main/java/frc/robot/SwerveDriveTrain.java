package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import java.io.File;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import swervelib.parser.SwerveParser;
import swervelib.SwerveDrive;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;

public class SwerveDriveTrain {
    
    private final CANSparkMax driveFrontLeft = new CANSparkMax(1, MotorType.kBrushless);
    private final CANSparkMax angleFrontLeft = new CANSparkMax(2, MotorType.kBrushless);

    private final CANSparkMax driveFrontRight = new CANSparkMax(3, MotorType.kBrushless);
    private final CANSparkMax angleFrontRight = new CANSparkMax(4, MotorType.kBrushless);

    private final CANSparkMax driveBackLeft = new CANSparkMax(5, MotorType.kBrushless);
    private final CANSparkMax angleBackLeft = new CANSparkMax(6, MotorType.kBrushless);

    private final CANSparkMax driveBackRight = new CANSparkMax(7, MotorType.kBrushless);
    private final CANSparkMax angleBackRight = new CANSparkMax(8, MotorType.kBrushless);

    double maximumSpeed = 4.35864;
    File swerveJsonDirectory = new File(Filesystem.getDeployDirectory(),"swerve");
    SwerveDrive  swerveDrive = new SwerveParser(directory).createSwerveDrive(maximumSpeed);

      /**
   * Command to drive the robot using translative values and heading as a setpoint.
   *
   * @param translationX Translation in the X direction.
   * @param translationY Translation in the Y direction.
   * @param headingX     Heading X to calculate angle of the joystick.
   * @param headingY     Heading Y to calculate angle of the joystick.
   * @return Drive command.
   */
  public Command driveCommand(DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier headingX,
        DoubleSupplier headingY)
  {
    return run(() -> {
      double xInput = Math.pow(translationX.getAsDouble(), 3); // Smooth controll out
      double yInput = Math.pow(translationY.getAsDouble(), 3); // Smooth controll out
      // Make the robot move
      driveFieldOriented(swerveDrive.swerveController.getTargetSpeeds(xInput, yInput, headingX.getAsDouble(), headingY.getAsDouble(), swerveDrive.getYaw().getRadians(),  swerveDrive.getMaximumVelocity()));
    });
  }

  /**
   * Command to drive the robot using translative values and heading as angular velocity.
   *
   * @param translationX     Translation in the X direction.
   * @param translationY     Translation in the Y direction.
   * @param angularRotationX Rotation of the robot to set
   * @return Drive command.
   */
  public Command driveCommand(DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier angularRotationX)
  {
    return run(() -> {
      // Make the robot move
      swerveDrive.drive(new Translation2d(translationX.getAsDouble() * swerveDrive.getMaximumVelocity(), translationY.getAsDouble() * swerveDrive.getMaximumVelocity()), angularRotationX.getAsDouble() * swerveDrive.getMaximumAngularVelocity(), true, false);
    });
  }
}
