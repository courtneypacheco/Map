import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.io.*;

public class pqDistances
{

    private Comparator<Double> comparator; 
    private PriorityQueue<Double> queue; 
    private HashMap<Double,String> distance_mapping; //maps distances to the distances in the priority queue "queue"
    private size;
    private HashMap<String, HashMap<String, ArrayList>>  States;


    /* Priority Queue structure
       @param size = size of the priority queue (i.e., max # of values the queue can hold)
       @param state = state found from Rtree
       @param latitude = origin point (x)
       @param longitude = origin point (y)
    */
    pqDistances(int size, String state, double latitude, double longitude){
         this.comparator = new pqComparator();
         this.queue = new PriorityQueue<Double>(size, this.comparator);
         this.distance_mapping = new HashMap<Double,String>();
         this.size = size;

	 loadMap();

         //Find state in hashmap
         HashMap<String, ArrayList> Counties = States.get(state);


         String county_name;
         ArrayList county_temp; //current county in the for loop
         double[] center_point;
         double distance;

         for (Object county : Counties){
              county_name = (String) county;
              county_temp = (ArrayList) Counties.get(county_name); //the ArrayList for the current county (contains MBR data)

              //Find center point of the county
              center_point = getCenter(county_temp);

              //Calculate distance
              distance = calculateDistance(center_point, longitude, latitude);

              //Insert into the queue
              addDistanceToQueue(distance);

              //Insert into hashmap to create a mapping between "queue" and "Counties"
              distance_mapping.put(distance,county_name);
         }

    }


    //Loads map data from 'latest_map.dat'
    private void loadMap(){
         File file = new File("latest_map.dat");
         FileInputStream f = new FileInputStream(file);
         ObjectInputStream s = new ObjectInputStream(f);
         HashMap<String, HashMap<String, ArrayList>> fileObj2 = (HashMap<String, HashMap<String, ArrayList>>) s.readObject();
         s.close();

         this.States = fileObj2;
    }



    //Get coordinates for the center of the county
    public double[] getCenter(ArrayList points){
        double mid_x = (points.get(0) + points.get(2)) / 2;
        double mid_y = (points.get(1) + points.get(3)) / 2;
        double[] center = { mid_x , mid_y };
        return center;
    }


    //Get distance
    public double calculateDistance(double[] center, double longitude, double latitude){
        double x = (longitude - center[0]) * Math.cos((latitude + center[1])/2);
        double y = (latitude - center[1]);
        return Math.sqrt(x*x + y*y);
    }



    //Add distance to queue
    private addDistanceToQueue(double distance){
         this.queue.add(distance);
    }

    //Print everything from queue
    private printQueue(){

         PriorityQueue<Double> temp_queue = new PriorityQueue<Double>(this.size, this.comparator);

         double d;
         String county_name;
         while (temp_queue.size() != 0)
              d = temp_queue.remove();
             
              //Now find the distance 'd' in the hashmap to figure out which county 'distance' corresponds to
              county_name = (String)distance_mapping.get(d);

              System.out.println(county_name);
    }




}
