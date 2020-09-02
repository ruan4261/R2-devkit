package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;

import java.io.Serializable;

/**
 * JSON键值对中的值字段
 *
 * @author ruan4261
 */
abstract class JSONValue extends JSON implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
}