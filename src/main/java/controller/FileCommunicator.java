package controller;


public class FileCommunicator {
    private final MyFileReader FR;
    private final MyFileWriter FW;
    private static FileCommunicator f;

    private FileCommunicator(){
        FR=MyFileReader.getInstance();
        FW=MyFileWriter.getInstance();
    }
    public static FileCommunicator getInstance(){
        if(f==null){
            f=new FileCommunicator();
        }
        return f;
    }

    public MyFileReader getFR() {
        return FR;
    }

    public MyFileWriter getFW() {
        return FW;
    }

}
