package br.com.santander.core.webdriver.utils.mobile;

public class UtilidadesDeIOS extends UtilidadesDeMobile
{
	@Override
	public void appiumDesliza( int x1, int y1, int x2, int y2 )
	{
		touchActionDesliza(x1, y1, x2, y2);
	}
	
	@Override
	public void appiumDeslizaAbaixo()
	{
		appiumDeslizaAbaixo( 0, 1 );
	}
	
	@Override
	public void appiumDeslizaAcima()
	{
		appiumDeslizaAcima( 0, 1 );
	}
}
