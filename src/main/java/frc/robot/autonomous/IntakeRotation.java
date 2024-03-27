package frc.robot.autonomous;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class IntakeRotation extends AutoAction {
    
    private boolean out;
    private boolean run;

    public IntakeRotation(boolean setOut, boolean setRun) {
        out = setOut;
        run = setRun;
    }
    
    @Override
    public void init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        components.autoIntakeOut = out;
        components.autoIntakeRun = run;
    }
    
    @Override
    public boolean execute(DriveTrain driveTrain, Components components, SensorInputs sensors) {
        if (components.autoIntakeOut) {
            if (sensors.intakeEncoder.get() >= 1073) return true;
        } else {
            if (sensors.intakeEncoder.get() <= 4) return true;
        }

        driveTrain.mecanumDrive(0, 0, 0, driveTrain.defaultRotation2d);
        return false;
    }

    @Override
    public void finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {}

    @Override
    public String toString() {
        return "Auto: Move Intake";
    }
}