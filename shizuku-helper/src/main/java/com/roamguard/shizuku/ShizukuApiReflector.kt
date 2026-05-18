package com.roamguard.shizuku

import android.content.Context
import android.telephony.TelephonyManager
import java.lang.reflect.Method

class ShizukuApiReflector {

    private var telephonyManager: TelephonyManager? = null

    fun init(context: Context) {
        telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    fun setNetworkSelectionModeManual(plmn: String, subId: Int = 0): Boolean {
        return try {
            val tm = telephonyManager ?: return false
            val classTm = tm.javaClass
            val method: Method = if (subId > 0) {
                classTm.getMethod(
                    "setNetworkSelectionModeManual",
                    Int::class.javaPrimitiveType,
                    String::class.java,
                    Boolean::class.javaPrimitiveType
                )
            } else {
                classTm.getMethod(
                    "setNetworkSelectionModeManual",
                    String::class.java,
                    Boolean::class.javaPrimitiveType
                )
            }
            val result = if (subId > 0) {
                method.invoke(tm, subId, plmn, false)
            } else {
                method.invoke(tm, plmn, false)
            }
            result as? Boolean ?: false
        } catch (e: Exception) {
            false
        }
    }

    @Suppress("DiscouragedPrivateApi")
    fun setDataRoamingEnabled(subId: Int, enabled: Boolean): Boolean {
        return try {
            val tm = telephonyManager ?: return false
            val classTm = tm.javaClass
            val method = classTm.getDeclaredMethod(
                "setDataRoamingEnabled",
                Int::class.javaPrimitiveType,
                Boolean::class.javaPrimitiveType
            )
            method.isAccessible = true
            method.invoke(tm, subId, enabled) as Boolean
        } catch (e: Exception) {
            false
        }
    }

    fun getNetworkOperatorMcc(): Int? {
        return try {
            telephonyManager?.networkOperator?.takeIf { it.length >= 3 }?.substring(0, 3)?.toIntOrNull()
        } catch (e: Exception) {
            null
        }
    }

    fun isNetworkRoaming(): Boolean {
        return try {
            telephonyManager?.isNetworkRoaming ?: false
        } catch (e: Exception) {
            false
        }
    }
}
