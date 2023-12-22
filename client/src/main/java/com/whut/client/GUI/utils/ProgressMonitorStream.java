package com.whut.client.GUI.utils;

import java.awt.Frame;
import java.io.IOException;
import java.io.InterruptedIOException;

import javax.swing.ProgressMonitor;

public class ProgressMonitorStream{
    private ProgressMonitor monitor;
    private int nread = 0;

    public ProgressMonitorStream(String message, int size) {
        monitor = new ProgressMonitor(new Frame(), message, null, 0, size);
    }

    public int setProgress(int nr) throws IOException{

        if (nr > 0) monitor.setProgress(nread += nr);
        if (monitor.isCanceled()) {
            InterruptedIOException exc =
                                    new InterruptedIOException("progress");
            exc.bytesTransferred = nread;
            throw exc;
        }
        return nr;
    }

    public ProgressMonitor getProgressMonitor(){
        return monitor;
    }

}
