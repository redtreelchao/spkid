package com.fclub.erp.remote.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fclub.erp.dto.goods.BatchResultVo;

public class ProcessStatus implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9016499088488429440L;

    private Integer lossTimes = 0;
    private boolean isFinished = false;
    private boolean errorflag = false;
    private Integer finishNum = 0;
    private Integer processCount = 0;
    private List<String> successDirs = new ArrayList<>();
    private List<String> processDirs = new ArrayList<>();
    private List<String> totalDirs;
    private List<String> logs = new CopyOnWriteArrayList<>();
    private List<String> logback = new CopyOnWriteArrayList<>();
    
    private List<BatchResultVo> results = new CopyOnWriteArrayList<>();

    public void increaseProcess() {
        this.processCount++;
    }
    
    public void increaseLossTimes() {
        this.lossTimes++;
    }

    public Integer getLossTimes() {
        return lossTimes;
    }

    public void setLossTimes(Integer lossTimes) {
        this.lossTimes = lossTimes;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public boolean isErrorflag() {
        return errorflag;
    }

    public void setErrorflag(boolean errorflag) {
        this.errorflag = errorflag;
    }

    public Integer getProcessCount() {
        return processCount;
    }

    public void setProcessCount(Integer processCount) {
        this.processCount = processCount;
    }

    public List<String> getSuccessDirs() {
        return successDirs;
    }

    public void setSuccessDirs(List<String> successDirs) {
        this.successDirs = successDirs;
    }

    public List<String> getProcessDirs() {
        return processDirs;
    }

    public void setProcessDirs(List<String> processDirs) {
        this.processDirs = processDirs;
    }

    public List<String> getTotalDirs() {
        return totalDirs;
    }

    public void setTotalDirs(List<String> totalDirs) {
        this.totalDirs = totalDirs;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    public List<String> getLogback() {
        return logback;
    }

    public void setLogback(List<String> logback) {
        this.logback = logback;
    }

    public List<BatchResultVo> getResults() {
        return results;
    }

    public void setResults(List<BatchResultVo> results) {
        this.results = results;
    }

}
