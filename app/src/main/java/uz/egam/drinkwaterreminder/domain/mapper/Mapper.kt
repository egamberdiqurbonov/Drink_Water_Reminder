package uz.egam.drinkwaterreminder.domain.mapper

import uz.egam.drinkwaterreminder.data.source.local.database.entity.DrinkEntity
import uz.egam.drinkwaterreminder.domain.data.DrinkUiData
import uz.egam.drinkwaterreminder.util.getHour

fun DrinkEntity.toDrinkUiData(): DrinkUiData =
    DrinkUiData(
        id = this.id,
        name = this.name,
        amount = this.amount,
        time = this.time.getHour(),
        isFavorite = this.isFavorite == 1
    )