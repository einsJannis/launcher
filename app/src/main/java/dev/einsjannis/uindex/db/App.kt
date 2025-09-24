package dev.einsjannis.uindex.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class App(
    @PrimaryKey @ColumnInfo(name = "package_name") val packageName: String,
    var favorite: Int?,
    @ColumnInfo(name = "is_hidden", defaultValue = "FALSE") var isHidden: Boolean,
)