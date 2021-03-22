package fuck.you.jewtrickml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.mojang.realmsclient.gui.ChatFormatting;

import fuck.you.jewtrickml.mcping.MinecraftPing;
import fuck.you.jewtrickml.mcping.MinecraftPingOptions;
import fuck.you.jewtrickml.mcping.MinecraftPingReply;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.text.TextComponentString;

public class Utils
{
	private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
	
	public void connectTo2b2t( )
	{
		Main.INSTANCE.getLogger( ).info( "Connecting to 2b2t.org [online: " + Main.INSTANCE.getChecker( ).getLastOnline( ) + "]" );
		
		Minecraft.getMinecraft( ).addScheduledTask( ( ) ->
		{
			ServerData data = new ServerData( "", "2b2t.org:25565", false );
			net.minecraftforge.fml.client.FMLClientHandler.instance( ).connectToServer( null, data );
		} );
	}
	
	public int getJewTrickStatus( )
	{
		try
		{
			StringBuffer buffer = new StringBuffer( );
			
			URL url = new URL( "http://server.jewtrick.xyz/jewtrickstatus.html" );
			HttpURLConnection connection = ( HttpURLConnection )url.openConnection( );
			connection.setRequestProperty( "User-Agent", USERAGENT );
			BufferedReader in = new BufferedReader( new InputStreamReader( connection.getInputStream( ) ) );
			
			String line = "";
			while( ( line = in.readLine( ) ) != null )
				buffer.append( line );
			in.close( );
			connection.disconnect( );
			
			return Integer.parseInt( buffer.toString( ) );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
		
		return -1;
	}
	
	public int getJewTrickOnline( )
	{
		try
		{
			StringBuffer buffer = new StringBuffer( );
			
			URL url = new URL( "http://server.jewtrick.xyz/jew_online.html" );
			HttpURLConnection connection = ( HttpURLConnection )url.openConnection( );
			connection.setRequestProperty( "User-Agent", USERAGENT );
			BufferedReader in = new BufferedReader( new InputStreamReader( connection.getInputStream( ) ) );
			
			String line = "";
			while( ( line = in.readLine( ) ) != null )
				buffer.append( line );
			in.close( );
			connection.disconnect( );
			
			return Integer.parseInt( buffer.toString( ) );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
		
		return -1;
	}
	
	public String getRestartStatus( )
	{
		try
		{
			StringBuffer buffer = new StringBuffer( );
			
			URL url = new URL( "http://87.251.79.176:777/" );
			HttpURLConnection connection = ( HttpURLConnection )url.openConnection( );
			connection.setRequestProperty( "User-Agent", USERAGENT );
			BufferedReader in = new BufferedReader( new InputStreamReader( connection.getInputStream( ) ) );
			
			String line = "";
			while( ( line = in.readLine( ) ) != null )
				buffer.append( line );
			in.close( );
			connection.disconnect( );
			
			return buffer.toString( );
		}
		catch( Exception e )
		{
		}
		
		return null;
	}
	
	public void printToChat( String str )
	{
		if( str == null ||
			Minecraft.getMinecraft( ).player == null ||
			Minecraft.getMinecraft( ).ingameGUI == null ||
			Minecraft.getMinecraft( ).ingameGUI.getChatGUI( ) == null )
			return;
		
		try
		{
			Minecraft.getMinecraft( ).ingameGUI.getChatGUI( )
				.printChatMessage( new TextComponentString(
						ChatFormatting.GRAY + "[jewtrick] " + ChatFormatting.YELLOW + str ) );
		}
		catch( Exception e )
		{
			Main.INSTANCE.getLogger( ).info( str );
		}
	}
	
	public void ping2b2t( )
	{
		try
		{
			MinecraftPingReply ping = new MinecraftPing( ).getPing(
					new MinecraftPingOptions( )
					.setHostname( "2b2t.org" )
					.setPort( 25565 )
					.setTimeout( 1500 ) );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
}
