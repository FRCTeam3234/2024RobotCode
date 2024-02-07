package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ComponentsControl {
    //Variable Defintions
    private final double shooterHighSpeed = 1.0;
    private final double shooterLowSpeed = 0.3;
    private final double intakeInSpeed = 1.0;
    
    //Run the components
    public void runComponents(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        //Variable Defintions
        double shooterSpeed = 0.0;
        double intakeSpeed = 0.0;

        if (controlInputs.shootHigh) {
            shooterSpeed = shooterHighSpeed;
            intakeSpeed = -intakeInSpeed;
        } else if (controlInputs.shootLow) {
            shooterSpeed = shooterLowSpeed;
            intakeSpeed = -intakeInSpeed;
        }

        if (controlInputs.intakeIn) {
            if (!controlInputs.intakeSensorOff) {
                if (!sensorInputs.intakeLimitHome) {
                    intakeSpeed = intakeInSpeed;
                }
            } else {
                intakeSpeed = intakeInSpeed;
            }
        }

        SmartDashboard.putNumber("Shooter Speed", shooterSpeed);
        SmartDashboard.putNumber("Intake Speed", intakeSpeed);

        components.leftLowerShooter.set(shooterSpeed);
        components.leftUpperShooter.set(shooterSpeed);
        components.intakeBigBar.set(intakeSpeed);
    }
}