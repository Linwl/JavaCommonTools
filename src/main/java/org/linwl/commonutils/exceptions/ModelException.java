package org.linwl.commonutils.exceptions;

/**
 * @program: JavaCommonTools
 * @description:
 * @author: linwl
 * @create: 2020-08-13 14:49
 **/
public class ModelException extends Exception {

    public ModelException() {
        super();
    }

    public ModelException(String messsage) {
        super(messsage);
    }

    public ModelException(String messsage, Throwable throwable) {
        super(messsage, throwable);
    }

    public ModelException(Throwable throwable) {
        super(throwable);
    }
}