/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing;

import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;

/**
 * 
 * @author likaiping
 * @version $Id: ImportTaskManager.java, v 0.1 Oct 25, 2012 4:32:28 PM likaiping Exp $
 */
public interface ImportTaskManager {

    ImportResult execute(ImportContext importContext);
}
