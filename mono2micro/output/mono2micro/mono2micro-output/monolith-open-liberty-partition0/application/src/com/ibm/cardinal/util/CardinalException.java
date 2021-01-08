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

public class CardinalException extends RuntimeException {

    public static final int APPLICATION_EXCEPTION = 555;

    public CardinalException(String msg, Throwable t) {
        super(msg, t);
    }

    public CardinalException(String msg) {
        super(msg);
    }

}