package frc.robot.auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

public class ShootAutoAction extends AutoAction {
    private double timeToShoot;
    private double power;
    private long startTime;
    
    public ShootAutoAction(double shootTime, double power) {
        timeToShoot = shootTime * 1000;
        this.power = power;
    }
    
    @Override
    public void init(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        startTime = System.currentTimeMillis();
    }

    @Override
    public boolean execute(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        components.leftLowerShooter.set(power);
        components.leftUpperShooter.set(power);
        return (System.currentTimeMillis() - startTime) >= timeToShoot;
    }

    @Override
    public void finalize(DriveTrain driveTrain, Components components, SensorInputs sensor) {
        components.leftLowerShooter.set(0.0);
        components.leftUpperShooter.set(0.0);
    }

    @Override
    public String toString() {
        return "Auto: Shoot";
    } 
}