/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing;

import com.fclub.tpd.batch.importing.dto.ImportResult;

/**
 * 
 * @author likaiping
 * @version $Id: MutiImportTask.java, v 0.1 Oct 29, 2012 1:47:43 PM likaiping Exp $
 */
public interface MutiImportTask extends ImportTask{
    
    ImportResult dispense();
}
