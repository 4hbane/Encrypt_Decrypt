package Classes;

public class  AlgoFactory{
    public SelectionAlgo getAlgo(AlgoType alg, ModeType mode, String data, int key, String pathOfInputFile,String pathOfOutputFile) {

        if (alg.equals(AlgoType.SHIFT) || alg == null ) {
            return  new ShiftAlgo ( mode, data, key, pathOfInputFile, pathOfOutputFile );
        }
        else if(alg.equals(AlgoType.UNICODE)){
            return  new UnicodeAlgo (mode, data, key, pathOfInputFile, pathOfOutputFile);
        }
        return (SelectionAlgo) new Exception ( "The given algorithm doesn't exist! " );
    }
}
