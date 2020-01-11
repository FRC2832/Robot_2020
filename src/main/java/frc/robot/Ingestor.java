package frc.robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Ingestor{

    private static Ingestor instance = null;

    private Ingestor() {

        HoloTable holoTable = HoloTable.getInstance();
        SpeedControllerGroup intake;
        intake = holoTable.getIntake();

    }

    public static Ingestor getInstance() {

        if (instance == null) {
            instance = new Ingestor();
        }

        return instance;

    }

}