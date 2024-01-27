package frc.robot.Auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoAction_Rotation extends AutoAction {
    
    //The target degree to end up at
    private double targetDegrees;

    //The error in degrees
    private double targetError = 2.0;

    //The desired overall max power to make a turn
    private double maxPower = 0.3;

    //The scaler value for power per degree
    private double spinK = 0.05;

    //The minumium power output to make a turn
        //Value is gotten through drive station from controller input in tele
    private double emulatedControllerMin = 0.3;
    
    /*  
        P = degrees
        T = targetAngle
        dP/dt = angular velocity
        dP/dt = k(T - P)
    */
    
    //Makes farthest degree always one
    private double proportionalScaler;

    private double minPower = emulatedControllerMin * emulatedControllerMin * 0.5;

    public AutoAction_Rotation(double ftargetDegrees) {
        targetDegrees = ftargetDegrees;
    }
    
    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        proportionalScaler = maxPower / Math.abs(targetDegrees - sensor.currentYawDegrees);
        System.out.println("Auto Rotation Scaler: " + proportionalScaler);
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        //Values
        double degreesLeft = targetDegrees - sensor.currentYawDegrees;
        SmartDashboard.putNumber("Auto Degrees Left", degreesLeft);
        
        //Rotation
        double power = degreesLeft * proportionalScaler;

        if (power > 0 && Math.abs(power) < minPower) power = minPower;
        if (power < 0 && Math.abs(power) < minPower) power = -minPower;

        driveTrain.mecanumDrive(0, 0, power, driveTrain.defualtRotation2d);
        SmartDashboard.putNumber("Auto Motor Power", power);
        
        //Returning
        if (Math.abs(degreesLeft) <= targetError) return true;
        return false;
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {}
}