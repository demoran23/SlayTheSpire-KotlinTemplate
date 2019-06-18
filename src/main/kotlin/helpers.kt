package template

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.player
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.MinionPower
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*
import kotlin.reflect.KProperty

private val random = Random()
private val logger: Logger = LogManager.getLogger("helpers");

fun chance(percentage: Int): Boolean {
    return random.nextInt(100) < percentage
}

fun AbstractCreature.isMinion(): Boolean {
    return this.hasPower(MinionPower.POWER_ID)
}

fun AbstractMonster.applyPower(power: AbstractPower, stackAmount: Int = 1) {
    ApplyPowerAction(
        this,
        player,
        power,
        stackAmount
    ).push()
}

fun AbstractGameAction.push() {
    actionManager.addToBottom(this)
}

// cf https://stackoverflow.com/a/54219019/773673
inline fun <reified T> KProperty<*>.getAnnotation(): T {
    return this.annotations.find { it is T } as T
}
