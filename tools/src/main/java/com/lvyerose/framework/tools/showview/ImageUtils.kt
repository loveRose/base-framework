package com.lvyerose.framework.tools.showview

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.text.TextUtils
import android.widget.ImageView
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

/**
 * desc: 图片处理工具
 * author: lad
 */
class ImageUtils {
    /**
     * 将彩色图转换为灰度图
     *
     * @param img 位图
     * @return 返回转换好的位图
     */
    fun convertGreyImg(img: Bitmap): Bitmap {
        val width = img.width //获取位图的宽
        val height = img.height //获取位图的高
        val pixels = IntArray(width * height) //通过位图的大小创建像素点数组
        img.getPixels(pixels, 0, width, 0, 0, width, height)
        val alpha = 0xFF shl 24
        for (i in 0 until height) {
            for (j in 0 until width) {
                var grey = pixels[width * i + j]
                val red = grey and 0x00FF0000 shr 16
                val green = grey and 0x0000FF00 shr 8
                val blue = grey and 0x000000FF
                grey =
                    (red.toFloat() * 0.3 + green.toFloat() * 0.59 + blue.toFloat() * 0.11).toInt()
                grey = alpha or (grey shl 16) or (grey shl 8) or grey
                pixels[width * i + j] = grey
            }
        }
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        result.setPixels(pixels, 0, width, 0, 0, width, height)
        return result
    }

    companion object {
        /**
         * 通过uri获取图片并进行压缩
         *
         * @param uri
         */
        @Throws(FileNotFoundException::class, IOException::class)
        fun getBitmapFormUri(ac: Context, uri: Uri?): Bitmap? {
            var input = ac.contentResolver.openInputStream(uri)
            val onlyBoundsOptions = BitmapFactory.Options()
            onlyBoundsOptions.inJustDecodeBounds = true
            onlyBoundsOptions.inDither = true //optional
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888 //optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
            input.close()
            val originalWidth = onlyBoundsOptions.outWidth
            val originalHeight = onlyBoundsOptions.outHeight
            if (originalWidth == -1 || originalHeight == -1) return null
            //图片分辨率以480x800为标准
            val hh = 800f //这里设置高度为800f
            val ww = 480f //这里设置宽度为480f
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            var be = 1 //be=1表示不缩放
            if (originalWidth > originalHeight && originalWidth > ww) { //如果宽度大的话根据宽度固定大小缩放
                be = (originalWidth / ww).toInt()
            } else if (originalWidth < originalHeight && originalHeight > hh) { //如果高度高的话根据宽度固定大小缩放
                be = (originalHeight / hh).toInt()
            }
            if (be <= 0) be = 1
            //比例压缩
            val bitmapOptions = BitmapFactory.Options()
            bitmapOptions.inSampleSize = be //设置缩放比例
            bitmapOptions.inDither = true //optional
            bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888 //optional
            input = ac.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions)
            input.close()
            return compressImage(bitmap) //再进行质量压缩
        }

        /**
         * 质量压缩方法
         *
         * @param image
         * @return
         */
        private fun compressImage(image: Bitmap): Bitmap {
            val baos = ByteArrayOutputStream()
            image.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                baos
            ) //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            var options = 100
            while (baos.toByteArray().size / 1024 > 500) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset() //重置baos即清空baos
                //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
                image.compress(
                    Bitmap.CompressFormat.JPEG,
                    options,
                    baos
                ) //这里压缩options%，把压缩后的数据存放到baos中
                options -= 10 //每次都减少10
            }
            val isBm =
                ByteArrayInputStream(baos.toByteArray()) //把压缩后的数据baos存放到ByteArrayInputStream中
            return BitmapFactory.decodeStream(isBm, null, null)
        }

        /**
         * 读取图片的旋转的角度
         *
         * @param path 图片绝对路径
         * @return 图片的旋转角度
         */
        fun getBitmapDegree(path: String?): Int {
            var degree = 0
            try {
                // 从指定路径下读取图片，并获取其EXIF信息
                val exifInterface = ExifInterface(path)
                // 获取图片的旋转信息
                val orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return degree
        }

        /**
         * 将图片按照某个角度进行旋转
         *
         * @param bm     需要旋转的图片
         * @param degree 旋转角度
         * @return 旋转后的图片
         */
        fun rotateBitmapByDegree(bm: Bitmap, degree: Int): Bitmap? {
            var returnBm: Bitmap? = null

            // 根据旋转角度，生成旋转矩阵
            val matrix = Matrix()
            matrix.postRotate(degree.toFloat())
            try {
                // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
                returnBm =
                    Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
            } catch (e: OutOfMemoryError) {
            }
            if (returnBm == null) {
                returnBm = bm
            }
            if (bm != returnBm) {
                bm.recycle()
            }
            return returnBm
        }

        fun getBitmap(url: String?): Bitmap? {
            var bm: Bitmap? = null
            try {
                val iconUrl = URL(url)
                val conn = iconUrl.openConnection()
                val http = conn as HttpURLConnection
                val length = http.contentLength
                conn.connect()
                // 获得图像的字符流
                val `is` = conn.getInputStream()
                val bis = BufferedInputStream(`is`, length)
                bm = BitmapFactory.decodeStream(bis)
                bis.close()
                `is`.close() // 关闭流
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bm
        }

        /**
         * 头像设置灰度
         *
         * @param view
         */
        fun setFilter(view: ImageView) {
            val matrix = ColorMatrix()
            matrix.setSaturation(0f)
            val filter = ColorMatrixColorFilter(matrix)
            view.colorFilter = filter
        }

        fun drawableToBitmap(drawable: Drawable): Bitmap // drawable 转换成bitmap
        {
            val width = drawable.intrinsicWidth // 取drawable的长宽
            val height = drawable.intrinsicHeight
            val config =
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565 // 取drawable的颜色格式
            val bitmap = Bitmap.createBitmap(width, height, config) // 建立对应bitmap
            val canvas = Canvas(bitmap) // 建立对应bitmap的画布
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas) // 把drawable内容画到画布中
            return bitmap
        }

        private fun getScaleImage(
            context: Context?,
            image: Bitmap,
            width: Float,
            height: Float
        ): Bitmap? {
            try {
//            Bitmap  bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.radio_checked);
                val matrix = Matrix()
                val scaleWidth = width / image.width
                val scaleHeight = height / image.height
                matrix.postScale(scaleWidth, scaleHeight)
                return Bitmap.createBitmap(
                    image,
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )
            } catch (e: Exception) {
            }
            return null
        }

        /**
         * 合成图片
         *
         * @return
         */
        fun getScaleImage(
            bkImage: Bitmap,
            selImage: Bitmap,
            context: Context?,
            width: Int,
            height: Int
        ): Bitmap? {
            var background: Bitmap? = null
            var mask: Bitmap? = null
            var newBitmap: Bitmap? = null
            return try {

                //1.选中图片压缩处理
//            Bitmap  bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.radio_checked);
                background = getScaleImage(
                    context,
                    selImage,
                    width.toFloat(),
                    height.toFloat()
                )

                //2.合成新图
                var config = background!!.config
                if (null == config) {
                    config = Bitmap.Config.ARGB_8888
                }
                newBitmap = Bitmap.createBitmap(width, height, config)
                val newCanvas = Canvas(newBitmap)
                newCanvas.drawBitmap(background, 0f, 0f, null)
                val paint = Paint()
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
                mask = getScaleImage(
                    context,
                    bkImage,
                    width.toFloat(),
                    height.toFloat()
                )
                newCanvas.drawBitmap(
                    mask,
                    Rect(
                        0,
                        0,
                        bkImage.width,
                        bkImage.height
                    ),
                    Rect(
                        0,
                        0,
                        bkImage.width,
                        bkImage.height
                    ),
                    paint
                )
                newBitmap
            } catch (e: Exception) {
                null
            } finally {
                try {
                    if (null != mask && !mask.isRecycled) {
                        mask.recycle()
                    }
                    if (null != newBitmap) {
                        if (null != background && !background.isRecycled) {
                            background.recycle()
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }

        /**
         * 下面的尺寸根据线上首页资讯原图尺寸作为参考，首页资讯banner原图宽度是556，列表图宽度是264
         * 所以首页视频按同样尺寸来显示，可以起到降低流量和内存占用的效果
         */
        var FULL_SCREEN_WIDTH = 556
        var HALF_SCREEN_WIDTH = 264
        var LESS_HALF_SCREEN_WIDTH = 202

        /**
         * 将来源是OSS地址的图片，转换为缩放后的图片地址
         * @param url
         * @param targetWidth
         * @return
         */
        fun getCompressUrl(url: String, targetWidth: Int): String {
            if (TextUtils.isEmpty(url)) return url
            return if (url.contains("vp-image.vpgame.com") || url.contains("vp-image.oss-cn-hangzhou.aliyuncs.com")) {
                // 将oss地址的图片，按目标宽度进行等比缩放后的地址来请求
                "$url?x-oss-process=image/resize,w_$targetWidth"
            } else {
                url
            }
        }

        /**
         * 计算出图片初次显示需要放大倍数
         * @param imagePath 图片的绝对路径
         */
        fun getImageScale(
            context: Context,
            imagePath: String?
        ): Float {
            if (TextUtils.isEmpty(imagePath)) {
                return 2.0f
            }
            var bitmap: Bitmap? = null
            try {
                bitmap = BitmapFactory.decodeFile(imagePath)
            } catch (error: OutOfMemoryError) {
                error.printStackTrace()
            }
            if (bitmap == null) {
                return 2.0f
            }

            // 拿到图片的宽和高
            val dw = bitmap.width
            val dh = bitmap.height
            val wm = (context as Activity).windowManager
            val width = wm.defaultDisplay.width
            val height = wm.defaultDisplay.height
            var scale = 1.0f
            //图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
            if (dw > width && dh <= height) {
                scale = width * 1.0f / dw
            }
            //图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
            if (dw <= width && dh > height) {
                scale = width * 1.0f / dw
            }
            //图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
            if (dw < width && dh < height) {
                scale = width * 1.0f / dw
            }
            //图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
            if (dw > width && dh > height) {
                scale = width * 1.0f / dw
            }
            bitmap.recycle()
            return scale
        }
    }
}