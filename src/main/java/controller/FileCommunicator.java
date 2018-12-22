package controller;



public class FileCommunicator {
    private MyFileReader FR;


    public MyFileReader getFR() {
        return FR;
    }
    private static FileCommunicator f;
    private FileCommunicator(){
        FR=MyFileReader.getInstance();
    }
    public static FileCommunicator getInstance(){
        if(f==null){
            f=new FileCommunicator();
        }
        return f;
    }

}
