package frc.robot.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SwerveDriveTrain;
import frc.robot.SensorInputs;

//Recommended tolerance in IN is 2.0

public class MoveInlineAutoAction implements AutoAction {
    private AutoMove auto;

    /*
        encoderCount = inches * gearRatio / (diameter * pi);
    */

    public MoveInlineAutoAction(Pose2d start, Pose2d end)
    {
        new AutoMove(start,end,1.0);
    }

    //Time in seconds to execute the move
    private double maxTime;

    //Distance of move in inches
    private double distance;
    
    //Allowable tolerance (error) of the error in encoder counts
    private double tolerance;

    //Scalar constant applied to the power output
    private double moveK = 0.125;

    //public MoveInlineAutoAction()
    //double maxTimeSeconds, double distanceInches, double toleranceInches) {
    //    maxTime = maxTimeSeconds;
    //    distance = distanceInches;

    //    tolerance = toleranceInches * AutoMove.gearRatio / (Math.PI * AutoMove.wheelDiameter);
    //}

    @Override
    public void init(SwerveDriveTrain swerveDriveTrain, Components components, SensorInputs sensor) {
        auto.moveInit();
        //auto.MoveInit(maxTime, distance, tolerance, moveK);
    }

    @Override
    public boolean execute(SwerveDriveTrain swerveDriveTrain, Components components, SensorInputs sensor) {
        return auto.moveExecute(swerveDriveTrain);
        //return auto.MoveExecute(swerveDriveTrain);
        //return true;
    }

    @Override
    public void finalize(SwerveDriveTrain swerveDriveTrain, Components components, SensorInputs sensor) {}

    @Override
    public String toString() {
        return "Auto: Move Inline";
    }
}