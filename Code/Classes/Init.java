package Classes;

abstract class Init {
    ModeType mode; // mode encryption by default may be changed [-mode dec]
    String data ; // the Cipher text to encrypt or decrypt
    String pathOfInputFile ;
    String pathOfOutputFile ;
    AlgoType alg;
    int key ; // the key is 0 by default, may be changed  [-key number]

    public Init(ModeType mode, String data, int key, String pathOfInputFile, String pathOfOutputFile){
        this.mode = mode == null? ModeType.ENC : mode;
        this.data = data == null ? "": data;
        this.key =  key;
        this.pathOfInputFile = pathOfInputFile == null ? "": pathOfInputFile;
        this.pathOfOutputFile = pathOfOutputFile == null ? "": pathOfOutputFile;
    }

}
