package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Components {
    //Components Definitions
        //Auto

        //Tele
    public final CANSparkMax climbLeft = new CANSparkMax(12, MotorType.kBrushless);
    public final CANSparkMax climbRight = new CANSparkMax(13, MotorType.kBrushless);

        //Both
    //public final CANSparkMax intakeSmallBar = new CANSparkMax(6, MotorType.kBrushless);
    public final CANSparkFlex intakeBigBar = new CANSparkFlex(5, MotorType.kBrushless);
    public final CANSparkMax intakeRotation = new CANSparkMax(9, MotorType.kBrushless);
    public final CANSparkMax leftBelt = new CANSparkMax(10, MotorType.kBrushless);
    public final CANSparkMax rightBelt = new CANSparkMax(11, MotorType.kBrushless);
    public final CANSparkMax leftShooter = new CANSparkMax(7, MotorType.kBrushed);
    public final CANSparkMax rightShooter = new CANSparkMax(8, MotorType.kBrushed);

    public Components() {
        leftShooter.setInverted(false);
        rightShooter.setInverted(true);
        rightBelt.setInverted(true);
        leftBelt.follow(rightBelt,true);
        //intakeSmallBar.follow(intakeBigBar,false);
    }
}