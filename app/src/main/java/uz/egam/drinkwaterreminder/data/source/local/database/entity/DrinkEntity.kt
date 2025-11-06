package uz.egam.drinkwaterreminder.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DrinkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val amount: Int,
    val time: Long,
    val isFavorite: Int
)
