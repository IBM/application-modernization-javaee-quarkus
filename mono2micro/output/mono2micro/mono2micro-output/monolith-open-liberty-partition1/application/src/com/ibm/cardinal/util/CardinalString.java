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

public class CardinalString {

    private String string;

    public CardinalString(String str) {
        this.string = str;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String str) {
        this.string = str;
    }

    public int length() {
        return this.string.length();
    }

    public boolean equals(Object obj) {
        return this.string.equals(obj);
    }

    public String toString() {
        return this.string.toString();
    }

    @Override
    public int hashCode() {
        return this.string.hashCode();
    }
     
}