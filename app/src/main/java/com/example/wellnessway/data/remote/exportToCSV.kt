package com.example.wellnessway.data.remote

interface exportToCSV {
    fun getCsvBodyRow(): String
    fun getCsvHeaderRow(): String
}