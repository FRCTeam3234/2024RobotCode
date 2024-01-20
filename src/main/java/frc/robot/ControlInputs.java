package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class ControlInputs {
    //Joysticks IDs
    private final int driveStickDeviceId = 0;

    //Joystick Definitions
    private final XboxController driveStick = new XboxController(driveStickDeviceId);

    //Variable Defintions
    public double driveStickX = 0.0;
    public double driveStickY = 0.0;
    public double driveStickZrotation = 0.0;

    //Reading the controls
    public final void readControls() {
        //Drivestick
        driveStickX = driveStick.getLeftX();
        driveStickY = driveStick.getLeftY();
        driveStickZrotation = driveStick.getRightX();
    }

    //For later
    public final void setRumble(double value) {
        driveStick.setRumble(RumbleType.kBothRumble, value);
    }
}