/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.remote;

import java.util.List;

import com.fclub.common.log.LoggerUtil;

/**
 * 分发策略接口 
 * @author likaiping
 * @version $Id: DispatchStragety.java, v 0.1 Jul 17, 2013 12:07:06 PM likaiping Exp $
 */
public interface DispatchStragety {

    /**
     * 由具体的分发策略分发任务
     * 
     * @return 返回未分发出的目录列表
     */
    List<String> dispatchTask(ServerManager manager, String uuid, String channel, List<String> dirs, LoggerUtil logger);

}
