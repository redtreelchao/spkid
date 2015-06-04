/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing;

import com.fclub.tpd.batch.importing.enums.ImportStatus;

/**
 * 任务生命周期接口
 * @author likaiping
 * @version $Id: TaskLifeCycle.java, v 0.1 Oct 25, 2012 5:21:10 PM likaiping Exp $
 */
public interface TaskLifeCycle {

    short getProgress();
    
    ImportStatus getState();
}
