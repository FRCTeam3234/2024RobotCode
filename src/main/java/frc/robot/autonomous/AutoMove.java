package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveTrain;
import frc.robot.Motion;
import edu.wpi.first.wpilibj.Timer;

public class AutoMove {
    //Class Defintions
    private Motion motion;

    //Variable Defintions
    static final double gearRatio = 9.13;
    static final double wheelDiameter = 6;
    private double rotationsToTravel;
    private double timeStarted;
    private double startPosition;

    //To convert inches to encoder count do: inches * gearRatio / (diameter * pi)
    public final void MoveInit(double maxTime, double inchesToTravel, double toleranceInEncoderCount, double scaler, DriveTrain driveTrain) {

        //Variable Defintions
        rotationsToTravel = inchesToTravel * gearRatio / (Math.PI * wheelDiameter);
        startPosition = driveTrain.getFrontLeftPosition();
        timeStarted = Timer.getFPGATimestamp();
        
        //Smart Dashboard Output
        SmartDashboard.putNumber("Auto Rotations To Travel", rotationsToTravel);

        //Class Defintion/Initialize
        motion = new Motion(maxTime, rotationsToTravel, toleranceInEncoderCount, scaler);
    }

    public final boolean MoveExecute(DriveTrain driveTrain) {
        
        //Variable Defintions
        double currentPosition = (driveTrain.getFrontLeftPosition() - startPosition);
        double currentTime = ((Timer.getFPGATimestamp()) - timeStarted);
        double power = motion.getPower(currentPosition, currentTime);
        
        //Smart Dashboard Output
        SmartDashboard.putNumber("Auto Current Position", currentPosition);
        SmartDashboard.putNumber("Auto Motor Power", power);

        //Control drivetrain
        driveTrain.mecanumDrive(0, -power, 0, driveTrain.defaultRotation2d);

        //Return true if done
        return motion.isDone(currentPosition, currentTime);
    }
}