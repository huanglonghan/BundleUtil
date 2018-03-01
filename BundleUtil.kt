package com.ec.www.utils

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import java.io.Serializable

/**
 * Created by huang on 2018/2/1.
 */

class BundleUtil {

    private val params = SparseArray<Any?>()
    private lateinit var types: ArrayList<Int>
    private var _index = 0

    companion object {
        private val KEY_BUNDLE_UTIL = "key_bundle_util_"
        private val KEY_TYPE_LISTS = "key_type_lists"

        private val VAL_NULL = -1
        private val VAL_STRING = 0
        private val VAL_INTEGER = 1
        private val VAL_BUNDLE = 3
        private val VAL_PARCELABLE = 4
        private val VAL_SHORT = 5
        private val VAL_LONG = 6
        private val VAL_FLOAT = 7
        private val VAL_DOUBLE = 8
        private val VAL_BOOLEAN = 9
        private val VAL_CHARSEQUENCE = 10
        private val VAL_LIST = 11
        private val VAL_SPARSEARRAY = 12
        private val VAL_BYTEARRAY = 13
        private val VAL_INTARRAY = 18
        private val VAL_LONGARRAY = 19
        private val VAL_BYTE = 20
        private val VAL_SERIALIZABLE = 21
        private val VAL_BOOLEANARRAY = 23
        private val VAL_DOUBLEARRAY = 28
        private val VAL_OBJECTARRAY = 29
    }

    fun write(any: Any?): BundleUtil {
        params.put(_index++, any)
        return this
    }

    fun <T> read(index: Int = this._index): T? {
        val value = params.get(index) ?: return null
        _index++
        return try {
            value as T
        } catch (e: ClassCastException) {
            return null
        }
    }

    fun bind(bundle: Bundle? = null): Bundle {
        val temp = bundle ?: Bundle()
        writeValue(temp)
        return temp
    }

    fun praise(bundle: Bundle?): Boolean {
        return readValue(bundle ?: return false)
    }

    fun bind(intent: Intent): Intent {
        val temp = Bundle()
        writeValue(temp)
        return intent.putExtras(temp)
    }

    fun praise(intent: Intent?): Boolean {
        return readValue(intent?.extras ?: return false)
    }


    private fun genKey(position: Int): String = KEY_BUNDLE_UTIL + position

    private fun readValue(bundle: Bundle): Boolean {
        types = bundle.getIntegerArrayList(KEY_TYPE_LISTS) ?: return false
        params.clear()
        types.indices.forEach {
            val key = genKey(it)
            val value: Any?
            when (types[it]) {
                VAL_NULL -> {
                    value = null
                }
                VAL_STRING -> {
                    value = bundle.getString(key)
                }
                VAL_INTEGER -> {
                    value = bundle.getInt(key)
                }
                VAL_BUNDLE -> {
                    value = bundle.getBundle(key)
                }
                VAL_PARCELABLE -> {
                    value = bundle.getParcelable(key)
                }
                VAL_SHORT -> {
                    value = bundle.getShort(key)
                }
                VAL_LONG -> {
                    value = bundle.getLong(key)
                }
                VAL_FLOAT -> {
                    value = bundle.getFloat(key)
                }
                VAL_DOUBLE -> {
                    value = bundle.getDouble(key)
                }
                VAL_BOOLEAN -> {
                    value = bundle.getBoolean(key)
                }
                VAL_CHARSEQUENCE -> {
                    value = bundle.getCharSequence(key)
                }
                VAL_LIST -> {
                    value = bundle.getParcelableArrayList<Parcelable>(key)
                }
                VAL_SPARSEARRAY -> {
                    value = bundle.getSparseParcelableArray<Parcelable>(key)
                }
                VAL_BYTEARRAY -> {
                    value = bundle.getByteArray(key)
                }
                VAL_INTARRAY -> {
                    value = bundle.getIntArray(key)
                }
                VAL_LONGARRAY -> {
                    value = bundle.getLongArray(key)
                }
                VAL_BYTE -> {
                    value = bundle.getByte(key)
                }
                VAL_SERIALIZABLE -> {
                    value = bundle.getSerializable(key)
                }
                VAL_BOOLEANARRAY -> {
                    value = bundle.getBooleanArray(key)
                }
                VAL_DOUBLEARRAY -> {
                    value = bundle.getDoubleArray(key)
                }
                VAL_OBJECTARRAY -> {
                    value = bundle.getParcelableArray(key)
                }
                else -> {
                    value = null
                }
            }
            params.put(it, value)
        }
        return true
    }

    private fun writeValue(bundle: Bundle) {
        types = arrayListOf()
        (0 until params.size()).forEach {
            val key = genKey(it)
            val value = params[it]
            var valueType: Int
            when (value) {
                is String -> {
                    valueType = VAL_STRING
                    bundle.putString(key, value)
                }
                is Int -> {
                    valueType = VAL_INTEGER
                    bundle.putInt(key, value)
                }
                is Short -> {
                    valueType = VAL_SHORT
                    bundle.putShort(key, value)
                }
                is Long -> {
                    valueType = VAL_LONG
                    bundle.putLong(key, value)
                }
                is Float -> {
                    valueType = VAL_FLOAT
                    bundle.putFloat(key, value)
                }
                is Double -> {
                    valueType = VAL_DOUBLE
                    bundle.putDouble(key, value)
                }
                is Boolean -> {
                    valueType = VAL_BOOLEAN
                    bundle.putBoolean(key, value)
                }
                is CharSequence -> {
                    valueType = VAL_CHARSEQUENCE
                    bundle.putCharSequence(key, value)
                }
                is Byte -> {
                    valueType = VAL_BYTE
                    bundle.putByte(key, value)
                }
                is ByteArray -> {
                    valueType = VAL_BYTEARRAY
                    bundle.putByteArray(key, value)
                }
                is IntArray -> {
                    valueType = VAL_INTARRAY
                    bundle.putIntArray(key, value)
                }
                is LongArray -> {
                    valueType = VAL_LONGARRAY
                    bundle.putLongArray(key, value)
                }
                is DoubleArray -> {
                    valueType = VAL_DOUBLEARRAY
                    bundle.putDoubleArray(key, value)
                }
                is BooleanArray -> {
                    valueType = VAL_BOOLEANARRAY
                    bundle.putBooleanArray(key, value)
                }
                is ArrayList<*> -> {
                    valueType = VAL_LIST
                    try {
                        bundle.putParcelableArrayList(key, value as ArrayList<out Parcelable>)
                    } catch (e: Exception) {
                        valueType = VAL_NULL
                    }
                }
                is kotlin.Array<*> -> {
                    valueType = VAL_OBJECTARRAY
                    try {
                        bundle.putParcelableArray(key, value as kotlin.Array<out Parcelable>)
                    } catch (e: Exception) {
                        valueType = VAL_NULL
                    }
                }
                is SparseArray<*> -> {
                    valueType = VAL_SPARSEARRAY
                    try {
                        bundle.putSparseParcelableArray(key, value as SparseArray<out Parcelable>)
                    } catch (e: Exception) {
                        valueType = VAL_NULL
                    }
                }
                is Bundle -> {
                    valueType = VAL_BUNDLE
                    bundle.putBundle(key, value)
                }
                is Parcelable -> {
                    valueType = VAL_PARCELABLE
                    bundle.putParcelable(key, value)
                }
                is Serializable -> {
                    valueType = VAL_SERIALIZABLE
                    bundle.putSerializable(key, value)
                }

                else -> {
                    valueType = VAL_NULL
                }
            }
            types.add(valueType)
        }
        bundle.putIntegerArrayList(KEY_TYPE_LISTS, types)
    }

}
