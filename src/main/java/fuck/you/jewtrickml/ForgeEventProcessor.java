package fuck.you.jewtrickml;

import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventProcessor
{
	@SubscribeEvent
	public void onConfigChanged( ConfigChangedEvent.OnConfigChangedEvent event )
	{
		if( event.getModID( ).equals( Main.MODID ) )
			ConfigManager.sync( Main.MODID, Type.INSTANCE );
	}
}
