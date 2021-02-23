package fuck.you.jewtrickml.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fuck.you.jewtrickml.Main;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;

@Mixin( GuiConnecting.class )
public class MixinGuiConnecting extends GuiScreen
{
	@Inject( method = "connect", at = @At( "HEAD" ) )
	private void connect( String ip, int port, CallbackInfo info )
	{
		if( port != 25565 ||
			( !ip.equals( "connect.2b2t.org" ) && !ip.equals( "2b2t.org" ) ) ) 
		{
			if( Main.INSTANCE.getChecker( ).isEnabled( ) ||
				Main.INSTANCE.getChecker( ).isAutoReconnecting( ) )
			{
				Main.INSTANCE.getChecker( ).setEnabled( false );
				Main.INSTANCE.getChecker( ).disableAutoReconnect( );
			}
		}
	}
}
