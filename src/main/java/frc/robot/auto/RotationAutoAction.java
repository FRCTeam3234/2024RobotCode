package frc.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SwerveDriveTrain;
import frc.robot.SensorInputs;

public class RotationAutoAction implements AutoAction {
    
    //The error in degrees
    private final double targetError = 2.0;

    //The desired overall max power to make a turn
    private final double maxPower = 0.3;

    //The minumium power output to make a turn
        //Value is gotten through drive station from controller input in tele
    private final double emulatedControllerMin = 0.3;

    //The direction of the movement (left/right)
    public static enum direction {
        LEFT,
        RIGHT,
        AUTO
    };
    
    /*  
        P = degrees
        T = targetAngle
        dP/dt = angular velocity
        dP/dt = k(T - P)
    */

    //The target degree to end up at
    private double targetDegrees;
    
    //Makes farthest degree always one
    private double proportionalScaler;

    //The direction the turn will be made
    private direction setDirection = null;

    //The degree to start the turn
    private double startingDegree;

    private final double minPower = emulatedControllerMin * emulatedControllerMin * 0.5;

    public RotationAutoAction(double targetDegrees, direction direction) {
        this.targetDegrees = targetDegrees;
        this.setDirection = direction;
    }
    public RotationAutoAction(double targetDegrees) {
        this(targetDegrees, direction.AUTO);
    }

    @Override
    public void init(SwerveDriveTrain swerveDriveTrain, Components components, SensorInputs sensor) {
        startingDegree = sensor.currentYawDegrees;
        
        if (setDirection != direction.LEFT && setDirection != direction.RIGHT) {
            setDirection = getFastestDirection(startingDegree);
        }
        System.out.println("Auto Rotation Direction: " + setDirection);

        //Scaler
        proportionalScaler = maxPower / travelDistance(startingDegree);
        System.out.println("Auto Rotation Scaler: " + proportionalScaler);
    }

    @Override
    public boolean execute(SwerveDriveTrain swerveDriveTrain, Components components, SensorInputs sensor) {
        //Values
        double degreesLeft = travelDistance(sensor.currentYawDegrees);
        SmartDashboard.putNumber("Auto Degrees Left", degreesLeft);
        
        //Rotation
        double power = degreesLeft * proportionalScaler;

        if (power > 0 && Math.abs(power) < minPower) power = minPower;
        if (power < 0 && Math.abs(power) < minPower) power = -minPower;

        // driveTrain.mecanumDrive(0, 0, power, driveTrain.defaultRotation2d);
        SmartDashboard.putNumber("Auto Motor Power", power);
        
        //Returning
        return Math.abs(degreesLeft) <= targetError;
    }

    @Override
    public void finalize(SwerveDriveTrain swerveDriveTrain, Components components, SensorInputs sensor) {}

    @Override
    public String toString() {
        return "Auto: Rotation";
    }

    private final direction getFastestDirection(double currentDegree) {
        double distanceRight = (360 + (targetDegrees - currentDegree)) % 360;
        if (distanceRight > 180) return direction.LEFT;
        return direction.RIGHT;
    }

    private final double travelDistance(double currentDegree) {
        if ( setDirection == direction.RIGHT ) return (360 + (targetDegrees - currentDegree)) % 360;
        return (360 - (targetDegrees - currentDegree)) % 360;
    }
}