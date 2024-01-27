package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

//Recommended tolerance in IN is 2.0

public class AutoAction_MoveInline extends AutoAction {
    private AutoMove auto = new AutoMove();

    /*
        encoderCount = inches * gearRatio / (diameter * pi);
    */

    //Drivetain setup
    private double gearRatio = 9.13;
    private double wheelDiameter = 6;

    //Time in seconds to execute the move
    private double maxTime;

    //Distance of move in inches
    private double distance;
    
    //Allowable tolerance (error) of the error in encoder counts
    private double tolerance;

    //Scalar constant applied to the power output
    private double moveK = 0.125;

    public AutoAction_MoveInline(double fmaxTimeSeconds, double fdistanceInches, double ftoleranceInches) {
        maxTime = fmaxTimeSeconds;
        distance = fdistanceInches;

        tolerance = ftoleranceInches * gearRatio / (Math.PI * wheelDiameter);
    }

    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        auto.MoveInit(maxTime, distance, tolerance, moveK);
    }

    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        return auto.MoveExecute(driveTrain);
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {}
}