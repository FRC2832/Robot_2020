/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;
import java.util.Map;

/**
 * Add your docs here.
 */
public class ShootingTable {
    private static ShootingTable instance = null;
    private static Map<Integer, Double> shootingTable = new HashMap<>(); //The integer is the distance in inches, the double is the multiplier
    private int distance = 0;

    

    public ShootingTable(){
        shootingTable.put(0 , 1.0);
        shootingTable.put(24 , 1.0);
        shootingTable.put(48, 1.0);
        shootingTable.put(72 , 1.0);
        shootingTable.put(96 , 1.0);
        shootingTable.put(120, 1.0);
        shootingTable.put(144, 1.0);
        shootingTable.put(168, 1.0);
        shootingTable.put(192, 1.0);
        shootingTable.put(216, 1.0);
        shootingTable.put(240, 1.0);
        shootingTable.put(264, 1.0);
        shootingTable.put(288, 1.0);
        shootingTable.put(312, 1.0);
        shootingTable.put(336, 1.0);
        shootingTable.put(360, 1.0);
    }

    public static ShootingTable getInstance() {

        if (instance == null) {
            instance = new ShootingTable();
        }

        return instance;

    }

    public double getMultiplier(double distance){
        this.distance = (int)(distance - (distance % 24));
        return shootingTable.get(this.distance);
    }

}
