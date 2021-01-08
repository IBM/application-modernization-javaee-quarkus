// # *****************************************************************
// #
// # Licensed Materials - Property of IBM
// #
// # (C) Copyright IBM Corp. 2019, 2020. All Rights Reserved.
// #
// # US Government Users Restricted Rights - Use, duplication or
// # disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
// #
// # *****************************************************************
package com.ibm.cardinal.util;

import java.util.Hashtable;
import java.util.UUID;
import java.util.Set;
import java.util.logging.Logger;

public class ClusterObjectManager {

    // cluster id
    private final static String clusterId = "partition1";
    
    // Object Table
    private final static Hashtable<String, Object> refTbl = new Hashtable<String, Object>();

    // Reference count to object
    private final static Hashtable<String, Integer> cntTbl = new Hashtable<String, Integer>();

    public static String putObject(Object instObj) {
        String refId;
        do {
            refId = clusterId + "::" + UUID.randomUUID().toString() + "::" + instObj.getClass().getName();
        } while (refTbl.get(refId) != null);

        refTbl.put(refId, instObj);
        cntTbl.put(refId, Integer.valueOf(1));
        logger.fine("New object added to COM: " + refId + "; Count: 1");
        return refId;
    }

    private static final Logger logger = CardinalLogger.getLogger(ClusterObjectManager.class);

    public static Object getObject(String refId) {
        try {
            return refTbl.get(refId);
        }
        catch (Throwable t) {
            String msg = "refID "+refId+" not found in object table";
            logger.severe(msg);
            throw new CardinalException(msg, t);
        }
    }

    public static void incObjectCount(String refId) {
        if (cntTbl.get(refId) == null) {
            cntTbl.put(refId, Integer.valueOf(1));
            logger.fine("Created RefID: " + refId + "; Count: 1");
        }
        else {
            Integer newCount = cntTbl.get(refId) + 1;
            cntTbl.put(refId, newCount);
            logger.fine("Incremented RefID: " + refId + "; Count: " + newCount);
        }
    }

    public static void decObjectCount(String refId) {
        if (cntTbl.get(refId) == null) {
            logger.severe("RefId " + refId + "does not exist");
        }
        else {
            Integer newCount = cntTbl.get(refId) - 1;
            logger.fine("Decremented RefID: " + refId + "; Count: " + newCount);
            if (newCount == 0) {
                cntTbl.remove(refId);
                refTbl.remove(refId);
                logger.fine("Removing reference from object map");
            }
        }
    }

    public static long getPID() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return Long.parseLong(processName.split("@")[0]);
    }

    public static void printTable() {
        Set<String> keys = refTbl.keySet();
        for (String key : keys) {
            logger.fine(key);
        }
    }

}