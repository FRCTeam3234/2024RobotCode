package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class ControlInputs {
    //Joysticks IDs
    private final int driveStickDeviceId = 0;

    //Max Input
    private final double driveStickMax = 0.8;

    //Joystick Definitions
    private final XboxController driveStick = new XboxController(driveStickDeviceId);

    //Variable Defintions
    public double driveStickX = 0.0;
    public double driveStickY = 0.0;
    public double driveStickZrotation = 0.0;

    //Reading the controls
    public final void readControls() {
        //Drivestick
        driveStickX = ( driveStick.getLeftX() * Math.abs(driveStick.getLeftX()) ) * driveStickMax;
        driveStickY = ( driveStick.getLeftY() * Math.abs(driveStick.getLeftY()) ) * driveStickMax;
        driveStickZrotation = ( driveStick.getRightX() * Math.abs(driveStick.getRightX()) ) * driveStickMax;
    }

    //For later
    public final void setRumble(double value) {
        driveStick.setRumble(RumbleType.kBothRumble, value);
    }
}