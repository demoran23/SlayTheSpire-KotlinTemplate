@file:JvmName("Configuration")

package template

import basemod.BaseMod.registerModBadge
import basemod.BaseMod.subscribe
import basemod.ModLabeledToggleButton
import basemod.ModPanel
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Texture
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.FontHelper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.reflect.KMutableProperty

@SpireInitializer
class Configuration : PostInitializeSubscriber {
    companion object {
        @JvmStatic
        fun initialize() {
            instance = Configuration()
        }

        private val logger: Logger = LogManager.getLogger(Configuration::class.java.name)

        private val booleanDelegate = SpireConfigBooleanDelegate()

        lateinit var instance: Configuration
    }

    init {
        subscribe(this)
    }

    @ConfigKey("exampleKey", "Example Description")
    var example by booleanDelegate

    // Set up the configuration UI
    override fun receivePostInitialize() {
        val badgeTexture = Texture("images/BaseModBadge.png")
        val settingsPanel = ModPanel().apply {
            val xPos = 350f
            var yPos = 760.0f
            fun next(): Float {
                yPos -= 30
                return yPos
            }

            fun addToggleButton(enabledProp: KMutableProperty<Boolean>) {
                addUIElement(ModLabeledToggleButton(
                    enabledProp.getAnnotation<ConfigKey>().Description,
                    xPos,
                    next(),
                    Settings.CREAM_COLOR,
                    FontHelper.charDescFont,
                    enabledProp.getter.call(),
                    this,
                    { },
                    { enabledProp.setter.call(it.enabled) }
                ))
            }

            addToggleButton(::example)
        }

        registerModBadge(badgeTexture, "Exacting", "Author", "Settings", settingsPanel)
    }
}
