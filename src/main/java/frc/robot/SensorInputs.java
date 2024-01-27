package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Rotation2d;

public class SensorInputs {
    //Sensor Definitions
    private final AHRS navxAhrs = new AHRS(Port.kMXP);
    
    //Variable Defintions
    public float currentPitchDegrees = (float) 0.0;
    public float currentYawDegrees = (float) 0.0;
    public float currentRollDegrees = (float) 0.0;
    public Rotation2d drivetrainRotation = Rotation2d.fromDegrees(0.0);

    //Reading the sensors
    public void readSensors() {
        //NavX
        currentPitchDegrees = navxAhrs.getPitch();
        currentYawDegrees = navxAhrs.getYaw();
        currentRollDegrees = navxAhrs.getRoll();
        drivetrainRotation = Rotation2d.fromDegrees(currentYawDegrees);
        SmartDashboard.putNumber("NavX Pitch", currentPitchDegrees);
        SmartDashboard.putNumber("NavX Yaw", currentYawDegrees);
        SmartDashboard.putNumber("NavX Roll", currentRollDegrees);
    }
}