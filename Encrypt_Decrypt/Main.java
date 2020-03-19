/******** Made by Abdellah Ahbane *******/

import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import  java.util.*;

class SelectionAlgoContext {

    private SelectionAlgo algorithm;

    public SelectionAlgoContext(SelectionAlgo algorithm){
        setAlgorithm ( algorithm );
    }

    public void setAlgorithm(SelectionAlgo algorithm) {
        this.algorithm = algorithm;
    }

    public void setData(String data) {
        algorithm.setData ( data );
    }


    //This function is for Making a choice between Decryption or Encryption depending on mode
    public ArrayList<String> process(){
        return this.algorithm.process ();
    }
    public void fromInFiletoOutFile(String pathOfInputFile, String pathOfOutputFile, boolean dataExist) throws FileNotFoundException {
            ArrayList<String> outputResult = new ArrayList<String>();
            File file = new File("./"+pathOfInputFile);
            Scanner fileIn = new Scanner(file);
            PrintWriter fileOut = new PrintWriter("./" + pathOfOutputFile);

            if ( dataExist ) { // if the -data parameter has been passed
                    outputResult = this.process();
                    fileOut.print(outputResult.toString().replaceAll("\\[|\\]",""));
            } else { // if the -data parameter hasn't been passed to CL but we still have -in SO we ll read data from the inputFile
                if (file.isFile()) { // check if the file passed isn't a Directory
                    while (fileIn.hasNext ()) {
                            this.setData (fileIn.nextLine());
                            outputResult.addAll(this.process());
                    }
                    fileOut.print(outputResult.toString().replaceAll("\\[|,|\\]","")); // We write the result in the Output File because it exists.
                }
            }
            fileIn.close();
            fileOut.close();
    }

    public void fromInputFile(String pathOfInputFile, boolean dataExist) throws FileNotFoundException {
        ArrayList<String> outputResult = new ArrayList<String>();
        File file = new File("./" + pathOfInputFile);
        Scanner fileIn = new Scanner(file);

        if ( dataExist ) { // We've -data && -in passed to CL, we prefer -data over -in
            outputResult = this.process();
        } else { // We only have -in passed to CL
            if (file.isFile()) {
                while (fileIn.hasNext()) {
                    this.setData (fileIn.nextLine());
                    outputResult = this.process();
                }

                fileIn.close();
            }
        }
        System.out.print( outputResult.toString().replaceAll("\\[|,|\\]",""));
    }


    public void fromDatatoOutputFile(boolean pathOfOutputFileExist, String pathOfOutputFile ) throws FileNotFoundException {
        ArrayList<String> outputResult = new ArrayList<String>();
            outputResult = this.process();
        if(!pathOfOutputFileExist){ // The -out parameter hasn't been passed to CL which means we ll display the result in the standard output
            System.out.println(outputResult.toString().replaceAll("\\[|\\]",""));
        }
        else {// The -out has been passed to CL, So we ll write the result into the created file.
            PrintWriter fileOut = new PrintWriter("./" + pathOfOutputFile);
            fileOut.print(outputResult.toString().replaceAll("\\[|\\]",""));
            fileOut.close();
        }
    }
}

enum AlgoType{
    SHIFT,
    UNICODE
}
enum ModeType{
    ENC,
    DEC
}

abstract class init {
    ModeType mode; // mode encryption by default may be changed [-mode dec]
    String data ; // the Cipher text to encrypt or decrypt
    String pathOfInputFile ;
    String pathOfOutputFile ;
    AlgoType alg;
    int key ; // the key is 0 by default, may be changed  [-key number]

    public init( ModeType mode, String data, int key, String pathOfInputFile,String pathOfOutputFile){
        this.mode = mode == null? ModeType.ENC : mode;
        this.data = data == null ? "": data;
        this.key =  key;
        this.pathOfInputFile = pathOfInputFile == null ? "": pathOfInputFile;
        this.pathOfOutputFile = pathOfOutputFile == null ? "": pathOfOutputFile;
    }

}


interface SelectionAlgo {

    ArrayList<String> process();

    ArrayList<String> encryption();

    ArrayList<String> decryption();

    void setData(String data);
}

class  ShiftAlgo extends  init implements  SelectionAlgo {


    public ShiftAlgo(ModeType mode, String data, int key, String pathOfInputFile,String pathOfOutputFile){
        super( mode, data, key, pathOfInputFile, pathOfOutputFile );
        this.alg = AlgoType.SHIFT;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public ArrayList<String> process() {

        if (mode.equals ( ModeType.DEC )) {
            return  decryption ();
        } else if (mode.equals ( ModeType.ENC ) || mode == null) {
            return encryption ();
        }
        return new ArrayList<String> ( Collections.singleton ( "The chosen mode doesn't exist ! ") );
    }

    @Override
    public ArrayList<String> encryption() {
        char[] input = this.data.toCharArray ();
        ArrayList<String> Outdata = new ArrayList<String> ();

        for (int i = 0; i < input.length; i++) {
            if (input[i] >= 'A' && input[i] <= 'Z') {
                if (input[i] + key > 'Z') {
                    input[i] = (char) (input[i] + key - 'Z'  + 'A' - 1 );
                } else {
                    input[i] = (char) ((input[i] + key));
                }
            }else if( input[i] >= 'a' && input[i] <= 'z'){
                if (input[i] + key > 'z') {
                    input[i] = (char) (input[i] + key - 'z' + 'a' - 1 );
                }else {
                    input[i] = (char) ((input[i] + key));
                }
            }
        }
        Outdata.add (String.valueOf (input).trim ());
        return Outdata;
    }

    @Override
    public ArrayList<String> decryption() {
        char[] input = this.data.toCharArray ();
        ArrayList<String> Outdata = new ArrayList<String> ();

        for (int i = 0; i < input.length; i++) {
            if (input[i] >= 'A' && input[i] <= 'Z') {
                if (input[i] - key < 'A') {
                    input[i] = (char) ( 'Z' - ('A' - input[i] + key ) + 1 );
                } else {
                    input[i] = (char) ((input[i] - key));
                }
            }else if( input[i] >= 'a' && input[i] <= 'z'){
                if (input[i] - key < 'a') {
                    input[i] = (char) (  'z' - ('a' - input[i] + key ) +1 );
                }else {
                    input[i] = (char) ((input[i] - key));
                }
            }
        }

        Outdata.add ( String.valueOf ( input ).trim () );
        return Outdata;

    }
}

class  UnicodeAlgo extends init implements  SelectionAlgo{


    public UnicodeAlgo(ModeType mode, String data, int key, String pathOfInputFile,String pathOfOutputFile){
        super( mode, data, key, pathOfInputFile, pathOfOutputFile );
        this.alg = AlgoType.UNICODE;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public ArrayList<String> process() {

        if (this.mode.equals ( ModeType.DEC )) {
            return decryption ();
        } else if (this.mode.equals ( ModeType.ENC ) || mode == null) {
            return encryption ();
        }else{
            return new ArrayList<String> ( Collections.singleton ( "The chosen mode doesn't exist ! " ) );
        }
    }

    /************************** This function is for Encryption**************************/
    @Override
    public ArrayList<String> encryption() {
        char[] input = this.data.toCharArray ();
        ArrayList<String> Outdata = new ArrayList<String> ();

        for (int i = 0; i < input.length; i++) {
            input[i] = (char) ((input[i] + key));
        }
        Outdata.add(String.valueOf(input).trim());
        return  Outdata;

    }
    /************************** This function is for Decryption**************************/
    @Override
    public ArrayList<String> decryption() {
        char[] input = this.data.toCharArray ();
        ArrayList<String> Outdata = new ArrayList<String> ();

        for (int i = 0; i < input.length; i++) {
            input[i] = (char) ((input[i] - key));
        }
        Outdata.add(String.valueOf(input).trim());
        return  Outdata;
    }


}


class  AlgoFactory{
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

public class Main {



    /*************************The main function ***************************/
    public static void main(String[] args) {

        AlgoFactory  algoFactory = new AlgoFactory ();
        AlgoType alg = AlgoType.SHIFT;
        ModeType mode = ModeType.ENC;
        String data = "";
        int key = 0;
        String pathOfInputFile = "";
        String pathOfOutputFile = "";

        boolean pathOfInputFileExist = false, pathOfOutputFileExist = false, dataExist = false;
        // Boolean to know either the option have been passed to command-line or Not.

        try {
            // for-boucle to read the parameters passed to command-line (CL)
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-mode":
                        mode = ModeType.valueOf ( args[i + 1].toUpperCase () );
                        break;
                    case "-alg":
                        alg = AlgoType.valueOf (args[i + 1].toUpperCase ());
                        break;
                    case "-key":
                        key = Integer.parseInt(args[i + 1]);
                        break;
                    case "-data":
                        data = args[i + 1];
                        dataExist = !data.equals("");
                        break;
                    case "-in":
                        pathOfInputFile = args[i + 1];
                        pathOfInputFileExist = !pathOfInputFile.equals("");
                        break;
                    case "-out":
                        pathOfOutputFile = args[i + 1];
                        pathOfOutputFileExist = !pathOfOutputFile.equals("");
                        break;
                }
            }

            SelectionAlgo selectionAlgo = algoFactory.getAlgo (alg, mode, data, key, pathOfInputFile, pathOfOutputFile);
            SelectionAlgoContext selectionAlgoContext = new SelectionAlgoContext (selectionAlgo);

            // The case where we've the parameters -in && -out have been passed to CL
            if (pathOfInputFileExist && pathOfOutputFileExist) {
                        selectionAlgoContext.fromInFiletoOutFile ( pathOfInputFile, pathOfOutputFile, dataExist );
            }
            else { // The case where One of two the parameters -in || -out haven't been passed to CL
                if (pathOfInputFileExist) { // Case where -in has been passed to CL
                            selectionAlgoContext.fromInputFile ( pathOfInputFile, dataExist );

                }else{// the -in hasn't been passed we will take -data otherwise we ll consider -data="" its default value
                    selectionAlgoContext.fromDatatoOutputFile ( pathOfOutputFileExist, pathOfOutputFile);
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
