package io.github.bomb787.extended_aviation.util;

import immersive_aircraft.item.upgrade.AircraftStat;
import io.github.bomb787.extended_aviation.entities.CustomAirplaneEntity;

/**
 * A.E.R.O.E.P.P.S
 * An Extremely Redundant, Otherwise Enhanced (Ph)light Physics System
 * 
 * More realistic but still quite unrealistic physics. Makes flying a bit more challenging.
 */
public class AeroEpps {
	
	public static float getAoa(CustomAirplaneEntity entity) {
		double vecY = entity.getForwardDirection().y;
		float pitch = entity.getXRot();
		return 1;
	}
	
	public static float lift(CustomAirplaneEntity entity) {
		
		
	}
	
	private static float liftCoefficient(float lift, float aoa) {
		//Thin airfoil theory
		
	}

}