package fuck.you.jewtrickml.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fuck.you.jewtrickml.Configuration;
import fuck.you.jewtrickml.Main;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;

@Mixin( GuiMultiplayer.class )
public class MixinGuiMultiplayer extends GuiScreen
{
	private GuiButton togglebutton;
	
	@Inject( method = "createButtons", at = @At( "RETURN" ) )
	public void createButtons( CallbackInfo info )
	{
		togglebutton = new GuiButton( 9235, this.width - 85, 5, 80, 20, "2b2t jewtrick" );
		
		updateButtonColor( );
		
		this.buttonList.add( togglebutton );
	}
	
	@Inject( method = "connectToSelected", at = @At( "HEAD" ) )
	public void connectToSelected( CallbackInfo info )
	{
		Main.INSTANCE.getChecker( ).setEnabled( false );
	}
	
	@Inject( method = "actionPerformed", at = @At( "HEAD" ) )
	public void actionPerformed( GuiButton button, CallbackInfo info )
	{
		if( button.id == togglebutton.id )
		{
			Main.INSTANCE.getChecker( ).toggle( );
			
			updateButtonColor( );
		}
	}
	
	@Inject( method = "drawScreen", at = @At( "RETURN" ) )
	public void drawScreen( int mouseX, int mouseY, float partialTicks, CallbackInfo info )
	{
		updateButtonColor( );
		
		if( Configuration.main.printInfo )
		{
			String pings = String.format(
					"Pings: %d", Main.INSTANCE.getChecker( ).getPings( ) );

			String lastonline = String.format(
					"Last online: %d", Main.INSTANCE.getChecker( ).getLastOnline( ) );
			
			this.drawString( this.fontRenderer, "[ jewtrick info ]", 2, this.height - 30, 0xCCCCCC );
			this.drawString( this.fontRenderer, pings, 2, this.height - 20, 0xFFFFFF );
			this.drawString( this.fontRenderer, lastonline, 2, this.height - 10, 0xFFFFFF );
		}
	}
	
	private void updateButtonColor( )
	{
		boolean state = Main.INSTANCE.getChecker( ).isEnabled( );
		togglebutton.packedFGColour =
				( state
				? 0xFF00FF00 // green
				: 0x8CFF0000 ); // red
	}
}
