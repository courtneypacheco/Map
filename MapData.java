package loadmapdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

class MapData{
    
    protected static String filename;
    protected HashMap States = new HashMap<String,HashMap<String,ArrayList>>();
    
    MapData(String filename) throws IOException{
        this.filename = filename;
        loadFile();
    }
    
    private void loadFile() throws FileNotFoundException, IOException{
        
        //Initialize variables
        String line, state, county, longitude, latitude;
        String[] data;
        String delimiter = "\t";
        
        //Read from file
        FileReader file = new FileReader(this.filename);
        BufferedReader BF = new BufferedReader(file);
        
        int line_number = 0;
        int max_lines = 10;
        while ((line = BF.readLine()) != null){

            data = line.split(delimiter);
            
            //Get state, county, longitude, and latitude
            state = data[0];
            county = data[1];
            longitude = data[2];
            latitude = data[3];
            
            //Add data to hashmap
            if (line_number > 0){
                if (this.States == null || this.States.get(state) == null){

                    //This hash map "counties" will be stored in the "State" hashmap
                    HashMap Counties = new HashMap<String, ArrayList>();

                    //This arraylist will contain the coordinates for said county
                    ArrayList coordinates = new ArrayList<Double>();
                    coordinates.add(longitude);
                    coordinates.add(latitude);

                    //Now add the county & arraylist to "Counties"
                    Counties.put(county, coordinates);

                    //Add Counties to "States" HashMap
                    this.States.put(state, Counties);
                }
                else{

                    //Get the info for the existing state name
                    HashMap county_info = (HashMap)this.States.get(state);

                    ArrayList coordinates = new ArrayList<Double>();
                    coordinates.add(longitude);
                    coordinates.add(latitude);

                    if (county_info.get(county) == null){
                        county_info.put(county, coordinates);
                    }
                    else{

                        //Get the existing coordinates from the hashmap
                        ArrayList existing_coordinates;
                        existing_coordinates = (ArrayList) county_info.get(county);

                        //Append the new coordinates
                        existing_coordinates.add(longitude);
                        existing_coordinates.add(latitude);

                        //Update hashmap
                        county_info.put(county,existing_coordinates);
                    }

                    //Update
                    this.States.put(state, county_info);
                }
            }
            
            line_number++;
        }
        
        //Save to file
        saveFile();
        
        /*
        //This prints out all the states. Leave this commented. Just for debugging purposes.
        for (Object key: this.States.keySet()) {
            System.out.println(key);
            HashMap curr_key = (HashMap)this.States.get(key);
            for (Object val: curr_key.keySet()){
                System.out.println("   - " + val);
            }
        }
        */
    }
    
    private void saveFile() throws FileNotFoundException, IOException{
        
        //Create the filename (just concatenate filename and .dat)
        String file = "latest_map.dat";
        File output = new File(file);
        
        //Now save
        FileOutputStream fos = new FileOutputStream(output); 
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.States);
        oos.close();
    }
    
}
