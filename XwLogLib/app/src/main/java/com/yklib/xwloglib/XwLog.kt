package com.yklib.xwloglib

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.*
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

object XwLog
{
    private var m_bInitialized = false
    private var m_bUseFileLog = false
    private var m_strLogDirName : String = ""
    private var m_strLogFileName : String = ""

    fun initXwLog(activity : Activity, logDirName : String, logFileName : String, useFileLog : Boolean): Boolean
    {
        m_bUseFileLog = useFileLog
        if (m_bUseFileLog)
        {
            m_strLogDirName = logDirName
            m_strLogFileName = logFileName

            if (m_strLogDirName.isEmpty()) return false
            if (m_strLogFileName.isEmpty()) return false

            val nSize = XwLogPermission.m_arrStoragePermissions.size
            var i = 0
            for (permission in XwLogPermission.m_arrStoragePermissions)
            {
                if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) i++
            }

            if (nSize == i)
            {
                m_bInitialized = true
                return true
            }

            DialogActivity.openDialogActivity(activity, logDirName, logFileName, useFileLog)
        }
        return true
    }


    fun d(TAG : String, strMessage : String)
    {
        if (!m_bInitialized || !m_bUseFileLog)
        {
            Log.d(TAG, strMessage)
            return
        }

        writeLog("$TAG d : $strMessage , CreateTime : ${getNowSimpleDate()}")
    }

    fun e(TAG : String, strMessage : String)
    {
        if (!m_bInitialized || !m_bUseFileLog)
        {
            Log.e(TAG, strMessage)
            return
        }

        writeLog("$TAG e : $strMessage , CreateTime : ${getNowSimpleDate()}")
    }

    fun w(TAG : String, strMessage : String)
    {
        if (!m_bInitialized || !m_bUseFileLog)
        {
            Log.w(TAG, strMessage)
            return
        }

        writeLog("$TAG w : $strMessage , CreateTime : ${getNowSimpleDate()}")
    }

    fun v(TAG : String, strMessage : String)
    {
        if (!m_bInitialized || !m_bUseFileLog)
        {
            Log.v(TAG, strMessage)
            return
        }

        writeLog("$TAG v : $strMessage , CreateTime : ${getNowSimpleDate()}")
    }

    private fun getNowSimpleDate() : String
    {
        val currentTimeMills = System.currentTimeMillis()
        val date = Date(currentTimeMills)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return sdf.format(date)
    }

    private fun writeLog(strContents : String)
    {
        fun makeMyAppFolder() : String
        {
            val f = File(Environment.getExternalStorageDirectory(), m_strLogDirName)
            if(!f.exists())
            {
                val s= f.mkdirs()
                if (s)
                {
                    return f.absolutePath
                }
            }
            else
            {
                return f.absolutePath
            }
            return ""
        }

        val strPath = makeMyAppFolder()
        if (strPath.isEmpty()) return

        try
        {
            val fos = FileOutputStream("${strPath}/$m_strLogFileName", true)
            //파일쓰기
            val writer = BufferedWriter(OutputStreamWriter(fos, Charset.forName("UTF-8")))
            writer.write(String("\n$strContents".toByteArray(Charset.forName("UTF-8")), Charset.forName("UTF-8")))
            writer.flush()
            writer.close()
            fos.close()
        } catch (e: IOException)
        {
            e.printStackTrace()
        }
    }
}