package com.example.absher.services.exceptions

class   ApiException  : Exception() {
    override val message: String?
        get() = super.message
}

class  TokenException  : Exception() {
    override val message: String?
        get() = super.message

}