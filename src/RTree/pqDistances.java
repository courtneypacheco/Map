import java.util.Comparator;
import java.util.PriorityQueue;

public class pqDistances
{

    private Comparator<Double> comparator; 
    private PriorityQueue<Double> queue; 
    private size;


    /* Priority Queue structure
       @param size = size of the priority queue (i.e., max # of values the queue can hold)
    */
    pqDistances(int size){
         this.comparator = new pqComparator();
         this.queue = new PriorityQueue<Double>(size, this.comparator);
         this.size = size;
    }

    //Add distance to queue
    private addDistanceToQueue(double distance){
         this.queue.add(distance);
    }

    //Print everything from queue
    private printQueue(){

         PriorityQueue<Double> temp_queue = new PriorityQueue<Double>(this.size, this.comparator);

         while (temp_queue.size() != 0)
              System.out.println(temp_queue.remove());

    }


}
