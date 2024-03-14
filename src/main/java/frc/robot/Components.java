package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkFlex;

public class Components {
    //Components Definitions
        //Auto

        //Tele
    public final CANSparkMax climbLeft = new CANSparkMax(12, MotorType.kBrushless);
    public final CANSparkMax climbRight = new CANSparkMax(13, MotorType.kBrushless);
    public final CANSparkFlex ampRamp = new CANSparkFlex(6, MotorType.kBrushless);

        //Both
    public final CANSparkMax intakeBars = new CANSparkMax(5, MotorType.kBrushless);
    public final CANSparkMax intakeRotation = new CANSparkMax(9, MotorType.kBrushed);
    public final CANSparkMax leftBelt = new CANSparkMax(10, MotorType.kBrushless);
    public final CANSparkMax rightBelt = new CANSparkMax(11, MotorType.kBrushless);
    public final CANSparkMax leftShooter = new CANSparkMax(7, MotorType.kBrushless);
    public final CANSparkMax rightShooter = new CANSparkMax(8, MotorType.kBrushless);

    public Components() {
        //Shooter
        leftShooter.setInverted(false);
        rightShooter.setInverted(true);
        
        //Belts
        rightBelt.setInverted(true);
        leftBelt.follow(rightBelt,true);
        
        //Intake
        intakeRotation.setInverted(true);
        intakeBars.setInverted(false);
        
        //Climb
        climbLeft.setInverted(true);
        climbRight.setInverted(false);

        climbLeft.getEncoder().setPosition(0.0);
        climbRight.getEncoder().setPosition(0.0);
        climbLeft.getEncoder().setPositionConversionFactor(1);
        climbRight.getEncoder().setPositionConversionFactor(1);

        //Amp Ramp
        ampRamp.setInverted(false);
        ampRamp.getEncoder().setPosition(0.0);
        ampRamp.getEncoder().setPositionConversionFactor(1);
    }
}