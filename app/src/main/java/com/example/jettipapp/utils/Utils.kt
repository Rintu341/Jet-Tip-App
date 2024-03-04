package com.example.jettipapp.utils

fun calculateTip(totalBillValue:Double,sliderValue:Double):Double{
    return (totalBillValue/100) * sliderValue
}
fun calculatePerPersonBill(totalBillValue: Double,splitValue:Double,sliderValue: Double):Double{
    return (totalBillValue + calculateTip(totalBillValue,sliderValue)) / splitValue
}