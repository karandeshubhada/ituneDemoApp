package com.demo.itunesdemoapp.util


fun Double.toCurrency(code: String): String {
    return "$this $code"
}