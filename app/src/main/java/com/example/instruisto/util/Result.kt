package com.example.instruisto.util

sealed class Result<out T> {
    class Success<out T>(val value: T): Result<T>()
    class NoSuchId(val id: Int): Result<Nothing>()
}