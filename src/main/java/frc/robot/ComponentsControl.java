package frc.robot;

import java.util.OptionalInt;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ComponentsControl {
    //Variable Defintions
    private final double shooterHighSpeed = 1.0;
    private final double shooterLowSpeed = 0.065;
    private final double intakeInSpeed = 1.0;
    private final double baseClimbSpeed = 0.45;
    private final double climbUpSpeed = 1.0;
    private final double climbLevelTolerance = 5.0;
    private final double climbConstant = 0.1;
    private final double ampRampConstant = 1.0;
    private final double ampRampJerkConstant = 0.4;
    private boolean ampOutVsIn = true;
    
    private boolean delay = false;
    private long delayStart;
    private boolean shootLow = false;
    private boolean shootHigh = false;
    private final double delayAmount = 1.0;

    private boolean delayRamp = false;
    private long delayRampStart;
    private boolean ampRampButton = false;
    private final double delayRampAmount = 0.7;
    
    //Run the components
    public void runComponents(Components components, ControlInputs controlInputs, SensorInputs sensorInputs, IntakeRotationController intakeRotationController) {
        //Variable Defintions
        double shooterLeftSpeed = 0.0;
        double shooterRightSpeed = 0.0;
        double beltSpeed = 0.0;
        double intakeSpeed = 0.0;
        double climbLeftSpeed = 0.0;
        double climbRightSpeed = 0.0;
        double ampRampSpeed = 0.0;

        //==Shooter Button Delayed==
        if (delay == false) {
            if (controlInputs.shootHigh) {
                shootHigh = true;
                shootLow = false;
            } else if (controlInputs.shootLow) {
                shootHigh = false;
                shootLow = true;
            }
            if (shootHigh || shootLow) {
                if (controlInputs.shootHigh == false && controlInputs.shootLow == false) {
                    shootHigh = false;
                    shootLow = false;
                    delay = true;
                    delayStart = System.currentTimeMillis();
                }
            }
        } else {
            if ( (System.currentTimeMillis() - delayStart) >= (delayAmount * 1000) ) delay = false;
        }

        //==Amp Ramp Delay==
        /**if (shootLow) {
            if (delayRamp == false) {
                delayRampStart = System.currentTimeMillis();
                delayRamp = true;
            } else {
                if ( (System.currentTimeMillis() - delayRampStart) >= (delayRampAmount * 1000) ) {
                    ampRampButton = true;
                }
            }
        } else {
            delayRamp = false;
            ampRampButton = false;
        }**/
        if (shootLow) {
            if (delayRamp == false) {
                if (sensorInputs.shooterProxySensor) {
                    delayRampStart = System.currentTimeMillis();
                    delayRamp = true;
                }
            } else {
                if ( (System.currentTimeMillis() - delayRampStart) >= (delayRampAmount * 1000) ) {
                    ampRampButton = true;
                }
            }
        } else {
            delayRamp = false;
            ampRampButton = false;
        }

        //==Shooter==
        if (shootHigh) {
            shooterLeftSpeed = shooterHighSpeed;
            shooterRightSpeed = (shooterHighSpeed-0.05);
            beltSpeed = 1.0;
            intakeSpeed = -0.5;
        } else if (shootLow) {
            shooterLeftSpeed = shooterLowSpeed;
            shooterRightSpeed = shooterLowSpeed;
            beltSpeed = 0.6;
            intakeSpeed = -0.5;
        }

        //==Amp Ramp==
        if (controlInputs.lowerAmpRamp) {
            ampRampSpeed = -ampRampConstant;
        } else if (ampRampButton) {
            if (ampOutVsIn) {
                ampRampSpeed = ampRampJerkConstant;
            } else {
                ampRampSpeed = -ampRampJerkConstant;
            }
            
            if (components.ampRamp.getEncoder().getPosition() >= 45) ampOutVsIn = false;
            if (components.ampRamp.getEncoder().getPosition() <= 26) ampOutVsIn = true;
        } else {
            ampRampSpeed = -ampRampConstant;
            if (components.ampRamp.getEncoder().getPosition() <= 4) ampRampSpeed = 0.0;
        }

        //==Intake==
        if (controlInputs.intakeIn) {
            if (!controlInputs.intakeSensorOff) {
                if (!sensorInputs.intakeProxySensor) {
                    intakeSpeed = intakeInSpeed;
                }
            } else {
                intakeSpeed = intakeInSpeed;
            }
        }

        if (controlInputs.intakeOut && sensorInputs.intakeEncoder.get() < 1075) {
            //Out position
            intakeRotationController.setTarget( OptionalInt.of(1075) );
        } else if (controlInputs.intakeOut == false && sensorInputs.intakeLimitHome == false) {
            //Return to 0 until limit switch is pressed
            intakeRotationController.setTarget( OptionalInt.of(0) );
        } else {
            //Default case is to not move
            intakeRotationController.setTarget( OptionalInt.empty() );
        }
        intakeRotationController.runRotation(components, controlInputs, sensorInputs);

        //==Climb==
        if (controlInputs.climbDown) {
            climbLeftSpeed = -baseClimbSpeed;
            climbRightSpeed = -baseClimbSpeed;

            if (sensorInputs.currentRollDegrees > climbLevelTolerance && sensorInputs.currentRollDegrees < 180) { //Right side too low
                climbLeftSpeed += climbConstant;
                climbRightSpeed -= climbConstant;
            } else if (sensorInputs.currentRollDegrees < (360-climbLevelTolerance) && sensorInputs.currentRollDegrees > 180) { //Left side too low
                climbLeftSpeed -= climbConstant;
                climbRightSpeed += climbConstant;
            }

            if (components.climbLeft.getEncoder().getPosition() <= 0) climbLeftSpeed = 0.0;
            if (components.climbRight.getEncoder().getPosition() <= 0) climbRightSpeed = 0.0;
        } else if (controlInputs.climbUp) {
            climbLeftSpeed = climbUpSpeed;
            climbRightSpeed = climbUpSpeed;

            if (components.climbLeft.getEncoder().getPosition() >= 180) climbLeftSpeed = 0.0;
            if (components.climbRight.getEncoder().getPosition() >= 180) climbRightSpeed = 0.0;
        } else if (controlInputs.lowerLeftClimb) {
            climbLeftSpeed = -baseClimbSpeed;
        } else if (controlInputs.lowerRightClimb) {
            climbRightSpeed = -baseClimbSpeed;
        }

        //==Display and set==
        SmartDashboard.putNumber("Shooter Left Speed", shooterLeftSpeed);
        SmartDashboard.putNumber("Shooter Right Speed", shooterRightSpeed);
        SmartDashboard.putNumber("Intake Speed", intakeSpeed);
        SmartDashboard.putNumber("Left Climb Speed", climbLeftSpeed);
        SmartDashboard.putNumber("Right Climb Speed", climbRightSpeed);
        SmartDashboard.putNumber("Left Climb Encoder", components.climbLeft.getEncoder().getPosition());
        SmartDashboard.putNumber("Right Climb Encoder", components.climbRight.getEncoder().getPosition());
        SmartDashboard.putNumber("Amp Ramp Speed", ampRampSpeed);
        SmartDashboard.putNumber("Amp Ramp Encoder", components.ampRamp.getEncoder().getPosition());

        components.leftShooter.set(shooterLeftSpeed);
        components.rightShooter.set(shooterRightSpeed);
        components.rightBelt.set(beltSpeed);

        components.intakeBars.set(intakeSpeed);
        
        components.climbLeft.set(powerClamp(climbLeftSpeed));
        components.climbRight.set(powerClamp(climbRightSpeed));

        components.ampRamp.set(ampRampSpeed);
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