package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeRotationControl {
    //Variable Defintions
    private boolean intakeEStopped = false;
    private final double intakeRotationMaxSpeed = 1.0; //Must be a + value
    private final double intakeRotationMinSpeed = 0.4; //Must be a + value
    private final double intakeMotionScalerConstant = 0.06;
    private boolean intakeHomed = false;
    double intakeTarget;
    int intakeEncoderPosition;

    //Run the components
    public final void runRotation(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        //Variable Defintions
        intakeEncoderPosition = components.intakeEncoder.get(); //Assume this value is + towards out
        intakeTarget = intakeEncoderPosition;
        SmartDashboard.putNumber("Intake Rotation Count", intakeEncoderPosition);

        //EStop Controls
        if (controlInputs.intakeEStop) intakeEStopped = true;
        SmartDashboard.putBoolean("Intake E Stopped", intakeEStopped);

        //Intake Target Controls
        if (controlInputs.intakeOut) {
            intakeTarget = 972.0;
        } else {
            if (sensorInputs.intakeLimitHome == false) intakeTarget = 0.0;
        }
        
        double intakePower = rotationMath(components, sensorInputs);
        SmartDashboard.putBoolean("Intake Unlocked", intakeHomed);
        SmartDashboard.putNumber("Intake Power", intakePower);
    }

    private final double rotationMath(Components components, SensorInputs sensorInputs) {
        if (!intakeEStopped && intakeHomed) {
            double intakeDistRemainTravel = (intakeTarget - intakeEncoderPosition);
            
            double intakeMath = 0.0;
            if (intakeTarget != intakeEncoderPosition) {
                intakeMath = Math.sqrt(Math.abs(intakeDistRemainTravel));
                intakeMath *= intakeMotionScalerConstant;
                if (intakeDistRemainTravel < 0) {
                    intakeMath = Math.min(-intakeRotationMinSpeed, -intakeMath);
                    SmartDashboard.putString("Intake Movement", "Moving In");
                } else if (intakeDistRemainTravel > 0) {
                    intakeMath = Math.max(intakeRotationMinSpeed, intakeMath);
                    SmartDashboard.putString("Intake Movement", "Moving Out");
                }
            } else {
                intakeMath = 0.0;
                SmartDashboard.putString("Intake Movement", "Stopped");
            }
            SmartDashboard.putNumber("Intake Math", intakeMath);
            SmartDashboard.putNumber("Intake Dist Travel", intakeDistRemainTravel);

            double intakeMathClamped = Math.max(-intakeRotationMaxSpeed, Math.min(intakeRotationMaxSpeed, intakeMath));
            SmartDashboard.putNumber("Intake Math Clamped", intakeMathClamped);
            return intakeMathClamped;
        } else if (!intakeEStopped && !intakeHomed) {
            if (sensorInputs.intakeLimitHome == false) {
                SmartDashboard.putString("Intake Movement", "In (Homing)");
                return -intakeRotationMinSpeed;
            } else {
                intakeHomed = true;
                components.intakeEncoder.reset();
                SmartDashboard.putString("Intake Movement", "Homed");
            }
        }
        return 0.0;
    }
}
