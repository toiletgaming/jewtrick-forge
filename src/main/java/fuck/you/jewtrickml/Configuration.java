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
		
		@Config.Name( "Connect delay (ms)" )
		@Config.RangeInt( min = 0, max = 2000 )
		public int connectDelay = 600;
		
		@Config.Name( "Print info" )
		public boolean printInfo = true;
		
		@Config.Name( "Ping 2b2t before joining" )
		public boolean ping = true;
		
		@Config.Name( "Auto Reconnect" )
		public boolean autoReconnect = false;
		
		@Config.Name( "Auto Reconnect delay" )
		@Config.RangeInt( min = 200, max = 5000 )
		public int autoReconnectDelay = 400;
		
		@Config.Name( "Check 2b2t restarts" )
		@Config.Comment( "Only works if \"Print info\" is enabled" )
		public boolean checkRestarts = false;
		
		@Config.Name( "Play sound when 2b2t restarts" )
		@Config.Comment( "Obviously works only if \"Check 2b2t restarts\" is enabled" )
		public boolean restartSound = true;
	}
	
	@Config.Name( "Main" )
	@Config.Comment( "Main" )
	public static final MainSettings main = new MainSettings( );
}
