package com.ibm.cardinal.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardinalLogger {

    /**
     The default log level prints warning messages only. The logging messages can be configured to
     these levels (in increasing order of verbosity):

        - Level.OFF: This turns off all logging

        - Level.SEVERE: Prints only those messages that pertain to failures in inter-partition
            communication

        - Level.WARNING: In addition to Level.SEVERE messages, prints messages about service calls
            in proxy methods that result in WebApplicationException being thrown. The cause of such
            an exception may be a wrapped application exception

        - Level.INFO: In addition to Level.SEVERE and Level.WARNING messages, prints messages that
            provide information about inter-partition service requests and responses

        - Level.FINE: This enables more verbose logging, where details of object serialization
            and deserialization across partitions, along with details of operations
            on the partition object manager, are printed

        - Level.ALL: This prints all log messages, irrespective of logging level
     */
    private static Level DEFAULT_LOG_LEVEL = Level.WARNING;

    public static Logger getLogger(Class<?> cls) {
        Logger logger = Logger.getLogger(cls.getName());
        Handler handler = new ConsoleHandler();
        logger.addHandler(handler);
        logger.setLevel(DEFAULT_LOG_LEVEL);
        logger.setUseParentHandlers(false);
        return logger;
    }

}