/******** Made by Abdellah Ahbane *******/

import Classes.*;

import java.io.FileNotFoundException;


public class Main {



    /*************************The main function ***************************/
    public static void main(String[] args) {

        AlgoFactory algoFactory = new AlgoFactory ();
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
