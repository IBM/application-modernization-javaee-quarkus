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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SerializationUtil {
     
    // map from each type encountered in application: USER_LOCAL, USER_REMOTE
    // the map contains an entry for each user-defined class and its classification depends
    // on the cluster for which this code is rendered
    public final static HashMap<String, String> type_category_map;
    static {
        type_category_map = new HashMap<String, String>();
        type_category_map.put("org.pwte.example.domain.AbstractCustomer", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.domain.Address", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.domain.BusinessCustomer", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.domain.Category", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.domain.LineItem", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.domain.LineItemId", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.domain.Order", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.domain.Product", "USER_LOCAL_TYPE");
        type_category_map.put("org.pwte.example.domain.ResidentialCustomer", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.exception.CategoryDoesNotExist", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.exception.CustomerDoesNotExistException", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.exception.GeneralPersistenceException", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.exception.InvalidQuantityException", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.exception.NoLineItemsException", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.exception.OrderAlreadyOpenException", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.exception.OrderModifiedException", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.exception.OrderNotOpenException", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.exception.ProductDoesNotExistException", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.service.CustomerOrderServicesImpl", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.service.ProductSearchServiceImpl", "USER_LOCAL_TYPE");
        type_category_map.put("org.pwte.example.app.CustomerServicesApp", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.app.ProductPriceChanged", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.resources.CategoryResource", "USER_LOCAL_TYPE");
        type_category_map.put("org.pwte.example.resources.CustomerOrderResource", "USER_REMOTE_TYPE");
        type_category_map.put("org.pwte.example.resources.ProductResource", "USER_LOCAL_TYPE");
    }

    static final String USER_LOCAL_TYPE = "USER_LOCAL_TYPE";
    static final String USER_REMOTE_TYPE = "USER_REMOTE_TYPE";

    private static final Logger logger = CardinalLogger.getLogger(SerializationUtil.class);

    public static boolean isUserLocalType(String typ) {
        return type_category_map.get(typ) == USER_LOCAL_TYPE;
    }

    public static boolean isUserRemoteType(String typ) {
        return type_category_map.get(typ) == USER_REMOTE_TYPE;
    }

    public static boolean isUserType(String typ) {
        return type_category_map.containsKey(typ);
    }

    public static String encode(Object object, String type) {
        if (type == null) {
            type = object.getClass().getName();
        }
        try (
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos)
            ) {
            out.writeObject(object);
            final byte[] byteArray = bos.toByteArray();
            String encodedStr = Base64.getEncoder().encodeToString(byteArray) + "::" + type;
            return encodedStr;
        }
        catch(Exception e) {
            String msg = "Error serializing object of type " + type;
            logger.severe(msg);
            throw new CardinalException(msg, e);
        }
    }
    
    public static Object decode(String byteString) {
        byteString = byteString.split("::")[0];
        final byte[] bytes = Base64.getDecoder().decode(byteString);
        try (
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(bis)
            ) {
            return in.readObject();
        }
        catch(Exception e) {
            String msg = "Error deserializing string" + e.getMessage();
            logger.severe(msg);
            throw new CardinalException(msg, e);
        }
    }

    /**
     * Encodes the given object (of non-primitive type) to a string based on dynamic type of the object,
     * handling various cases:
     *     - null value: encoded to klu string constant
     *     - user type: encoded to reference ID
     *     - java.util.Collection type: recursive encoding of each collection element
     *     - java.util.Map type: recursive encoding of each (key, value) pair
     *     - array: recursive encoding of each array element
     *     - other cases (scalar non-user type, array of primitive type): serialized object
     * @param object Object to be encoded
     * @return Encoded string for object
     * @throws CardinalException if error occurs in encoding
     */
    public static String encodeWithDynamicTypeCheck(Object object) throws CardinalException {

        // for null object, generate string constant indicating null value
        if (object == null) {
            return "klu_null";
        }

        // get type of object
        Class<?> objType = object.getClass();

        // if user type, convert object to reference ID
        if (isUserType(objType.getName())) {
            logger.fine("Encoding user type to refID: "+objType.getName());
            if (!(object instanceof KluInterface)) {
                throw new CardinalException("User type does not implement KluInterface: "+object.getClass().getName());
            }
            KluInterface kluObj = (KluInterface)object;
            // user type (local or remote), assign reference ID
            if (isUserLocalType(objType.getName())) {
                // user-local type: add object to cluster object manager if needed
                if (kluObj.getKlu__referenceID() == "") {
                    kluObj.setKlu__referenceID(ClusterObjectManager.putObject(object));
                }
            }
            return kluObj.getKlu__referenceID();
        }
        
        // if collection, convert to collection of strings, then encode the collection
        if (object instanceof Collection) {
            Collection<String> strList = encodeObjectList((Collection<?>)object);
            return encode(strList, null);
        }

        // if map, convert to map of key, value strings, then encode the map
        if (object instanceof Map) {
            Map<String, String> strMap = encodeObjectMap((Map<?,?>)object);
            return encode(strMap, null);
        }

        // if array of non-primitive types, convert to array of strings, then encode the array
        if (objType.isArray()) {
            Object[] objArr = (Object[])object;
            logger.fine("Encoding Array: size="+objArr.length);
            ArrayList<Object> objList = new ArrayList<>(Arrays.asList(objArr));
            Collection<String> strList = encodeObjectList(objList);
            String[] strArr = strList.toArray(new String[0]);
            return encode(strArr, objType.getName());
        }
        
        // for other cases (scalar non-user type, array of primitive type), serialize the object
        return encode(object, null);
    }

    /**
     * Decodes the given string, based on dynamic type, into an object, handling various cases: 
     *     - klu string constant: decoded to null value
     *     - user-type: decoded to physical object (from cluster object manager) or proxy object
     *     - java.util.Collection: recursive decoding of each string in collection 
     *     - java.util.Map: recursive decoding of each (key, value) string pair
     *     - array: recursive decoding of each array element
     *     - other cases (scalar non-user type, array of primitive type): deserialized object
     * @param encodedStr string representing serialized object
     * @return Object deserialized from string
     * @throws CardinalException if error occurs in decoding
     */
    public static Object decodeWithDynamicTypeCheck(String encodedStr) throws CardinalException {

        // if string constant indicating null value, return null
        if (encodedStr.equals("klu_null")) {
            return null;
        }
        
        // get Class object for the type represented in encoded string
        String type = getTypeFromEncodedString(encodedStr);
        Class<?> objType = null;
        try {
            objType = Class.forName(type);
        }
        catch (Exception e) {
            String msg = "Error getting class object for " + type + "\n" + e.getMessage();
            logger.severe(msg);
            throw new CardinalException(msg, e);
        }

        // if user-local type: return object from cluster pbject manager
        if (isUserLocalType(objType.getName())) {
            logger.fine("Decoding user-local type: "+objType.getName());
            return ClusterObjectManager.getObject(encodedStr);
        }

        // if user-remote type: create and return proxy object
        if (isUserRemoteType(objType.getName())) {
            logger.fine("Decoding user-remote type: "+objType.getName());
            try {
                Constructor<?> proxyCtor = objType.getDeclaredConstructor(CardinalString.class);
                return proxyCtor.newInstance(new CardinalString(encodedStr));
            }
            catch (Exception e) {
                String msg = "Error creating proxy object for type: " + objType + "\n" + e.getMessage();
                logger.severe(msg);
                throw new CardinalException(msg, e);
            }
        }

        // deserialize string to create object
        Object object = decode(encodedStr);

        // if collection, convert to collection of objects and return
        if (object instanceof Collection) {
            return decodeStringList((Collection<String>)object, objType);
        }

        // if map, convert to map of key, value objects and return
        if (object instanceof Map) {
            return decodeStringMap((Map<String, String>)object, objType);
        }

        // if array, convert to array of objects and return
        if (objType.isArray()) {
            String[] strArr = (String[])object;
            logger.fine("Decoding Array: size="+strArr.length);
            try {
                // convert string array to list of strings and decode
                List<String> strList = Arrays.asList(strArr);
                Collection<?> objList = decodeStringList(strList, Class.forName("java.util.ArrayList"));

                // parse array dimension and component type from the type string
                // int arrdim = (int)type.chars().filter(ch -> ch == '[').count();
                int arrdim = type.lastIndexOf('[')+1;
                String[] typ = type.split("L");
                String arrtyp = typ[typ.length-1].split(";")[0];

                // convert to array of objects using dimension-specific helper method
                if (arrdim == 1) {
                    return objList.toArray(get1DimArrayOfType(arrtyp));
                }
                else if (arrdim == 2) {
                    return objList.toArray(get2DimArrayOfType(arrtyp));
                }
                else if (arrdim == 3) {
                    return objList.toArray(get3DimArrayOfType(arrtyp));
                }
                else if (arrdim == 4) {
                    return objList.toArray(get4DimArrayOfType(arrtyp));
                }
                else if (arrdim == 5) {
                    return objList.toArray(get5DimArrayOfType(arrtyp));
                }
                throw new CardinalException("Array dimensions higher than 5 not handled: "+arrdim);
            }
            catch (Exception e) {
                String msg = "Error decoding array type:\n" + e.getMessage();
                logger.severe(msg);
                throw new CardinalException(msg, e);    
            }
        }
        
        // for other cases (scalar non-user type, array of primitive type), return deserialized object
        return object;
    }

    /**
     * Encodes the given list of objects to a list of strings. It iterates over each object in list and
     * encodes it to a string by recursively calling encodeWithDynamicTypeCheck(), handling the cases where the object is null or an instance of user-local type,
     * user-remote type, or non-user type  and generating the suitable string in each case.
     * 
     * @param objList List of objects representing physical or proxy objects
     * @return
     */
    private static Collection<String> encodeObjectList(Collection<?> objList) {
        Collection<String> strList = null;
        String collClassName = objList.getClass().getName();
        logger.fine("Encoding collection: type="+collClassName+", size="+objList.size());
        try {
            if (collClassName.equals("java.util.Arrays$ArrayList")) {
                strList = new ArrayList<>();
            }
            else {
                strList = (Collection<String>)objList.getClass().newInstance();
            }
        }
        catch (Exception e) {
            String msg = "Error creating instance of " + collClassName + "\n" + e.getMessage();
            logger.severe(msg);
            throw new CardinalException(msg, e);
        }
        for (Object obj: objList) {
            String encObj = encodeWithDynamicTypeCheck(obj);
            strList.add(encObj);
        }
        return strList;
    }

    /**
     * Decodes the given list of srings to a list of objects. It iterates over each string in list,
     * which could represent a reference ID or a serialized non-user object, and converts it into
     * a physical or proxy object.
     * 
     * @param strList List of strings representing reference IDs or serialized objects
     * @return Collection of objects
     * @throws CardinalException if exception thrown while creating collection object via reflection
     */
    private static Collection<?> decodeStringList(Collection<String> strList, Class<?> type) {
        logger.fine("Decoding collection: type="+type.getName()+", size="+strList.size());
        try {
            Collection<Object> objList = (Collection<Object>)type.newInstance();
            for (String str: strList) {
                objList.add(decodeWithDynamicTypeCheck(str));
            }
            return objList;
        }
        catch (Exception e) {
            String msg = "Error creating object of type: " + type.getName() + "\n" + e.getMessage();
            logger.severe(msg);
            throw new CardinalException(msg, e);
        }
    }

    /***
     * Encodes the given map of objects to a map of strings. It iterates over each (key, value) pain in map and
     * encodes the key/value to a string, handling the cases where the object is null or an instance of user-local type,
     * user-remote type, or non-user type and generating the suitable string in each case.
     * 
     * @param objMap Map of objects representing physical or proxy objects
     * @return
     */
    private static Map<String, String> encodeObjectMap(Map<?, ?> objMap) {
        Map<String, String> strMap = null;
        String mapClassName = objMap.getClass().getName();
        logger.fine("Encoding map: type="+mapClassName+", size="+objMap.size());
        try {
            strMap = (Map<String, String>)objMap.getClass().newInstance();
        }
        catch (Exception e) {
            String msg = "Error creating instance of " + objMap.getClass().getName() + "\n" + e.getMessage();
            logger.severe(msg);
            throw new CardinalException(msg, e);
        }
        for (Object objKey: objMap.keySet()) {
            Object objVal = objMap.get(objKey);
            logger.fine("Encoding key/value of type "+objKey.getClass().getName()+
                ":"+objVal.getClass().getName());
            String strKey = encodeWithDynamicTypeCheck(objKey);
            String strVal = encodeWithDynamicTypeCheck(objVal);
            strMap.put(strKey, strVal);
        }
        return strMap;
    }

    /**
     * Decodes the given map of srings to a map of objects. It iterates over each (key, value) in list,
     * where the key/value could represent a reference ID or a serialized non-user object, and converts it into
     * a physical or proxy object.
     * 
     * @param strMap Map of strings representing reference IDs or serialized objects
     * @return Map of object pairs
     * @throws CardinalException if exception thrown while creating map object via reflection
     */
    private static Map<?, ?> decodeStringMap(Map<String, String> strMap, Class<?> type) {
        logger.fine("Decoding map: type="+type+", size="+strMap.size());
        try {
            Map<Object, Object> objMap = (Map<Object, Object>)type.newInstance();
            for (String strKey: strMap.keySet()) {
                Object objKey = decodeWithDynamicTypeCheck(strKey);
                Object objVal = decodeWithDynamicTypeCheck((String)strMap.get(strKey));
                objMap.put(objKey, objVal);
            }
            return objMap;
        }
        catch (Exception e) {
            String msg = "Error creating object of type: " + type.getName() + "\n" + e.getMessage();
            logger.severe(msg);
            throw new CardinalException(msg, e);
        }
    }

    private static <T> T[] get1DimArrayOfType(String typ) throws Exception {
        int[] dims = new int[] { 0 };
        return (T[])Array.newInstance(Class.forName(typ), dims);
    }

    private static <T> T[][] get2DimArrayOfType(String typ) throws Exception {
        int[] dims = new int[] { 0, 0 };
        return (T[][])Array.newInstance(Class.forName(typ), dims);
    }

    private static <T> T[][][] get3DimArrayOfType(String typ) throws Exception {
        int[] dims = new int[] { 0, 0, 0 };
        return (T[][][])Array.newInstance(Class.forName(typ), dims);
    }

    private static <T> T[][][][] get4DimArrayOfType(String typ) throws Exception {
        int[] dims = new int[] { 0, 0, 0, 0 };
        return (T[][][][])Array.newInstance(Class.forName(typ), dims);
    }

    private static <T> T[][][][][] get5DimArrayOfType(String typ) throws Exception {
        int[] dims = new int[] { 0, 0, 0, 0, 0 };
        return (T[][][][][])Array.newInstance(Class.forName(typ), dims);
    }


    private static String getTypeFromEncodedString(String encStr) {
        String[] tokens = encStr.split("::");
        return tokens[tokens.length-1];
    }

}