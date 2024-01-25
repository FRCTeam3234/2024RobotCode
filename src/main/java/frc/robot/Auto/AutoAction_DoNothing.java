package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class AutoAction_DoNothing extends AutoAction {
    @Override
    public void Init(DriveTrain driveTrain, Components components, SensorInputs sensor) {}
    
    @Override
    public boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensors) {
        driveTrain.mecanumDrive(0, 0, 0);
        return false;
    }

    @Override
    public void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {}
}