package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.math.geometry.Rotation2d;

public class DriveTrain {
    
    // private final CANSparkMax driveFrontLeft = new CANSparkMax(4, MotorType.kBrushless);
    // private final CANSparkMax driveRearLeft = new CANSparkMax(3, MotorType.kBrushless);
    
    // private final CANSparkMax driveFrontRight = new CANSparkMax(2, MotorType.kBrushless);
    // private final CANSparkMax driveRearRight = new CANSparkMax(1, MotorType.kBrushless);

    // private final MecanumDrive robotDrive = new MecanumDrive(driveFrontLeft, driveRearLeft, driveFrontRight, driveRearRight);

    public final Rotation2d defaultRotation2d = Rotation2d.fromDegrees(0.0);

    public DriveTrain() {
        //Left Invert
        // driveFrontLeft.setInverted(false);
        // driveRearLeft.setInverted(false);
        // //Right Invert
        // driveFrontRight.setInverted(true);
        // driveRearRight.setInverted(true);
        // //Overall
        // robotDrive.setDeadband(0.0);
    }

    // public void mecanumDrive(double xSpeed, double ySpeed, double zRotation, Rotation2d gyroRotation) {
        // robotDrive.driveCartesian(-ySpeed, xSpeed, zRotation, gyroRotation);
    // }

    // public double getFrontLeftPosition() {
    //     // return driveFrontLeft.getEncoder().getPosition();
    // }
    
    // public double getFrontRightPosition() {
    //     // return driveFrontRight.getEncoder().getPosition();
    // }

    public void resetEncoders() {
        //Left
        // driveFrontLeft.getEncoder().setPosition(0);
        // driveRearLeft.getEncoder().setPosition(0);
        // //Right
        // driveFrontRight.getEncoder().setPosition(0);
        // driveRearRight.getEncoder().setPosition(0);
    }
}