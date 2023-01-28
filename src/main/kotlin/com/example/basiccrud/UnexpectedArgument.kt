package com.example.basiccrud

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UnexpectedArgument(msg:String, vararg expected:String): IllegalArgumentException("$msg, expected: ${expected.contentToString()}") {

}