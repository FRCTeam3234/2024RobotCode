package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Components {
    //Components Definitions
        //Auto

        //Tele

        //Both
        public final CANSparkMax intakeSmallBar = new CANSparkMax(5, MotorType.kBrushless);
        public final CANSparkMax intakeBigBar = new CANSparkMax(6, MotorType.kBrushless);
        public final CANSparkMax leftShooter = new CANSparkMax(7, MotorType.kBrushless);
        public final CANSparkMax rightShooter = new CANSparkMax(8, MotorType.kBrushless);
}