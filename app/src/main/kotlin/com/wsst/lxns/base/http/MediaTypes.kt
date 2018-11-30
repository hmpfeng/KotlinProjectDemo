package com.wsst.lxns.base.http

import okhttp3.MediaType

/**
 * Created by 谢岳峰 on 2018/9/11.
 */
object MediaTypes {
    val APPLICATION_ATOM_XML_TYPE = MediaType.parse("application/atom+xml;charset=utf-8")
    val APPLICATION_FORM_URLENCODED_TYPE = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8")
    val APPLICATION_JSON_TYPE = MediaType.parse("application/json;charset=utf-8")
    val APPLICATION_OCTET_STREAM_TYPE = MediaType.parse("application/octet-stream")
    val APPLICATION_SVG_XML_TYPE = MediaType.parse("application/svg+xml;charset=utf-8")
    val APPLICATION_XHTML_XML_TYPE = MediaType.parse("application/xhtml+xml;charset=utf-8")
    val APPLICATION_XML_TYPE = MediaType.parse("application/xml;charset=utf-8")
    val MULTIPART_FORM_DATA_TYPE = MediaType.parse("multipart/form-data;charset=utf-8")
    val TEXT_HTML_TYPE = MediaType.parse("text/html;charset=utf-8")
    val TEXT_XML_TYPE = MediaType.parse("text/xml;charset=utf-8")
    val TEXT_PLAIN_TYPE = MediaType.parse("text/plain;charset=utf-8")
    val IMAGE_TYPE = MediaType.parse("image/*")
    val WILDCARD_TYPE = MediaType.parse("*/*")
}
