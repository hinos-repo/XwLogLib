package com.yklib.xwloglib

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

internal class DialogActivity : AppCompatActivity()
{
    companion object
    {
        const val EXTRA_LOG_DIR_NAME = "extra_log_dir_name"
        const val EXTRA_LOG_FILE_NAME = "extra_log_file_name"
        const val EXTRA_LOG_FILE_USE = "extra_log_file_use"

        fun openDialogActivity(context : Context, logDirName : String, logFileName : String, UseFileLog : Boolean)
        {
            val i = Intent(context, DialogActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra(EXTRA_LOG_DIR_NAME, logDirName)
            i.putExtra(EXTRA_LOG_FILE_NAME, logFileName)
            i.putExtra(EXTRA_LOG_FILE_USE, UseFileLog)
            context.startActivity(i)
        }
    }

    private var m_strLogDirName : String = ""
    private var m_strLogFileName : String = ""
    private var m_bUseFileLog = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(android.R.style.Theme_Translucent_NoTitleBar)
        super.onCreate(savedInstanceState)
        if (intent.hasExtra(EXTRA_LOG_DIR_NAME))
        {
            m_strLogDirName = intent.getStringExtra(EXTRA_LOG_DIR_NAME) ?: ""
        }
        if (intent.hasExtra(EXTRA_LOG_FILE_NAME))
        {
            m_strLogFileName = intent.getStringExtra(EXTRA_LOG_FILE_NAME) ?: ""
        }
        if (intent.hasExtra(EXTRA_LOG_FILE_USE))
        {
            m_bUseFileLog = intent.getBooleanExtra(EXTRA_LOG_FILE_USE, false)
        }

        val bResult = XwLogPermission.getPermissionState(this, XwLogPermission.EnumPermission.STORAGE_PERMISSION)
        if (bResult)
        {
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        val bResult = XwLogPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, XwLogPermission.EnumPermission.STORAGE_PERMISSION)
        if (bResult)
        {
            XwLog.initXwLog(this, m_strLogDirName, m_strLogFileName, m_bUseFileLog)
        }
        finish()
    }

    override fun onBackPressed()
    {
        return
    }
}
