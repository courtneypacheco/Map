import java.util.Comparator;

public class pqComparator implements Comparator<Double>
{

    //d1 and d2 are distances 
    @Override
    public int compare(double d1, double d2)
    {

        if (d1 > d2)
        {
            return -1;
        }
        if (d2 < d1)
        {
            return 1;
        }
        return 0;
    }
}
