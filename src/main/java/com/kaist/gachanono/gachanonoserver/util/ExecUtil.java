package com.kaist.gachanono.gachanonoserver.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

@Scope(value = "singleton")
public class ExecUtil {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected ExecUtil() { super(); }
    protected static class LazyHolder {
        private static final ExecUtil INSTANCE = new ExecUtil();
    }
    public static ExecUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    private int exitValue = 0;
    private List<String> stdout = new ArrayList<String>();
    private List<String> stderr = new ArrayList<String>();

    private class CollectingLogOutputStream extends LogOutputStream {
        private final List<String> lines = new LinkedList<String>();
        @Override
        protected void processLine(String line, int level) {
            lines.add(line);
        }
        public List<String> getLines() {
            return lines;
        }
    }

    public void exec(String command) throws IOException, InterruptedException {

        this.exitValue = 0;
        this.stdout.clear();
        this.stderr.clear();

        DefaultExecutor exec = new DefaultExecutor();

        CommandLine commandLine = null;
        if(OSValidator.isWindows()) {
            commandLine = new CommandLine("cmd.exe");
            commandLine.addArguments(new String[]{ "/c", command }, false);
        } else {
            commandLine = new CommandLine("/bin/sh");
            commandLine.addArguments(new String[]{ "-c", command }, false);
        }

        CollectingLogOutputStream so = new CollectingLogOutputStream();
        CollectingLogOutputStream se = new CollectingLogOutputStream();
        PumpStreamHandler psh = new PumpStreamHandler(so, se);
        exec.setStreamHandler(psh);

        try{
            this.exitValue = exec.execute(commandLine);
            this.stdout = so.getLines();
        } catch (ExecuteException ee) {
            this.exitValue = ee.getExitValue();
            this.stderr = se.getLines();
        } catch (IOException ioe) {
            this.exitValue = -1;
            this.stderr = se.getLines();
        }
    }

    public int getExitValue() {
        return this.exitValue;
    }

    public List<String> getStdout() {
        return this.stdout;
    }

    public List<String> getStderr() {
        return this.stderr;
    }
    
    public String getStdOutString() {
        return String.join("\n", this.stdout);
    }

    public String getStdErrString() {
        return String.join("\n", this.stderr);
    }

}
