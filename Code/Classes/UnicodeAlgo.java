package Classes;

import java.util.ArrayList;
import java.util.Collections;

class  UnicodeAlgo extends Init implements  SelectionAlgo{


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
