package fuck.you.jewtrickml;

public class Checker
{
	private boolean state = false;
	private boolean autoreconnecting = false;
	
	private int checks = 0;
	private int laststatus = 0;
	private int lastonline = 0;
	private long connecttime = 0;
	
	public void createThread( )
	{
		Thread thread = new Thread( new Runnable( )
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
		
		thread.setName( "jewtrickml-thread" );
		thread.start( );
		
		Main.INSTANCE.getLogger( ).info( "Started thread" );
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
}
