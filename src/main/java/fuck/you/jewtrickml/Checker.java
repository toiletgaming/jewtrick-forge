package fuck.you.jewtrickml;

public class Checker
{
	private boolean state = false;
	
	private int checks = 0;
	private int laststatus = 0;
	private int lastonline = 0;
	
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
						if( isEnabled( ) )
						{
							checks++;
							
							int status = Main.INSTANCE.getUtils( ).getJewTrickStatus( );
							
							if( status != -1 )
							{
								int online = Main.INSTANCE.getUtils( ).getJewTrickOnline( );
								
								laststatus = status;
								if( online != -1 )
									lastonline = online;
								
								if( status == 2 )
								{
									Main.INSTANCE.getLogger( ).info( "Connecting to 2b2t.org [online: " + online + "]" );
									
									setEnabled( false );
									
									if( Configuration.main.ping )
										Main.INSTANCE.getUtils( ).ping2b2t( );
									
									if( Configuration.main.connectDelay > 0 )
										Thread.sleep( Configuration.main.connectDelay );
									
									Main.INSTANCE.getUtils( ).connectTo2b2t( );
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
	
	public void setEnabled( boolean state )
	{
		this.state = state;
		reset( );
	}
}
