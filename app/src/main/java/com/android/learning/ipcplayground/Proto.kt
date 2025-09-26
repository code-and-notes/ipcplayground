package com.android.learning.ipcplayground

object Proto {
    // client -> service
    const val C_PING = 10
    const val C_FETCH_STATUS = 11

    // service -> client
    const val S_PONG = 110
    const val S_STATUS = 111

}
object Keys {
    const val TEXT = "text"
    const val REQ_ID = "reqId"
}