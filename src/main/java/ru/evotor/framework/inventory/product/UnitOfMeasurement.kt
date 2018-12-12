package ru.evotor.framework.inventory.product

import ru.evotor.framework.inventory.product.provider.UnitOfMeasurementContract
import ru.evotor.query.FilterBuilder

sealed class UnitOfMeasurement(val type: Type, val name: String, val precision: Precision = Precision.ZERO) {
    class Kilogram(precision: Precision = Precision.ZERO) : UnitOfMeasurement(Type.MASS_UNIT, NAME, precision) {
        companion object {
            const val NAME = "кг"
        }
    }

    class Piece : UnitOfMeasurement(Type.QUANTITY_UNIT, NAME) {
        companion object {
            const val NAME = "шт"
        }
    }

    class Custom(type: Type, name: String, precision: Precision) : UnitOfMeasurement(type, name, precision)

    enum class Type {
        QUANTITY_UNIT,
        MASS_UNIT,
        DISTANCE_UNIT,
        AREA_UNIT,
        VOLUME_UNIT
    }

    enum class Precision {
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
    }

    class Filter<Q, S : FilterBuilder.SortOrder<S>, R> : FilterBuilder.Inner<Q, S, R>() {
        val type = addFieldFilter<Type>(UnitOfMeasurementContract.COLUMN_TYPE)
        val name = addFieldFilter<String>(UnitOfMeasurementContract.COLUMN_NAME)
        val precision = addFieldFilter<Precision>(UnitOfMeasurementContract.COLUMN_PRECISION)

        class SortOrder<S : FilterBuilder.SortOrder<S>> : FilterBuilder.Inner.SortOrder<S>() {
            val type = addFieldSorter(UnitOfMeasurementContract.COLUMN_TYPE)
            val name = addFieldSorter(UnitOfMeasurementContract.COLUMN_NAME)
            val precision = addFieldSorter(UnitOfMeasurementContract.COLUMN_PRECISION)
        }
    }
}