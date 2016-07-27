package Tamaized.TamModized.particles;

import java.util.ArrayList;

public class ParticleRegistry {
	
	private static ArrayList<Class<? extends TamParticle>> list = new ArrayList<Class<? extends TamParticle>>();
	
	private ParticleRegistry(){
		
	}
	
	/**
	 * registers the particle class and returns the ID, or returns the ID if it is already registered
	 */
	public static int registerParticle(Class<? extends TamParticle> p){
		if(list.contains(p)) return getID(p);
		list.add(p);
		return list.size()-1;
	}
	
	public static Class<? extends TamParticle> getParticle(int id){
		return list.get(id);
	}
	
	/**
	 * returns the particle class ID and will register the particle if it isn't already
	 */
	public static int getID(Class<? extends TamParticle> c){
		if(list.contains(c)) return list.indexOf(c);
		else return registerParticle(c);
	}

}
