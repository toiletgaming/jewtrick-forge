package fuck.you.jewtrickml;

import net.minecraftforge.common.config.Config;

@Config( modid = Main.MODID )
@Config.LangKey( Main.NAME )
public class Configuration
{
	public static class MainSettings
	{
		@Config.Name( "Check delay (ms)" )
		@Config.RangeInt( min = 1000, max = 3000 )
		public int checkDelay = 1000;
		
		@Config.Name( "Print info" )
		public boolean printInfo = true;
		
		@Config.Name( "Ping 2b2t before AutoJoining" )
		public boolean ping = false;
	}
	
	@Config.Name( "Main" )
	@Config.Comment( "Main" )
	public static final MainSettings main = new MainSettings( );
}
