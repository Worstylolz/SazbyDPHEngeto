package src;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DphListWorker {
    private FileLoader fileLoader;

    private List<StateDPHInformation> stateDPHInformationList = new ArrayList<>();

    public List<StateDPHInformation> getStateDPHInformationList() {
        return stateDPHInformationList;
    }

    public void setStateDPHInformationList(List<StateDPHInformation> stateDPHInformationList) {
        this.stateDPHInformationList = stateDPHInformationList;
    }

    public void transformListWithDPHToDPHObjects(String filePath) throws FileNotFoundException {
        if (fileLoader==null) {
            fileLoader = new FileLoader(filePath);
        }else {
            fileLoader.setFilePath(filePath);
        }
        List<String> beforeLoadToObjectsList=fileLoader.listWithRowsOfCsvFolder();
        for (int i = 0; i < beforeLoadToObjectsList.size(); i++) {
            try{
                String[] poleForWork=beforeLoadToObjectsList.get(i).replace(",",".").split("\t");
                stateDPHInformationList.add(new StateDPHInformation(poleForWork[0],poleForWork[1],Float.parseFloat(poleForWork[2]),Float.parseFloat(poleForWork[3]),Boolean.parseBoolean(poleForWork[4])));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Řádek"+i+" ve vašem nahraném souboru, obsahuje špatně zformátovaná data a nebylo možné jej přidat. Prosím o upravení tohoto řádku do správného formátu.");
            }
        }
    }
}
