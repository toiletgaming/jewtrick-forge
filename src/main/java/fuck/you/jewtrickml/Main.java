package fuck.you.jewtrickml;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(
		modid = Main.MODID,
		name = Main.NAME,
		version = Main.VERSION
)
@SideOnly( Side.CLIENT )
public class Main
{
	public static final String MODID = "jewtrickml";
	public static final String NAME = "jewtrick.ml";
	public static final String VERSION = "1.1.1";
	
	private static Logger logger = LogManager.getLogger( Main.NAME );
	private static Utils utils = new Utils( );
	private static Checker checker = new Checker( );
	
	public static final Main INSTANCE = new Main( );
	
	@EventHandler
	public void init( FMLInitializationEvent event )
	{
		logger.info( "Initializing..." );
		
		checker.createThread( );
		MinecraftForge.EVENT_BUS.register( new ForgeEventProcessor( ) );
	}
	
	public Logger getLogger( )
	{
		return logger;
	}
	
	public Utils getUtils( )
	{
		return utils;
	}
	
	public Checker getChecker( )
	{
		return checker;
	}
}
