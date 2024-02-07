package frc.robot.auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class DoNothingAutoAction extends AutoAction {
    @Override
    public void init(DriveTrain driveTrain, Components components, SensorInputs sensor) {}
    
    @Override
    public boolean execute(DriveTrain driveTrain, Components components, SensorInputs sensors) {
        driveTrain.mecanumDrive(0, 0, 0, driveTrain.defaultRotation2d);
        return false;
    }

    @Override
    public void finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {}

    @Override
    public String toString() {
        return "Auto: Do Nothing";
    }
}