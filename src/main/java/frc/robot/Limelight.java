package frc.robot;

public class Limelight {
    static double limelight_aim_proportional(){
        double kP = 0.35;
        double targetingAngularVelocity = LimelightHelpers.getTX("limelight") * kP;
        targetingAngularVelocity *= 360;
        targetingAngularVelocity *= -1;

        return targetingAngularVelocity;
    }
    
    static double limelight_range_proportional(){
        double kP = .1;
        double targetingFowardSpeed = LimelightHelpers.getTY("limelight") * kP;
        targetingFowardSpeed *= -1;

        return targetingFowardSpeed;
    }
}

