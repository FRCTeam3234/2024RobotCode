    package frc.robot.auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;
import edu.wpi.first.wpilibj.Timer;

class WaitAutoAction implements AutoAction {
    private double startTime = 0;
    private double duration = 0;

    public WaitAutoAction(double durationSeconds) {
        this.duration = durationSeconds;
    }

    @Override
    public void init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        this.startTime = Timer.getFPGATimestamp();
    }
    
    @Override
    public boolean execute(DriveTrain driveTrain, Components components, SensorInputs sensors) {
        return Timer.getFPGATimestamp() > this.startTime + this.duration;
    }

    @Override
    public void finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {

    }

    @Override
    public String toString() {
        return "Waiting for " + this.duration + " s";
    }
}