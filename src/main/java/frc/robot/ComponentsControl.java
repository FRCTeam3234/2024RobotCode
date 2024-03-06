package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ComponentsControl {
    //Variable Defintions
    private final double shooterHighSpeed = 0.8;
    private final double shooterLowSpeed = 0.3;
    private final double intakeInSpeed = 1.0;
    private final double baseClimbSpeed = 0.45;
    private final double climbUpSpeed = 0.25;
    private final double climbLevelTolerance = 5.0;
    private final double climbConstant = 0.05;
    
    //Run the components
    public void runComponents(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        //Variable Defintions
        double shooterSpeed = 0.0;
        double beltSpeed = 0.0;
        double intakeSpeed = 0.0;
        double climbLeftSpeed = 0.0;
        double climbRightSpeed = 0.0;

        if (controlInputs.shootHigh) {
            shooterSpeed = shooterHighSpeed;
            beltSpeed = 1.0;
            intakeSpeed = -0.2;
        } else if (controlInputs.shootLow) {
            shooterSpeed = shooterLowSpeed;
            beltSpeed = 1.0;
            intakeSpeed = -0.2;
        }

        if (controlInputs.intakeIn) {
            if (!controlInputs.intakeSensorOff) {
                if (!sensorInputs.intakeProxySensor) {
                    intakeSpeed = intakeInSpeed;
                }
            } else {
                intakeSpeed = intakeInSpeed;
            }
        }

        if (controlInputs.climbDown) {
            climbLeftSpeed = -baseClimbSpeed;
            climbRightSpeed = -baseClimbSpeed;

            if (sensorInputs.currentRollDegrees > climbLevelTolerance) { //Right side too low
                double rightPowerAddition = climbConstant * Math.abs(sensorInputs.currentRollDegrees);
                climbRightSpeed -= rightPowerAddition;
            } else if (sensorInputs.currentRollDegrees < -climbLevelTolerance) { //Left side too low
                double leftPowerAddition = climbConstant * Math.abs(sensorInputs.currentRollDegrees);
                climbLeftSpeed -= leftPowerAddition;
            }
        } else if (controlInputs.climbUp) {
            climbLeftSpeed = climbUpSpeed;
            climbRightSpeed = climbUpSpeed;
        }

        SmartDashboard.putNumber("Shooter Left Speed", shooterSpeed);
        SmartDashboard.putNumber("Shooter Right Speed", Math.max(0, shooterSpeed-0.05) );
        SmartDashboard.putNumber("Intake Speed", intakeSpeed);
        SmartDashboard.putNumber("Left Climb Speed", climbLeftSpeed);
        SmartDashboard.putNumber("Right Climb Speed", climbRightSpeed);

        components.leftShooter.set(shooterSpeed);
        components.rightShooter.set( Math.max(0, shooterSpeed-0.05) );
        components.rightBelt.set(beltSpeed);

        components.intakeBars.set(intakeSpeed);
        
        components.climbLeft.set(powerClamp(climbLeftSpeed));
        components.climbRight.set(powerClamp(climbRightSpeed));
    }

    private double powerClamp(double power) {
        if (power > 1.0) {
            power = 1.0;
        } else if (power < -1) {
            power = -1.0;
        }
        return power;
    }
}