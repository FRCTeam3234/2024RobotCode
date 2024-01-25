package frc.robot.Auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public abstract class AutoAction {
    //Ran first when sequence | Ran before Execute
    public abstract void Init(DriveTrain driveTrain, Components components, SensorInputs sensor);
    
    //Ran after Init
    public abstract boolean Execute(DriveTrain driveTrain, Components components, SensorInputs sensor);
    
    //Ran once action finished
    public abstract void Finalize(DriveTrain driveTrain, Components components, SensorInputs sensor);
}