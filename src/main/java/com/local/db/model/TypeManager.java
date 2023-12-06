package com.local.db.model;

import java.util.regex.Pattern;

public class TypeManager {
    public static Object parseObjectByType(String object, Type dataType) throws NumberFormatException {
        String rgbRegex = "^rgb\\((0|255|25[0-4]|2[0-4]\\d|1\\d\\d|0?\\d?\\d),(0|255|25[0-4]|2[0-4]\\d|1\\d\\d|0?\\d?\\d),(0|255|25[0-4]|2[0-4]\\d|1\\d\\d|0?\\d?\\d)\\)$";
        switch (dataType) {
            case INTEGER:
                return Integer.valueOf(object);
            case CHAR:
                if(Pattern.matches(".?", object))
                    return object.charAt(0);
                else
                    throw new NumberFormatException("error");
            case REAL:
                return Double.valueOf(object);
            case STRING:
                return object;
            case CHARINVL:
                if(object == null || object == "") return object;
                String[] splitedCall = object.split("-");
                if(splitedCall.length != 2) throw new NumberFormatException("error");
                for(int j = 0; j < splitedCall.length; j++) {
                    if(splitedCall[j].length() != 1) throw new NumberFormatException("error");
                }
                return object;
            case STRINGINVL:
                if(object == null || object == "") return object;
                Integer cellLength = object.length();
                String cellString = object.substring(1, cellLength - 1);
                String[] splittedCell = cellString.split(";");
                for(int k = 0; k < splittedCell.length; k++) {
                    String range = splittedCell[k];
                    if(range.length() != 3 || !range.contains("-")) throw new NumberFormatException("error");
                }
                return object;
            default:
                throw new NumberFormatException("Unknown data format");
        }
    }
}

