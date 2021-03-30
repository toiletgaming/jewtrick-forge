package fuck.you.jewtrickml.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import fuck.you.jewtrickml.Configuration;
import fuck.you.jewtrickml.Main;

@Mixin( value = GuiDisconnected.class, priority = 999999 )
public class MixinGuiDisconnected extends GuiScreen
{
	@Inject( method = "initGui", at = @At( "RETURN" ) )
	public void initGui( CallbackInfo info )
	{
		if( Main.INSTANCE.getChecker( ).isAutoReconnecting( ) )
			Main.INSTANCE.getChecker( ).setConnectTime( System.currentTimeMillis( ) );
	}
	
	@Inject( method = "drawScreen", at = @At( "RETURN" ) )
	public void drawScreen( int mouseX, int mouseY, float partialTicks, CallbackInfo info )
	{
		if( Main.INSTANCE.getChecker( ).isAutoReconnecting( ) )
		{
			long time = Main.INSTANCE.getChecker( ).getReconnectTimeRemaining( );
			
			if( time <= 0 )
				Main.INSTANCE.getUtils( ).connectTo2b2t( );
			
			if( Configuration.main.printInfo )
			{
				this.drawString( this.fontRenderer, "[ jewtrick info ]", 2, this.height - 20, 0xCCCCCC );
				this.drawString( this.fontRenderer, "Reconnecting in " + time + "ms", 2, this.height - 10, 0xFFFFFF );
			}
		}
	}
}
