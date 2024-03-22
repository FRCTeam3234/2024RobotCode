package frc.robot.autonomous;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

//Recommended tolerance in IN is 2.0

public class MoveInlineAutoAction extends AutoAction {
    private AutoMove auto = new AutoMove();

    /*
        encoderCount = inches * gearRatio / (diameter * pi);
    */

    //Time in seconds to execute the move
    private double maxTime;

    //Distance of move in inches
    private double distance;
    
    //Allowable tolerance (error) of the error in encoder counts
    private double tolerance;

    //Scalar constant applied to the power output
    private double moveK = 0.125;

    public MoveInlineAutoAction(double maxTimeSeconds, double distanceInches, double toleranceInches) {
        maxTime = maxTimeSeconds;
        distance = distanceInches;

        tolerance = toleranceInches * AutoMove.gearRatio / (Math.PI * AutoMove.wheelDiameter);
    }

    @Override
    public void init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        auto.MoveInit(maxTime, distance, tolerance, moveK, driveTrain);
    }

    @Override
    public boolean execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        return auto.MoveExecute(driveTrain);
    }

    @Override
    public void finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {}

    @Override
    public String toString() {
        return "Auto: Move Inline";
    }
}