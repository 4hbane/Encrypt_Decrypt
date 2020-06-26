package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SelectionAlgoContext {

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
