/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.launch.data.legacyui

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.ui.client.GuiBackground
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.minecraft.client.gui.*
import net.minecraft.client.resources.I18n
import net.minecraftforge.fml.client.GuiModList
import org.lwjgl.opengl.GL11
import java.awt.Color

class GuiMainMenu : GuiScreen(), GuiYesNoCallback {
    override fun initGui() {
        val defaultHeight = (this.height / 3.5).toInt()

        this.buttonList.add(GuiButton(1, this.width / 2 - 50, defaultHeight, 100, 20, I18n.format("menu.singleplayer")))
        this.buttonList.add(GuiButton(2, this.width / 2 - 50, defaultHeight + 24, 100, 20, I18n.format("menu.multiplayer")))
        this.buttonList.add(GuiButton(100, this.width / 2 - 50, defaultHeight + 24*2, 100, 20, "%ui.altmanager%"))
        this.buttonList.add(GuiButton(103, this.width / 2 - 50, defaultHeight + 24*3, 100, 20, "%ui.mods%"))
        this.buttonList.add(GuiButton(102, this.width / 2 - 50, defaultHeight + 24*4, 100, 20, "%ui.background%"))
        this.buttonList.add(GuiButton(0, this.width / 2 - 50, defaultHeight + 24*5, 100, 20, I18n.format("menu.options")))
        this.buttonList.add(GuiButton(4, this.width / 2 - 50, defaultHeight + 24*6, 100, 20, I18n.format("menu.quit")))

        super.initGui()
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawBackground(0)

        val bHeight=(this.height / 3.5).toInt()

        Gui.drawRect(width / 2 - 60, bHeight - 30, width / 2 + 60, bHeight + 174, Integer.MIN_VALUE)

        Fonts.font40.drawCenteredString(LiquidBounce.CLIENT_NAME,(width / 2).toFloat(), (bHeight - 20).toFloat(),Color.WHITE.rgb,false)
        Fonts.font40.drawString(LiquidBounce.CLIENT_VERSION, 3F, (height - Fonts.font35.FONT_HEIGHT).toFloat(), 0xffffff,  false)
        val str="§cWebsite: §fhttps://${LiquidBounce.CLIENT_WEBSITE}/"
        Fonts.font40.drawString(str, (this.width - Fonts.font40.getStringWidth(str) - 3).toFloat(), (height - Fonts.font35.FONT_HEIGHT).toFloat(), 0xffffff, false)
        super.drawScreen(mouseX, mouseY, partialTicks)

        GL11.glPushMatrix()
        GL11.glTranslatef(2f,2f,0f)

//        for (jsonElement in LiquidBounce.updatelog) {
//            try {
//                if(jsonElement.isJsonObject){
//                    val json=jsonElement.asJsonObject
//                    Fonts.font35.drawString(json.get("text").asString, 0f, 0f, ColorUtils.decodeColorJsonFormat(json.getAsJsonObject("color")).rgb)
//                }else{
//                    Fonts.font35.drawString(jsonElement.asString, 0f, 0f, Color(255, 255, 255, 160).rgb)
//                }
//                GL11.glTranslatef(0f,Fonts.font35.height+1f,0f)
//            }catch (e: UnsupportedOperationException){
//                // ignore
//            }
//        }

        GL11.glPopMatrix()
    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> mc.displayGuiScreen(GuiOptions(this, mc.gameSettings))
            1 -> mc.displayGuiScreen(GuiSelectWorld(this))
            2 -> mc.displayGuiScreen(GuiMultiplayer(this))
            4 -> mc.shutdown()
            100 -> mc.displayGuiScreen(GuiAltManager(this))
            102 -> mc.displayGuiScreen(GuiBackground(this))
            103 -> mc.displayGuiScreen(GuiModList(this))
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {}
}