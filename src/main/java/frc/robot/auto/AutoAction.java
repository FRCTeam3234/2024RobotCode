package frc.robot.auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public interface AutoAction {
    //Ran first when sequence | Ran before Execute
    public void init(DriveTrain driveTrain, Components components, SensorInputs sensor);
    
    //Ran after init
    public boolean execute(DriveTrain driveTrain, Components components, SensorInputs sensor);
    
    //Ran once action finished
    public void finalize(DriveTrain driveTrain, Components components, SensorInputs sensor);

    //Used to override toString() and make logging easier
    public String toString();
}