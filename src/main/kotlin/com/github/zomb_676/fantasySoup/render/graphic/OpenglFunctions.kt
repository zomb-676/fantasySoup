package com.github.zomb_676.fantasySoup.render.graphic

import com.github.zomb_676.fantasySoup.FantasySoup
import org.lwjgl.opengl.GL43
import org.lwjgl.opengl.GLDebugMessageCallback
import org.lwjgl.system.MemoryStack

object OpenglFunctions{
    fun checkOpenGlError() {
        var errorCode = GL43.glGetError()
        while (errorCode != GL43.GL_NO_ERROR) {
            println("[glErrorCode]:$errorCode")
            errorCode = GL43.glGetError()
        }
    }

    @Suppress("ControlFlowWithEmptyBody")
    fun clearOpenGlError() {
        while (GL43.glGetError() != GL43.GL_NO_ERROR); }

    @Throws(AssertionError::class)
    fun assertNoError(needReason: Boolean = true) {
        var errorExisted = false
        var errorCode = GL43.glGetError()
        while (errorCode != GL43.GL_NO_ERROR) {
            errorExisted = true
            FantasySoup.logger.error(Canvas.graphicMarker,"[glErrorCode]:$errorCode")
            if (needReason) ErrorType.gerErrorFromCode(errorCode)
                .let {
                    FantasySoup.logger.error(Canvas.graphicMarker,"[errorName]:${it.errorName}")
                    FantasySoup.logger.error(Canvas.graphicMarker,"[Reason]:${it.detailReason}")
                }
            errorCode = GL43.glGetError()
        }
        if (errorExisted) {
            throw AssertionError("opengl error occurred")
        }
    }

    enum class ErrorType(val errorCode: Int, val errorName: String, val detailReason: String) {
        INVALID_ENUM(
            GL43.GL_INVALID_ENUM,
            "Invalid Enum",
            "An unacceptable value is specified for an enumerated argument. The offending command is ignored and has no other side effect than to set the error flag"
        ),
        INVALID_VALUE(
            GL43.GL_INVALID_VALUE,
            "Invalid Value",
            "A numeric argument is out of range. The offending command is ignored and has no other side effect than to set the error flag"
        ),
        INVALID_OPERATION(
            GL43.GL_INVALID_OPERATION,
            "Invalid Operation",
            "The specified operation is not allowed in the current state. The offending command is ignored and has no other side effect than to set the error flag"
        ),
        INVALID_FRAMEBUFFER_OPERATION(
            GL43.GL_INVALID_FRAMEBUFFER_OPERATION,
            "Invalid Framebuffer Operation",
            "The framebuffer object is not complete. The offending command is ignored and has no other side effect than to set the error flag"
        ),
        OUT_OF_MEMORY(
            GL43.GL_OUT_OF_MEMORY,
            "Out Of Memory",
            "There is not enough memory left to execute the command. The state of the GL is undefined, except for the state of the error flags, after this error is recorded"
        ),
        STACK_UNDERFLOW(
            GL43.GL_STACK_UNDERFLOW,
            "Stack Underflow",
            "An attempt has been made to perform an operation that would cause an internal stack to underflow"
        ),
        STACK_OVERFLOW(
            GL43.GL_STACK_OVERFLOW,
            "Stack Overflow",
            "An attempt has been made to perform an operation that would cause an internal stack to overflow"
        );

        companion object {
            @Throws(RuntimeException::class)
            fun gerErrorFromCode(code: Int): ErrorType {
                values().forEach { if (it.errorCode == code) return it }
                throw RuntimeException("failed to get correct errorType")
            }
        }
    }

    @Suppress("ConvertTryFinallyToUseCall")
    inline fun <T, C : MemoryStack> C.use(block: C.() -> T): T {
        try {
            return block()
        } finally {
            this.close()
        }
    }

    @Throws(IllegalArgumentException::class)
    fun sourceToString(int: Int): String {
        return when (int) {
            GL43.GL_DEBUG_SOURCE_API -> "API"
            GL43.GL_DEBUG_SOURCE_WINDOW_SYSTEM -> "WINDOW SYSTEM"
            GL43.GL_DEBUG_SOURCE_SHADER_COMPILER -> "SHADER COMPILER"
            GL43.GL_DEBUG_SOURCE_THIRD_PARTY -> "THIRD PARTY"
            GL43.GL_DEBUG_SOURCE_APPLICATION -> "APPLICATION"
            GL43.GL_DEBUG_SOURCE_OTHER -> "OTHER"
            else ->  throw IllegalArgumentException("failed to fetch source name from $int")
        }
    }

    @Throws(IllegalArgumentException::class)
    fun typeToString(int: Int): String {
        return when (int) {
            GL43.GL_DEBUG_TYPE_ERROR -> "ERROR"
            GL43.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR -> "DEPRECATED BEHAVIOR"
            GL43.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR -> "UNDEFINED BEHAVIOR"
            GL43.GL_DEBUG_TYPE_PORTABILITY -> "PORTABILITY"
            GL43.GL_DEBUG_TYPE_PERFORMANCE -> "PERFORMANCE"
            GL43.GL_DEBUG_TYPE_OTHER -> "OTHER"
            GL43.GL_DEBUG_TYPE_MARKER -> "MARKER"
            else -> throw IllegalArgumentException("failed to fetch type name from $int")
        }
    }

    @Throws(IllegalArgumentException::class)
    fun severityToString(int: Int): String {
        return when (int) {
            GL43.GL_DEBUG_SEVERITY_NOTIFICATION -> "NOTIFICATION"
            GL43.GL_DEBUG_SEVERITY_HIGH -> "HIGH"
            GL43.GL_DEBUG_SEVERITY_MEDIUM -> "MEDIUM"
            GL43.GL_DEBUG_SEVERITY_LOW -> "LOW"
            else -> throw IllegalArgumentException("failed to fetch severity name from $int")
        }
    }

    fun addGlDebugMessageCallback(identifyFlag:Long){
        GL43.glDebugMessageCallback(object : GLDebugMessageCallback(){
            override fun invoke(
                source: Int,
                type: Int,
                id: Int,
                severity: Int,
                length: Int,
                message: Long,
                userParam: Long
            ) {
                FantasySoup.logger.error(Canvas.graphicMarker,"gl error occurred")
                FantasySoup.logger.error(Canvas.graphicMarker,"source:${sourceToString(source)}")
                FantasySoup.logger.error(Canvas.graphicMarker,"type:${typeToString(type)}")
                FantasySoup.logger.error(Canvas.graphicMarker,"id:$id")
                FantasySoup.logger.error(Canvas.graphicMarker,"severity:${severityToString(severity)}")
                FantasySoup.logger.error(Canvas.graphicMarker,"message:${getMessage(length,message)}")
            }
        },identifyFlag)
    }

    /**
     * @param codeBlock source , type , id , severity , identifyFlag
     */
    fun addGlDebugMessageCallback(identifyFlag:Long,codeBlock:(String,String,Int,String,String,Long)->Unit){
        GL43.glDebugMessageCallback(object : GLDebugMessageCallback(){
            override fun invoke(
                source: Int,
                type: Int,
                id: Int,
                severity: Int,
                length: Int,
                message: Long,
                userParam: Long
            ) {
                codeBlock.invoke(sourceToString(source), typeToString(type),id, severityToString(severity),getMessage(length,message),userParam)
            }
        },identifyFlag)
    }

}