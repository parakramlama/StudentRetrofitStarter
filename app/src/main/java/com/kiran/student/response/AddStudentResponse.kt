package com.kiran.student.response

import com.kiran.student.entity.Student

data class AddStudentResponse (
    var success : Boolean? = null,
    var data : Student?=null
        )
