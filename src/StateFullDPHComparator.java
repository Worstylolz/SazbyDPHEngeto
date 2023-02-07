package src;

import java.util.Comparator;

public class StateFullDPHComparator implements Comparator<StateDPHInformation> {

    @Override
    public int compare(StateDPHInformation o1, StateDPHInformation o2) {
        return o1.getFullDPH().compareTo(o2.getFullDPH());
    }
}
