package com.example.sensorapp.data.remote

interface exportToCSV {
    fun getCsvBodyRow(): String
    fun getCsvHeaderRow(): String
}