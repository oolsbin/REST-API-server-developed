package com.example.demo.config;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class RepairWrongUrlParamEncoding {
    private static String restoredParamToOriginal;

    /**
    * @param wrongUrlParam
    * @return Repaired url param (UTF-8 encoded)
    * @throws UnsupportedEncodingException
    */
    public static String repair(String wrongUrlParam) throws UnsupportedEncodingException {
    /* First step: encode the incorrectly converted UTF-8 strings back to 
                  the original URL format
    */
    restoredParamToOriginal = URLEncoder.encode(wrongUrlParam, "ISO-8859-1");

    /* Second step: decode to UTF-8 again from the original one
    */
    return URLDecoder.decode(restoredParamToOriginal, "UTF-8");
    }
}