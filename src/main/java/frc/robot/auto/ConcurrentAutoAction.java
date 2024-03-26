package frc.robot.auto;

import frc.robot.Components;
import frc.robot.DriveTrain;
import frc.robot.SensorInputs;

import java.util.List;
import java.util.ArrayList;

class ConcurrentAutoAction implements AutoAction {

    private class SelfFinalizableAutoAction implements AutoAction {
        private AutoAction autoAction;
        private boolean finished = false;

        public SelfFinalizableAutoAction(AutoAction autoAction){
            this.autoAction = autoAction;
        }

        public boolean isFinished() {
            return finished;
        }

        @Override
        public void init(DriveTrain driveTrain, Components components, SensorInputs sensors) {
            autoAction.init(driveTrain, components, sensors);
        }

        @Override
        public boolean execute(DriveTrain driveTrain, Components components, SensorInputs sensors) {
            if(!finished) {
                finished = autoAction.execute(driveTrain, components, sensors);
                if (finished) {
                    autoAction.finalize(driveTrain, components, sensors);
                }
            }
            return finished;
        }

        @Override
        public void finalize(DriveTrain driveTrain, Components components, SensorInputs sensors) { }

           @Override
        public String toString() {
            return autoAction.toString();
        }
    }

    private List<SelfFinalizableAutoAction> concurrentActions = new ArrayList<>();

    public ConcurrentAutoAction(List<AutoAction> concurrentActions) {
        this.concurrentActions = concurrentActions
            .stream()
            .map(autoAction -> new SelfFinalizableAutoAction(autoAction))
            .toList();
    }

    @Override
    public void init(DriveTrain driveTrain, Components components, SensorInputs sensors) {
        for (AutoAction autoAction : this.concurrentActions) {
            autoAction.init(driveTrain, components, sensors);
        }
    }
    
    @Override
    public boolean execute(DriveTrain driveTrain, Components components, SensorInputs sensors) {
        return this.concurrentActions
            .stream()
            .allMatch(autoAction -> autoAction.execute(driveTrain, components, sensors));
    }

    @Override
    public void finalize(DriveTrain driveTrain, Components components, SensorInputs sensors) {}

    @Override
    public String toString() {
        return "Running Concurrently [" + 
            String.join(",", 
                concurrentActions.stream()
                .filter(action -> !action.isFinished())
                .map(action -> action.toString())
                .toList()) + 
        " ]";
    }
}