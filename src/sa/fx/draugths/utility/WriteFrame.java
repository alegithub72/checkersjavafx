/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.utility;

import javafx.beans.value.WritableValue;
import sa.fx.draugths.pieces.efa.Soldier;

/**
 *
 * @author  Alessio Sardaro
 */
public class WriteFrame implements WritableValue<Object> {

    Soldier p;
    int frame;
    public WriteFrame(Soldier p) {
       this.p=p;
    }

    
    @Override
    public Object getValue() {
       return frame;
    }

    @Override
    public void setValue(Object value) {
        p.setFrame(frame);
    }
    
}
