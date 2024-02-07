package frc.robot.auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public abstract class AutoAction {
    //Ran first when sequence | Ran before Execute
    public abstract void init(DriveTrain driveTrain, Components components, SensorInputs sensor);
    
    //Ran after init
    public abstract boolean execute(DriveTrain driveTrain, Components components, SensorInputs sensor);
    
    //Ran once action finished
    public abstract void finalize(DriveTrain driveTrain, Components components, SensorInputs sensor);

    //Used to override toString() and make logging easier
    public abstract String toString();
}