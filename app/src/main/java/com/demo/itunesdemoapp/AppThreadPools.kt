package com.demo.itunesdemoapp

import java.util.concurrent.Executor
import java.util.concurrent.Executors

val IO_EXECUTOR: Executor by lazy {
    Executors.newFixedThreadPool(1)
}

val NETWORK_EXECUTOR: Executor by lazy {
    Executors.newFixedThreadPool(4)
}

