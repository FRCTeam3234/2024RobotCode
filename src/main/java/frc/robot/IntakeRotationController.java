package frc.robot;

import java.util.OptionalInt;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeRotationController {
    //Variable Defintions
    private boolean emergencyStopped = false;
    private final double rotationMaxSpeed = 1.0; //Must be a + value
    private final double rotationMinSpeed = 0.4; //Must be a + value
    private final double motionScalerConstant = 0.06;
    private boolean homed = false;

    //Define all intake cases here
    private final OptionalInt getTarget(int encoderPosition, ControlInputs controlInputs, SensorInputs sensorInputs) {
        //Out position
        if (controlInputs.intakeOut && encoderPosition < 1075) return OptionalInt.of(1075);
        
        //Return to 0 until limit switch is pressed
        if (controlInputs.intakeOut == false && sensorInputs.intakeLimitHome == false) return OptionalInt.of(0);
        
        //Default case is to not move
        return OptionalInt.empty();
    }

    //Run the intake
    public final void runRotation(Components components, ControlInputs controlInputs, SensorInputs sensorInputs) {
        //Variable Defintions
        int encoderPosition = sensorInputs.intakeEncoder.get(); //Assume this value is + towards out
        OptionalInt target = OptionalInt.empty();
        SmartDashboard.putNumber("Intake Rotation Count", encoderPosition);

        //EStop Controls
        if (controlInputs.intakeEStop) emergencyStopped = true;
        SmartDashboard.putBoolean("Intake E Stopped", emergencyStopped);
        if (emergencyStopped) {
            return;
        }

        //Intake Target Controls
        target = getTarget(encoderPosition, controlInputs, sensorInputs);  

        //Prevent target from being less then zero (for saftey)
        if (target.orElse(0) < 0) target = OptionalInt.empty();
        SmartDashboard.putString("Intake Target", target.isPresent() ? target.getAsInt()+"" : "null" );
        
        double intakePower = 0.0;
        if (homed) {
            if (target.isPresent()) {
                intakePower = rotationMath(target.getAsInt(), encoderPosition);
            }
        } else {
            intakePower = rotationHome(sensorInputs);
        }
        
        components.intakeRotation.set(intakePower);
        SmartDashboard.putBoolean("Intake Unlocked", homed);
        SmartDashboard.putNumber("Intake Power", intakePower);
    }

    private final double rotationMath(int target, int encoderPosition) {
        int intakeDistRemainTravel = (target - encoderPosition);
        
        double intakeMath = 0.0;
        if (target != encoderPosition) {
            intakeMath = Math.sqrt(Math.abs(intakeDistRemainTravel));
            intakeMath *= motionScalerConstant;
            if (intakeDistRemainTravel < 0) {
                intakeMath = Math.min(-rotationMinSpeed, -intakeMath);
                SmartDashboard.putString("Intake Movement", "Moving In");
            } else if (intakeDistRemainTravel > 0) {
                intakeMath = Math.max(rotationMinSpeed, intakeMath);
                SmartDashboard.putString("Intake Movement", "Moving Out");
            }
        } else {
            intakeMath = 0.0;
            SmartDashboard.putString("Intake Movement", "Stopped");
        }
        SmartDashboard.putNumber("Intake Math", intakeMath);
        SmartDashboard.putNumber("Intake Dist Travel", intakeDistRemainTravel);

        double intakeMathClamped = Math.max(-rotationMaxSpeed, Math.min(rotationMaxSpeed, intakeMath));
        SmartDashboard.putNumber("Intake Math Clamped", intakeMathClamped);
        return intakeMathClamped;
    }

    private final double rotationHome(SensorInputs sensorInputs) {
        if (sensorInputs.intakeLimitHome == false) {
            SmartDashboard.putString("Intake Movement", "In (Homing)");
            return -rotationMinSpeed;
        } else {
            homed = true;
            sensorInputs.intakeEncoder.reset();
            SmartDashboard.putString("Intake Movement", "Homed");
        }
        return 0.0;
    }
}
