package GUI_v2;

import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;

public class GUI_v2 extends JFrame {
	public static void main(String arg[]) throws InterruptedException, MalformedURLException, URISyntaxException {
		System.setProperty("teamdev.license.info", "true");
		final Browser myBrowser = BrowserFactory.create();
		JFrame myFrame = new JFrame("EC504 Map Project");
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.add(myBrowser.getView().getComponent(), BorderLayout.CENTER);
		myFrame.setSize(800, 800);
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
		
		String path;
        try {
	        path = GUI_v2.class.getResource("/GUI_v2/map_v2.html").toURI().toURL().toString();
        	//path = "C:/Users/Minnie/Development/Map/src/GUI/map.html";
	        System.out.println(path);
	        myBrowser.loadURL(path);
        } catch (MalformedURLException e) {
        	//TODO Auto-generated catch block
        	e.printStackTrace();
        }
		//myBrowser.loadHTML("<!DOCTYPE html><html><head><style type=\"text/css\">body{position:relative; width:100%;height:100%;top:0px; left:0px;margin-left:auto; margin-right:auto; background-color:white;  }  .divTop {position:relative;width:100%;height:100px;top:0px; left:0px;margin-left:auto; margin-right:auto; background-color:white;  }  .divMiddle{position:relative;width:100%;height:500px;top:0px;left:0px;margin-left:auto;margin-right:auto;  }  .divBottom{position:relative;width:100%;height:50px;top:0px;left:0px;margin-left:auto;margin-right:auto;  }  h1 {font-size: 3em;font-weight: bold;font-family: sans-serif;margin-top: 0.1em;margin-left: 0;margin-right: 0;margin-bottom: 0;  }  p {font-size: 0.7em;font-family: sans-serif;margin-top: 0.1em;margin-left: 0;margin-right: 0;  }  </style>  </head><script  src=\"http://maps.googleapis.com/maps/api/js?sensor=false\"></script><script>var map;  var markers = [];function initialize()  {var mapOptions = {  center: new google.maps.LatLng(39, -99),zoom: 3,mapTypeId:google.maps.MapTypeId.ROADMAP };map = new google.maps.Map(document.getElementById(\"myMap\"), mapOptions);google.maps.event.addListener(map, 'click', function(event) { getCoordinates(event.latLng) });  }function getCoordinates(location) {var marker = new google.maps.Marker({position: location,   map: map});markers.push(marker);var infowindow = new google.maps.InfoWindow({   content: '<div id=\"content\">' + '<h2 id=\"heading\">Coordinates</h2>' + '<p><b>Latitude: </b>' + location.lat() + '<p><b>Longitude: </b> ' + location.lng() + '</div>' });infowindow.open(map, marker);  }function deleteMarkers() {for (var i = 0; i < markers.length; i++) {  markers[i].setMap(null);}markers= [];  }google.maps.event.addDomListener(window, 'load', initialize);</script><body>  <div class=\"divTop\" ><h1>EC 504 Map Project</h1><input onclick=\"deleteMarkers();\" type=button value=\"Delete Markers\" style=\"position: relative; top:0px; left:1200px\">  </div>  <div class=\"divMiddle\" ><div id=\"myMap\" style=\"width:100%;height:500px\"></div>  </div>  <div class=\"divBottom\" ><p align=\"center\">Boston University EC 504 Fall 2014 Project | Copyright 2014 Minnie Kim, Courtney Pacheco, Amit Sinha, Jeannie Trinh<p> </div></body></html>");
		//myBrowser.loadURL("C:/Users/Minnie/Development/Map/src/GUI/map.html");
	}
}