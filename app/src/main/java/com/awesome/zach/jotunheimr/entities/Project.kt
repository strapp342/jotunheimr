package com.awesome.zach.jotunheimr.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "project_table")
data class Project(
    @NonNull
    @ColumnInfo(name = "name")
    var name: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}