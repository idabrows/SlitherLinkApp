package model;
import com.quantego.clp.*;

public class Calculator{

    public void calculate(){
        CLP model = new CLP();
        CLPVariable x = model.addVariable().lb(-3).ub(3);
        CLPVariable y = model.addVariable().free();
        model.createExpression().add(3,x).add(4,y).leq(10);
        model.printModel();
    }

}