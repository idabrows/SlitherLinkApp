package controller;



public class FileCommunicator {
    private MyFileReader FR;
    private MyFileWriter FW;

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

    public void setFR(MyFileReader FR) {
        this.FR = FR;
    }

    public MyFileWriter getFW() {
        return FW;
    }

    public void setFW(MyFileWriter FW) {
        this.FW = FW;
    }

    public static FileCommunicator getF() {
        return f;
    }

    public static void setF(FileCommunicator f) {
        FileCommunicator.f = f;
    }
}
