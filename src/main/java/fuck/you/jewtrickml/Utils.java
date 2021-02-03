package fuck.you.jewtrickml;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import fuck.you.jewtrickml.mcping.MinecraftPing;
import fuck.you.jewtrickml.mcping.MinecraftPingOptions;
import fuck.you.jewtrickml.mcping.MinecraftPingReply;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class Utils
{
	private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
	
	public void connectTo2b2t( )
	{
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
