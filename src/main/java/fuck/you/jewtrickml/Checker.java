package fuck.you.jewtrickml;

import java.util.HashMap;
import java.util.Map;

public class Checker
{
	private boolean state = false;
	private boolean autoreconnecting = false;
	
	private int checks = 0;
	private int laststatus = 0;
	private int lastonline = 0;
	private long connecttime = 0;
	
	private Map< String, String > restartmap = new HashMap< >( );
	private String restartstate = null;
	
	public void createThread( )
	{
		restartmap.put( "15", "15 minutes" );
		restartmap.put( "10", "10 minutes" );
		restartmap.put( "5", "5 minutes" );
		restartmap.put( "2", "2 minutes" );
		restartmap.put( "0.15", "15 seconds" );
		
		Thread checkthread = new Thread( new Runnable( )
		{
			@Override
			public void run( )
			{
				while( !Thread.currentThread( ).isInterrupted( ) )
				{
					try
					{
						if( isEnabled( ) || autoreconnecting )
						{
							checks++;
							
							int status = Main.INSTANCE.getUtils( ).getJewTrickStatus( );
							
							if( status != -1 )
							{
								int online = Main.INSTANCE.getUtils( ).getJewTrickOnline( );
								
								laststatus = status;
								if( online != -1 )
									lastonline = online;
								
								if( !autoreconnecting )
								{
									if( status == 2 )
									{
										if( Configuration.main.autoReconnect )
											autoreconnecting = true;
										
										setEnabled( false );

										if( Configuration.main.ping )
											Main.INSTANCE.getUtils( ).ping2b2t( );
										
										if( Configuration.main.connectDelay > 0 )
											Thread.sleep( Configuration.main.connectDelay );
										
										Main.INSTANCE.getUtils( ).connectTo2b2t( );
									}
								}
							}
						}
						
						Thread.sleep( Configuration.main.checkDelay );
					}
					catch( Exception e )
					{
						e.printStackTrace( );
					}
				}
			}
		} );
		
		Thread restartthread = new Thread( new Runnable( )
		{
			@Override
			public void run( )
			{
				while( !Thread.currentThread( ).isInterrupted( ) )
				{
					try
					{
						if( Configuration.main.printInfo && Configuration.main.checkRestarts )
						{
							String restart = Main.INSTANCE.getUtils( ).getRestartStatus( );
							if( restart != null )
							{
								if( restart.equals( "0" ) )
									updateRestartState( null );
								else if( restart.equals( "1" ) )
									updateRestartState( "2b2t is restarting" );
								else
								{
									String time = restartmap.get( restart );
									if( time != null )
										updateRestartState( "2b2t restarting in " + time );
									else // wtf????
										Main.INSTANCE.getLogger( ).error( "No entry for " + restart + "in restartmap" );
								}
								
								Main.INSTANCE.getUtils( ).sleep( 6000 );
							}
						}
						
						Main.INSTANCE.getUtils( ).sleep( 1000 );
					}
					catch( Exception e )
					{
						try
						{
							Main.INSTANCE.getUtils( ).sleep( 1000 );
						}
						catch( Exception e2 ) {}
						
						e.printStackTrace( );
					}
				}
			}
		} );
		
		checkthread.setName( "jewtrickxyz-check-thread" );
		checkthread.start( );
		
		restartthread.setName( "jewtrickxyz-restartcheck-thread" );
		restartthread.start( );
		
		Main.INSTANCE.getLogger( ).info( "Started threads" );
	}
	
	public void reset( )
	{
		this.checks = 0;
		this.lastonline = 0;
		
		if( !Configuration.main.autoReconnect )
		{
			this.autoreconnecting = false;
			this.connecttime = 0;
		}
	}
	
	public void disableAutoReconnect( )
	{
		this.autoreconnecting = false;
		this.connecttime = 0;
	}
	
	public boolean isEnabled( )
	{
		return state;
	}
	
	public boolean toggle( )
	{
		state = !state;
		return state;
	}
	
	public int getPings( )
	{
		return checks;
	}
	
	public int getLastOnline( )
	{
		return lastonline;
	}
	
	public int getLastStatus( )
	{
		return laststatus;
	}
	
	public boolean isAutoReconnecting( )
	{
		return autoreconnecting;
	}
	
	public long getReconnectTimeRemaining( )
	{
		return ( connecttime + Configuration.main.autoReconnectDelay ) - System.currentTimeMillis( );
	}
	
	public void setEnabled( boolean state )
	{
		this.state = state;
		reset( );
	}
	
	public void setConnectTime( long connecttime )
	{
		this.connecttime = connecttime;
	}
	
	public void updateRestartState( String str )
	{
		if( ( this.restartstate == null && str != null ) ||
			( this.restartstate != null && str != null && !this.restartstate.equals( str ) ) )
			Main.INSTANCE.getUtils( ).printToChat( str );

		this.restartstate = str;
	}
	
	public String getRestartState( )
	{
		return restartstate;
	}
}
