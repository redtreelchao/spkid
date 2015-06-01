/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.dto;

import java.util.List;

/**
 * 
 * @author likaiping
 * @version $Id: ConsoleOut.java, v 0.1 Oct 31, 2012 10:17:38 AM likaiping Exp $
 */
public class ConsoleOut {

    List<String> list;
    boolean      finish;
    boolean      empty;

    /**
     * 
     */
    public ConsoleOut() {
        super();
    }

    /**
     * @param empty
     */
    public ConsoleOut(boolean empty) {
        super();
        this.empty = empty;
    }

    /**
     * @param list
     * @param finish
     */
    public ConsoleOut(List<String> list, boolean finish) {
        super();
        this.list = list;
        this.finish = finish;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

}
