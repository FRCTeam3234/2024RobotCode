package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

public class ControlInputs {
    //Joysticks IDs
    private final int driveStickDeviceId = 0;
    private final int componentsBoardLeftId = 1;
    private final int componentsBoardRightId = 2;

    //Max Input
    private final double driveStickMaxMovement = 0.8;
    private final double driveStickMaxRotation = 0.5;

    //Buttons IDs
        //Components Board Right
        private final int intakeInId = 10;
        private final int shootHighId = 8;
        private final int shootLowId = 9;
        //Components Board Left
        private final int intakeOutId = 12;

    //Joystick Definitions
    private final XboxController driveStick = new XboxController(driveStickDeviceId);
    private final Joystick componentsBoardLeft = new Joystick(componentsBoardLeftId);
    private final Joystick componentsBoardRight = new Joystick(componentsBoardRightId);

    //Variable Defintions
    public double driveStickX = 0.0;
    public double driveStickY = 0.0;
    public double driveStickZrotation = 0.0;
    public boolean intakeOut = false;
    public boolean intakeEStop = false; //Switch
    public boolean intakeSensorOff = false; //Switch
    public boolean intakeIn = false;
    public boolean shootHigh = false;
    public boolean shootLow = false;
    public boolean climbUp = false;
    public boolean climbDown = false;

    //Reading the controls
    public final void readControls(ComponentsControl componentsControl) {
        //Drivestick
        driveStickX = ( driveStick.getLeftX() * Math.abs(driveStick.getLeftX()) ) * driveStickMaxMovement;
        driveStickY = ( driveStick.getLeftY() * Math.abs(driveStick.getLeftY()) ) * driveStickMaxMovement;
        driveStickZrotation = ( driveStick.getRightX() * Math.abs(driveStick.getRightX()) ) * driveStickMaxRotation;
        //Components Board Right
        intakeIn = componentsBoardRight.getRawButton(intakeInId);
        shootHigh = componentsBoardRight.getRawButton(shootHighId);
        shootLow = componentsBoardRight.getRawButton(shootLowId);
        intakeSensorOff = (componentsBoardRight.getY() <= 0.5);
        SmartDashboard.putBoolean("Intake Sensor", !intakeSensorOff);
        //Components Board Left
        intakeOut = componentsBoardLeft.getRawButton(intakeOutId);
        intakeEStop = (componentsBoardLeft.getX() <= -0.5);
        //Drive Controller Climb
        climbDown = driveStick.getAButton();
        climbUp = driveStick.getYButton();
    }

    //For later
    public final void setRumble(double value) {
        driveStick.setRumble(RumbleType.kBothRumble, value);
    }
}