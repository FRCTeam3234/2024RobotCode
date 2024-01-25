package frc.robot;

public class Motion {

    // Scaler value that will be applied to the power output (significantly less then one)
    //This Scaler takes velocity and translates it into pwm
    private double moveConstant;

    // Total time of the move in seconds
    private double timeMax;

    // Total distance of the move in encoder counts
    private double distance;

    // The max distance that the encoder value can be away from the specified distance
    private double tolerance;

    public Motion(double time, double dist, double tol, double moveK){
        timeMax = Time(dist);
        distance = dist;
        tolerance = tol;
        moveConstant = moveK;
    }

    private double Time(double maxDistance) {
        return (2*maxDistance)/(5500/60);
    }
    
    /*
        Returns a double with an output range of [-1.0, 1.0] as the motor power by changing the target distance to be along
        a polynomial curve according to the current time of the move
    */
    public double getPower(double currentPosition, double currentTime) {
        double targetPosition = distance * polynomial345(currentTime / timeMax);
        return getFeedbackPower(currentPosition, targetPosition);
    }
    
    private double polynomial345(double time) {
        return (10 * Math.pow(time, 3.0)) - (15 * Math.pow(time, 4.0)) + (6 * Math.pow(time, 5.0));
    }

    // Returns a power value between -1.0 and 1.0 for the power of the motor
    private double getFeedbackPower(double currentPosition, double targetPosition) {
        double power = moveConstant * Math.sqrt(Math.abs(targetPosition - currentPosition));
        if (targetPosition - currentPosition < 0.0) {
            power *= -1.0;
        }
        if (power < -1.0) {
            return -1.0;
        } else if (power > 1.0) {
            return 1.0;
        } else {
            return power;
        }
    }
    public double getFeedbackPower(double currentPosition, double targetPosition, double moveConstant) {
        double power = moveConstant * Math.sqrt(Math.abs(targetPosition - currentPosition));
        if (targetPosition - currentPosition < 0.0) {
            power *= -1.0;
        }
        if (power < -1.0) {
            return -1.0;
        } else if (power > 1.0) {
            return 1.0;
        } else {
            return power;
        }
    }

    /*
        Boolean value that is true if either the current time of the move is greater than the max time of the move,
        or if the distance away from the target position is within the tolerance of error for the position
    */
    public boolean isDone (double currentPosition, double currentTime) {
        return (currentTime > timeMax) || (Math.abs(currentPosition - distance) < tolerance);
    }
}