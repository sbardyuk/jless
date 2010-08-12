package com.bazaarvoice.jless;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public final class TestUtils {

    private static File _sLogFile;
    private static PrintStream _sLogStream;

    public static PrintStream getLog() {
        if (_sLogStream == null) {
            try {
                _sLogFile = new File("test.log");
                _sLogStream = new PrintStream(new FileOutputStream(_sLogFile));
            } catch (IOException e) {
                System.err.println(e);
                e.printStackTrace();
            }            
        }
        return _sLogStream;
    }

    public static void flushLog() {
        if (_sLogStream == null) {
            return;
        }
        _sLogStream.flush();
    }
}
