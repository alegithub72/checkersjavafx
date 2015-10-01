/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import javafx.beans.value.WritableValue;

/**
 *
 * @author ale2s_000
 */
public class WriteFrame implements WritableValue<Object> {

    PedinaWhite p;
    int frame;
    public WriteFrame(PedinaWhite p) {
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
