/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.JumpEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.ListValue

@ModuleInfo(name = "NoWeb", category = ModuleCategory.MOVEMENT)
class NoWeb : Module() {

    private val modeValue = ListValue("Mode", arrayOf("None", "OldAAC", "LAAC", "Rewinside","Horizon", "Spartan", "AAC4", "OldMatrix"), "None")
    private val horizonSpeed = FloatValue("HorizonSpeed", 0.1F, 0.01F, 0.8F)
    
     private var usedTimer = false
    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (usedTimer) {
            mc.timer.timerSpeed = 1F
            usedTimer = false
        }
        if (!mc.thePlayer.isInWeb)
            return

        when (modeValue.get().toLowerCase()) {
            "none" -> mc.thePlayer.isInWeb = false
            "oldaac" -> {
                mc.thePlayer.jumpMovementFactor = 0.59f

                if (!mc.gameSettings.keyBindSneak.isKeyDown)
                    mc.thePlayer.motionY = 0.0
            }
            "laac" -> {
                mc.thePlayer.jumpMovementFactor = if (mc.thePlayer.movementInput.moveStrafe != 0f) 1.0f else 1.21f

                if (!mc.gameSettings.keyBindSneak.isKeyDown)
                    mc.thePlayer.motionY = 0.0

                if (mc.thePlayer.onGround)
                    mc.thePlayer.jump()
            }
            "aac4" -> {
                mc.timer.timerSpeed = 0.99F
                mc.thePlayer.jumpMovementFactor = 0.02958f
                mc.thePlayer.motionY -= 0.00775
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump()
                    mc.thePlayer.motionY = 0.4050
                    mc.timer.timerSpeed = 1.35F
                }
            }
            "horizon" -> {
                if(mc.thePlayer.onGround){
                    MovementUtils.strafe(horizonSpeed.get())
                }
            }
            "spartan" -> {
                MovementUtils.strafe(0.27F)
                mc.timer.timerSpeed = 3.7F
                if (!mc.gameSettings.keyBindSneak.isKeyDown)
                    mc.thePlayer.motionY = 0.0
                if(mc.thePlayer.ticksExisted % 2 == 0){
                    mc.timer.timerSpeed = 1.7F
                }
                if(mc.thePlayer.ticksExisted % 40 == 0){
                    mc.timer.timerSpeed = 3F
                }
                usedTimer = true
            }
            "oldmatrix" -> {
                mc.thePlayer.jumpMovementFactor = 0.124133333f
                mc.thePlayer.motionY = -0.0125
                if (mc.gameSettings.keyBindSneak.isKeyDown) mc.thePlayer.motionY = -0.1625
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump()
                    mc.thePlayer.motionY = 0.2425
                }
            }
            "rewinside" -> {
                mc.thePlayer.jumpMovementFactor = 0.42f

                if (mc.thePlayer.onGround)
                    mc.thePlayer.jump()
            }
        }
    }
    fun onJump(event : JumpEvent){
        if(modeValue.equals("AACv4"))
            event.cancelEvent()
    }

    override fun onDisable() {
        mc.timer.timerSpeed = 1.0F
    }

    
    override val tag: String?
        get() = modeValue.get()
}
