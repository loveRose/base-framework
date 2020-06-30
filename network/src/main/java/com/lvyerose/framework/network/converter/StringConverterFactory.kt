package com.lvyerose.framework.network.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.nio.charset.Charset

/**
 * 字符串转换器示例
 * 可直接使用作为字符串转换器
 */
class StringConverterFactory private constructor() : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        return StringResponseBodyConverter()
    }

    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation>?,
        methodAnnotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody>? {
        return StringRequestBodyConverter()
    }

    class StringRequestBodyConverter internal constructor() : Converter<String, RequestBody> {
        override fun convert(value: String): RequestBody? {
            val buffer = Buffer()
            val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
            writer.write(value)
            writer.close()
            return buffer.readByteString().toRequestBody(MEDIA_TYPE)
        }

        companion object {
            private val MEDIA_TYPE = "application/json; charset=UTF-8".toMediaType()
            private val UTF_8 = Charset.forName("UTF-8")
        }
    }

    class StringResponseBodyConverter : Converter<ResponseBody, String> {
        @Throws(IOException::class)
        override fun convert(value: ResponseBody): String? {
            value.use { v ->
                return v.string()
            }
        }
    }

    companion object {
        internal fun create(): StringConverterFactory {
            return StringConverterFactory()
        }
    }
}