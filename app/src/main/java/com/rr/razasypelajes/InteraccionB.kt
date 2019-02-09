package com.rr.razasypelajes

import android.support.v7.app.AppCompatActivity
import com.rr.razasypelajes.Helpers.JSONHelper
import com.rr.razasypelajes.Horses.Horse

class InteraccionB: GameMode() {
    override fun newGame(context: Game) {
        val horses = JSONHelper.fromJSON(Horse::class.java, context.resources.openRawResource(R.raw.horses))
    }
}