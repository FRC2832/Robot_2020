package frc.robot;

import edu.wpi.first.networktables.NetworkTable;

public class CameraNetworkTable {
    NetworkTable.initialize("10.28.32.4");
    NetworkTable.addConnectionListener();
}