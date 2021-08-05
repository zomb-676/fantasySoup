package test

import org.lwjgl.opengl.GL43

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
        println(
            "[glErrorCode]:$errorCode${
                if (needReason) ErrorType.gerErrorFromCode(errorCode)
                    .let { "\n[errorName]:${it.errorName}\n[Reason]:${it.detailReason}" } else ""
            }"
        )
        errorCode = GL43.glGetError()
    }
    if (errorExisted) {
        throw AssertionError("error occurred")
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