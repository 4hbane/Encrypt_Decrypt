package Classes;

import java.util.ArrayList;
import java.util.Collections;

class  ShiftAlgo extends Init implements  SelectionAlgo {


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
