package frc.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveTrain;
import frc.robot.Motion;
import frc.robot.SwerveDriveTrain;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.wpilibj.Timer;

public class AutoMove {
    
    //Class Defintions
    private Motion motion;

    //Variable Defintions
    static final double gearRatio = 9.13;
    static final double wheelDiameter = 6;
    private double rotationsToTravel;
    private double timeStarted;
    private Pose2d startPose;
    private Pose2d endPose;
    private long movementDurationInNanoSeconds;
    private long startTime;
    
    public AutoMove (Pose2d startPose2d, Pose2d endPose2d, double moveTimeInSeconds) {
        startPose = startPose2d;
        endPose = endPose2d;
        movementDurationInNanoSeconds = (long)(moveTimeInSeconds * 1000000000);        
    }

    public final void moveInit()
    {
        startTime = System.nanoTime();
    }

    public final boolean moveExecute(SwerveDriveTrain swerveDriveTrain) {
        long currentTimeInNano = System.nanoTime();
        long elapsedTime = currentTimeInNano - startTime;
        Pose2d targetPose = startPose.interpolate(endPose, (double)elapsedTime/(double)movementDurationInNanoSeconds);
        Pose2d currentPose = swerveDriveTrain.getPose();

        Twist2d twist = currentPose.log(targetPose);

        swerveDriveTrain.driveFC(twist.dx, twist.dy, twist.dtheta);

        return (elapsedTime > movementDurationInNanoSeconds);
    }

    //To convert inches to encoder count do: inches * gearRatio / (diameter * pi)
    //public final void MoveInit(double maxTime, double inchesToTravel, double toleranceInEncoderCount, double scaler) {

        // //Variable Defintions
        // rotationsToTravel = inchesToTravel * gearRatio / (Math.PI * wheelDiameter);
        // timeStarted = Timer.getFPGATimestamp();
        
        // //Smart Dashboard Output
        // SmartDashboard.putNumber("Auto Rotations To Travel", rotationsToTravel);

        // //Class Defintion/Initialize
        // motion = new Motion(maxTime, rotationsToTravel, toleranceInEncoderCount, scaler);
    //}

    // public final boolean MoveExecute(SwerveDriveTrain swerveDriveTrain) {
        
    //     //Variable Defintions
    //     double currentPosition = driveTrain.getFrontLeftPosition();
    //     double currentTime = ((Timer.getFPGATimestamp()) - timeStarted);
    //     double power = motion.getPower(currentPosition, currentTime);
        
    //     //Smart Dashboard Output
    //     SmartDashboard.putNumber("Auto Left Current Position", driveTrain.getFrontLeftPosition());
    //     SmartDashboard.putNumber("Auto Right Current Position", driveTrain.getFrontRightPosition());
    //     SmartDashboard.putNumber("Auto Motor Power", power);

    //     Control drivetrain
    //     driveTrain.mecanumDrive(0, -power, 0, driveTrain.defaultRotation2d);

    //     //Return true if done
    //     //return motion.isDone(currentPosition, currentTime);
    //     return true;
    // }
}