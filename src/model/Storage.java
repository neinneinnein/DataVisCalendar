package model;

import java.util.ArrayList;

import de.bezier.data.XlsReader;
import processing.core.PApplet;


public class Storage {

	public static ArrayList<NiceDates> dateList = new ArrayList<NiceDates>();

	
	private static ArrayList<MChannel> channelList 		= new ArrayList<MChannel>();
	private static ArrayList<MCampaign> campaignList 	= new ArrayList<MCampaign>();
	public static PApplet pa;


	
	public static void init() {
		
		MChannel.pa = pa;
		MCampaign.pa = pa;
			
//		channelList.add(new MChannel("TV",pa.color(120,244,0,90)));
//		channelList.add(new MChannel("ATL",pa.color(0,255,0,90)));
//		channelList.add(new MChannel("Key Local / Global",pa.color(219,236,239,90)));
//		channelList.add(new MChannel("Retail Window",pa.color(255,255,255,90)));
//		channelList.add(new MChannel("Retail CLP",pa.color(253,230,243,90)));
//		channelList.add(new MChannel("PLP/PDP",pa.color(255,255,255,90)));
//		channelList.add(new MChannel("Email / CRM",pa.color(252,234,209,90)));
//		channelList.add(new MChannel("Digital Mobile",pa.color(255,255,255,90)));
//		channelList.add(new MChannel("Twitter",pa.color(64,60,102,90)));
//		channelList.add(new MChannel("Youtube",pa.color(55,200,0,90)));
//		channelList.add(new MChannel("Instagram",pa.color(5,64,60,90)));
//		channelList.add(new MChannel("Facebook",pa.color(120,22,128,90)));
		
		
		
		initXLS();
		
	}

	private static XlsReader reader;


	private static int rows = 0;
	private static int cols = 0;


	private static String[][] cells;
	
	private static void initXLS() {
		
		reader = new XlsReader(pa, pa.dataPath("Info06102014 tom_paddy.xls"));
		
//		reader.openSheet(0);
		reader.showWarnings(false);
		
		reader.firstCell();	
		reader.firstRow();

		// count rows & columns
		while(reader.hasMoreRows())	{	rows++;	reader.nextRow();		}
		while(reader.hasMoreCells()){	cols++;		reader.nextCell();	}
		
		// 
		cells = new String[rows][cols];
		
		// READ & PARSE CELL CONTENTS
		
		reader.firstCell();
		reader.firstRow();
		
		int nrows = 0;
		int ncols = 0;
		
		int COL_CATEGORY 		= 0;
		int COL_CAMPAIGN 		= 1;
		int COL_PRIORITY 		= 2;
		int COL_CHANNEL 		= 3;
		
		int ROW_CHANNELTYPES 	= 1;
		int ROW_CHANNELSUBTYPES 	= 2;
		int ROW_CONTENTSTART 	= 5;
		
		String currentCategoryName 	= "";
		String currentCampaignName 	= "";
		String currentChannelName 	= "";
		
		MCampaign	currentCampaign = new MCampaign("");
		MChannel 	currentChannel;
		
		while(reader.hasMoreRows()){

			ncols = 0;
			
			if(nrows==ROW_CONTENTSTART)	PApplet.println("ROW_CONTENTSTART --------------------------------");
			if(nrows==ROW_CHANNELTYPES)	PApplet.println("ROW_CHANNELTYPES --------------------------------");
			
			PApplet.print("ROW "+nrows+" : ");
			
			while(reader.hasMoreCells()){
				
				String content;
				
				try {
					content = reader.getString();
				} catch (Exception e) {
					//PApplet.print("ERRORROROR "+e.getMessage());
					content = ""+reader.getInt();
				}
				
				// READ CATEGORY NAMES & STORE THEM
				// CHECK EMPTY CELLS IN CATEGORY COLUMN
				// IF EMPTY CELL, USE CURRENT CATEGORY NAME
				
				if(nrows>=ROW_CONTENTSTART && ncols==COL_CATEGORY && content!="") {
					currentCategoryName = content;
				}
				if(nrows>=ROW_CONTENTSTART && ncols==COL_CATEGORY && content=="") {
					content = currentCategoryName;
				}
				
				// READ & CREATE CAMPAIGN
				if(nrows>=ROW_CONTENTSTART && ncols==COL_CAMPAIGN && content!="") {
					currentCampaignName 			= content;
					currentCampaign 				= new MCampaign(currentCampaignName);
					currentCampaign.setCategory(currentCategoryName);
					campaignList.add(currentCampaign);
					
					PApplet.print(content+";");
					
				}
				
				// READ CHANNELNAMES & CREATE CHANNELTYPES
				if(nrows==ROW_CHANNELTYPES && ncols>=COL_CHANNEL && content!="") {
					// NEW CHANNEL
					currentChannelName 	= content;
					currentChannelName 	= currentChannelName.replace("\n", " "); // remove linebreaks
					currentChannel 		= new MChannel(currentChannelName);
					currentChannel.id	= (ncols-COL_CHANNEL)/2;
					channelList.add(currentChannel);
				}
				
				if(nrows==ROW_CHANNELSUBTYPES && ncols>=COL_CHANNEL && content!="") {
					// NEW CHANNEL
					String currentSubChannelName = content;
					currentSubChannelName 	= currentSubChannelName.replace("\n", " "); // remove linebreaks
					int channelID = (ncols-COL_CHANNEL)/2;
					if(channelID<channelList.size())
					channelList.get(channelID).subChannelName = currentSubChannelName;
				}
				
				// READ CHANNELDATES & ADD TO CURRENT CAMPAIGN
				if(nrows>=ROW_CONTENTSTART && ncols>=COL_CHANNEL && content!="") {					
					
					int channelID = (ncols-COL_CHANNEL)/2;
					
					boolean isLaunchDate 	= (ncols-COL_CHANNEL)%2==0;
					boolean isEndDate 		= (ncols-COL_CHANNEL)%2!=0;
					
					String[] split = content.split(",");
					
					for(int i=0; i<split.length; i++) {
					
						if(isLaunchDate)	currentCampaign.addActivity(channelID,split[i].trim());
						if(isEndDate)		currentCampaign.endActivity(channelID,split[i].trim());
					
					}
					
					//PApplet.print(content+";");
					
										
				}
				
				// READ CAMPAIGN
				
				cells[nrows][reader.getCellNum()] = content;
				
				
								
				reader.nextCell();
				
				ncols++;
								
			}
		
			PApplet.println("");
			nrows++;
			reader.nextRow();
		}

		// CHECKCHECK EINS ZWO
		
		for(int i=0; i<channelList.size(); i++) {
			
			MChannel channel = channelList.get(i);
			PApplet.println(i+": "+channel.channelName+" / "+channel.id);
			
		}
		
		for(int i=0; i<campaignList.size(); i++) {
			
			MCampaign campaign = campaignList.get(i);
			PApplet.println(i+": "+campaign.categoryName+" / "+campaign.campaignName+" / "+campaign.getActivities().size());
			PApplet.println(i+": "+campaign.campaignStartDay+" / "+campaign.campaignEndDay);
			
		}
	
	
	}
	
	//
	
	public static ArrayList<MCampaign> GetAllCampaigns() {
		// TODO Auto-generated method stub

		return campaignList;
		
	}
	
	public static ArrayList<MChannel> GetAllChannels() {
		// TODO Auto-generated method stub
		ArrayList<MChannel> filteredList = new ArrayList<MChannel>();
		
		for(int i=0; i<channelList.size(); i++) {
			if(channelList.get(i).isActive)	filteredList.add(channelList.get(i));
		}
		return filteredList;
		
	}
	
	public static ArrayList<NiceDates> GetAllDates() {
		// TODO Auto-generated method stub
		return dateList;
		
	}

	public static void setSingleCampaign(int campaignID) {
		// TODO Auto-generated method stub
		if(campaignID>=campaignList.size())	return;
		
		for(int i=0; i<campaignList.size(); i++) {
			campaignList.get(i).isActive = (i==campaignID);
		}
		
	}
	public static void setAllCampaignsActive() {
		// TODO Auto-generated method stub
		
		for(int i=0; i<campaignList.size(); i++) {
			campaignList.get(i).isActive = true;
		}
		
	}
	public static void toggleChannel(int channelID) {
		// TODO Auto-generated method stub
		if(channelID>=channelList.size())	return;

		channelList.get(channelID).isActive = !channelList.get(channelID).isActive;
		
	}

	public static boolean channelIsActive(int channelID) {
		// TODO Auto-generated method stub
		if(channelID>=channelList.size())	return false;
		return channelList.get(channelID).isActive;
	}
	
	
	
	
	
	
	

}
