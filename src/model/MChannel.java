package model;

import processing.core.PApplet;


public class MChannel {
	
	public String channelName 			= "void";
	public String subChannelName		= "void";
	public int colorCode = 255;
	public int id;
	public float gridRadius;
	
	public Boolean isActive = true;
	
	public static PApplet pa;

	public MChannel(String _name) {

		channelName 	= _name;
		colorCode 	= getColorForChannel(_name);
	
	}

	private int getColorForChannel(String _name) {
		
		_name = _name.toLowerCase();
		
		if(_name.indexOf("tv")!=-1)			return pa.color(64,96,109,150);
		if(_name.indexOf("atl")!=-1)		return pa.color(96,123,132,150);
		if(_name.indexOf("retail")!=-1)		return pa.color(127,149,155,150);
		if(_name.indexOf("isc")!=-1)		return pa.color(158,176,178,150);
		if(_name.indexOf("ecom")!=-1)		return pa.color(189,202,200,150);
		if(_name.indexOf("digital")!=-1)	return pa.color(220,228,222,150);
		if(_name.indexOf("social")!=-1)		return pa.color(252,255,245,150);
		
		return pa.color(255,0,255,255);
	}

}
