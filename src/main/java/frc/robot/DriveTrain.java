package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class DriveTrain {
    
    private final CANSparkMax driveFrontLeft = new CANSparkMax(0, MotorType.kBrushless);
    private final CANSparkMax driveRearLeft = new CANSparkMax(0, MotorType.kBrushless);
    
    private final CANSparkMax driveFrontRight = new CANSparkMax(0, MotorType.kBrushless);
    private final CANSparkMax driveRearRight = new CANSparkMax(0, MotorType.kBrushless);

    private final MecanumDrive robotDrive = new MecanumDrive(driveFrontLeft, driveRearLeft, driveFrontRight, driveRearRight);

    public DriveTrain() {
        
    }

    public void mecanumDrive(double xSpeed, double ySpeed, double zRotation) {
        robotDrive.driveCartesian(xSpeed, ySpeed, zRotation);
    }

    public void resetEncoders() {
        //Left
        driveFrontLeft.getEncoder().setPosition(0);
        driveRearLeft.getEncoder().setPosition(0);
        //Right
        driveFrontRight.getEncoder().setPosition(0);
        driveRearRight.getEncoder().setPosition(0);
    }
}