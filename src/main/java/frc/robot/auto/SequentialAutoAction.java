package frc.robot.auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

import java.util.Queue;
import java.util.LinkedList;

public class SequentialAutoAction implements AutoAction {

    private String name;
    private Queue<AutoAction> actionSequence = new LinkedList<>();
    private AutoAction currentAction;

    public SequentialAutoAction(String name, Queue<AutoAction> actionSequence) {
        this.name = name;
        this.actionSequence = actionSequence;
    }

    @Override
    public void init(DriveTrain driveTrain, Components components, SensorInputs sensors) {
        currentAction = actionSequence.poll();
        if (currentAction != null) {
            currentAction.init(driveTrain, components, sensors);
        }
    }
    
    @Override
    public boolean execute(DriveTrain driveTrain, Components components, SensorInputs sensors) {
        if (currentAction == null) {
            return true;
        }
        boolean finished = currentAction.execute(driveTrain, components, sensors);
        if (finished) {
            currentAction.finalize(driveTrain, components, sensors);
            currentAction = actionSequence.poll();
            if (currentAction != null) {
                currentAction.init(driveTrain, components, sensors);
            }
        }
        return currentAction == null;
    }

    @Override
    public void finalize(DriveTrain driveTrain, Components components, SensorInputs sensors) {}

    @Override
    public String toString() {
        return "Running " + name + ": " + currentAction;
    }
}