package com.lvyerose.framework.tools.storage

import android.os.Environment
import android.os.StatFs
import java.io.File

/**
 * desc: SDCard辅助类
 * author: lad
 */
class SDCardUtils private constructor() {
    companion object {
        /**
         * 判断SDCard是否可用
         */
        private val isSDCardEnable: Boolean
            get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        /**
         * 获取SD卡路径
         */
        private val sDCardPath: String
            get() = Environment.getExternalStorageDirectory()
                .absolutePath + File.separator// 获取空闲的数据块的数量
        // 获取单个数据块的大小（byte）

        /**
         * 获取SD卡的剩余容量 单位byte
         */
        val sDCardAllSize: Long
            get() {
                if (isSDCardEnable) {
                    val stat = StatFs(sDCardPath)
                    // 获取空闲的数据块的数量
                    val availableBlocks = stat.availableBlocks.toLong() - 4
                    // 获取单个数据块的大小（byte）
                    val freeBlocks = stat.availableBlocks.toLong()
                    return freeBlocks * availableBlocks
                }
                return 0
            }

        /**
         * 获取指定路径所在空间的剩余可用容量字节数，单位byte
         */
        fun getFreeBytes(filePath: String): Long {
            // 如果是sd卡的下的路径，则获取sd卡可用容量
            var filePath = filePath
            filePath = if (filePath.startsWith(sDCardPath)) {
                sDCardPath
            } else { // 如果是内部存储的路径，则获取内存存储的可用容量
                Environment.getDataDirectory().absolutePath
            }
            val stat = StatFs(filePath)
            val availableBlocks = stat.availableBlocks.toLong() - 4
            return stat.blockSize * availableBlocks
        }

        /**
         * 获取系统存储路径
         */
        val rootDirectoryPath: String
            get() = Environment.getRootDirectory().absolutePath
    }

    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }
}