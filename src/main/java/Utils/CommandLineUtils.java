package Utils;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

import java.io.File;

public class CommandLineUtils {

    static public int ExecuteCommandLine( String command, String workingDir, File outputFile ) throws Exception {

        ProcessBuilder builder;
        builder = new ProcessBuilder( command );

        builder.redirectErrorStream(true);
        builder.redirectOutput(outputFile);
        builder.directory( new File( workingDir ) );
        builder.inheritIO();
        builder.environment().put("command", command );
        builder.environment().put("workingdir", workingDir );
        builder.environment().put("outputfile", outputFile.getAbsolutePath() );

        final Process process = builder.start();
        return process.waitFor();

    }

}
