package com.resucito.app.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.resucito.app.R
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage

object GetStringResource {

    @Composable
    fun getLocalizedName(stage: Stage): String {
        val resId = when (stage) {
            Stage.ELECTION -> R.string.election
            Stage.PRECATECHUMENATE -> R.string.precatechumenate
            Stage.CATECHUMENATE -> R.string.catechumenate
            Stage.LITURGY -> R.string.liturgy
        }
        return stringResource(id = resId)
    }

    @Composable
    fun getLocalizedName(category: Category): String {
        val resId = when (category) {
            Category.PSALM -> R.string.psalm
            Category.ADVENT -> R.string.advent
            Category.CHILDRENS_SONG -> R.string.childrens_song
            Category.CHRISTMAS -> R.string.christmas
            Category.COMMUNION -> R.string.communion
            Category.LENT -> R.string.lent
            Category.EASTER -> R.string.easter
            Category.ENTRANCE -> R.string.entrance
            Category.EXIT -> R.string.exit
            Category.FRACTION_OF_BREAD -> R.string.fraction_of_bread
            Category.LUTES_AND_VESPERS -> R.string.lutes_and_vespers
            Category.PEACE_AND_OFFERINGS -> R.string.peace_and_offerings
            Category.PENTECOST -> R.string.pentecost
            Category.SIGNING_TO_THE_VIRGIN -> R.string.signing_to_the_virgin
        }
        return stringResource(id = resId)
    }

}