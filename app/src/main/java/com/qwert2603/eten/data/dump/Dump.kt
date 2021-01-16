package com.qwert2603.eten.data.dump

import kotlinx.serialization.Serializable

@Serializable
class SerializableProduct(
    val uuid: String,
    val name: String,
    val calorie: Double,
)

@Serializable
class SerializableMealPart(
    val containerId: String?,
    val uuid: String,
    val weight: Double,
    val productUuid: String?,
    val dishUuid: String?,
)

@Serializable
class SerializableRawCalories(
    val containerId: String?,
    val uuid: String,
    val name: String?,
    val calories: Double,
)

@Serializable
class SerializableDish(
    val uuid: String,
    val name: String,
    val time: Long,
)

@Serializable
class SerializableMeal(
    val uuid: String,
    val name: String?,
    val time: Long,
)

@Serializable
class SerializableDump(
    val products: List<SerializableProduct>,
    val mealParts: List<SerializableMealPart>,
    val rawCalories: List<SerializableRawCalories>,
    val dishes: List<SerializableDish>,
    val meals: List<SerializableMeal>,
)