package br.com.santander.core.webdriver.utils.mobile;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UtilidadesDeDataTempo
{
	public String retornarProximoDiaUtilDesdeHoje( int diferencaEmDias )
	{
		return retornarProximoDiaUtilDesdeHoje( diferencaEmDias, "dd/MM/uuuu" );
	}
	
	public String retornarProximoDiaUtilDesdeHoje( int diferencaEmDias, String formato )
	{
		return converterLocalDateParaString( retornarLocalDateDoProximoDiaUtilDesdeHoje( diferencaEmDias ), formato );
	}
	
	public String retornarProximoDiaUtilDesde( LocalDate dataInicial )
	{
		return converterLocalDateParaString( retornarLocalDateDoProximoDiaUtilDesde( dataInicial ), "dd/MM/uuuu" );
	}
	
	public String retornarProximoDiaUtilDesde( LocalDate dataInicial, String formato )
	{
		return converterLocalDateParaString( retornarLocalDateDoProximoDiaUtilDesde( dataInicial ), formato );
	}
	
	public int diferencaEntreOProximoDiaUtilEHoje()
	{
		return retornarLocalDateDoProximoDiaUtilDesde( LocalDate.now() ).compareTo( LocalDate.now() );
	}
	
	public LocalDate retornarLocalDateDoProximoDiaUtilDesdeHoje( int diferencaEmDias )
	{
		return retornarLocalDateDoProximoDiaUtilDesde( LocalDate.now(), diferencaEmDias );
	}
	
	public LocalDate retornarLocalDateDoProximoDiaUtilDesde( LocalDate dataInicial, int diferencaEmDias )
	{
		dataInicial = retornarLocalDateDoProximoDiaUtilDesde( dataInicial );
		dataInicial = dataInicial.plusDays( diferencaEmDias );
		dataInicial = retornarLocalDateDoProximoDiaUtilDesde( dataInicial );
		
		return dataInicial;
	}
	
	public LocalDate retornarLocalDateDoProximoDiaUtilDesde( LocalDate dataInicial )
	{
		while( !verificaDiaUtil( dataInicial ) ) dataInicial = dataInicial.plusDays( 1 );
		
		return dataInicial;
	}
	
	public boolean verificaDiaUtil( LocalDate data )
	{
		LocalDate[] feriados = {
				LocalDate.of( 2019,  1,  1 ), // ano novo
				LocalDate.of( 2019,  3,  4 ), // carnaval
				LocalDate.of( 2019,  3,  5 ), // carnaval
				LocalDate.of( 2019,  4, 19 ), // sexta-feira da paixao
				LocalDate.of( 2019,  4, 21 ), // tiradentes
				LocalDate.of( 2019,  5,  1 ), // dia do trabalho
				LocalDate.of( 2019,  6, 20 ), // corpus christi
				LocalDate.of( 2019,  9,  7 ), // independencia
				LocalDate.of( 2019, 10, 12 ), // nossa sra
				LocalDate.of( 2019, 11,  2 ), // finados
				LocalDate.of( 2019, 11, 15 ), // proclamacao da republica
				LocalDate.of( 2019, 12, 25 ), // natal
		};
		
		for ( LocalDate feriado : feriados )
		{
			if ( data.equals( feriado ) )
			{
//				System.out.println( "Data IGUAL a Feriado: " + data );
				return false;
			}
		}
		
		DayOfWeek diaDaSemana = data.getDayOfWeek();
		
		if ( diaDaSemana.equals( DayOfWeek.SATURDAY ) )
		{
//			System.out.println( "Data IGUAL a Sabado: " + data );
			return false;
		}
		else
		{
			if ( diaDaSemana.equals( DayOfWeek.SUNDAY ) )
			{
//				System.out.println( "Data IGUAL a Domingo: " + data );
				return false;
			}
		}
		
		return true;
	}
	
	public String obterHorarioAtual()
	{
		return DateTimeFormatter.ofPattern( "HH:mm:ss" ).format( LocalTime.now() );
	}
	
	public String obterHorarioAtual( String formato )
	{
		return DateTimeFormatter.ofPattern( formato ).format( LocalTime.now() );
	}
	
	/**
	 * @param diferencaEmDias
	 *            quantos dias a partir da data de hoje. Positivo para adicionar
	 *            dias. Negativo para subtrair dias. Zero para obter a data de hoje.
	 * @param formato
	 *            padr??o de formata????o de data. Se nulo, considera como default
	 *            'ddMMuuuu'.
	 * @return data de hoje mais 'diferenciaEmDias'
	 */
	public String obterDataDeHoje(long diferencaEmDias, String formato) {
		return converterLocalDateParaString(obterLocalDateDeHoje(diferencaEmDias), formato);
	}

	/**Data de hoje, mais a diferenca em dias para frente ou
	 * para tr??s.
	 * 
	 * @param diferencaEmDias
	 *            quantos dias a partir da data de hoje. Positivo para adicionar
	 *            dias. Negativo para subtrair dias. Zero para obter a data de hoje.
	 * @return objeto LocalDate
	 */
	public LocalDate obterLocalDateDeHoje(long diferencaEmDias) {
		return LocalDate.now().plusDays(diferencaEmDias);
	}
	
	/**Data de hoje com hora, mais a diferenca em dias para frente ou
	 * para tr??s.
	 * 
	 * @param diferencaEmDias
	 *            quantos dias a partir da data de hoje. Positivo para adicionar
	 *            dias. Negativo para subtrair dias. Zero para obter a data de hoje.
	 * @return objeto LocalDateTime
	 */
	public LocalDateTime obterLocalDateTimeDeHoje(long diferencaEmDias) {
		return LocalDateTime.now().plusDays(diferencaEmDias);
	}

	/**
	 * Obt??m o pr??ximo dia da semana. Por exemplo, se hoje for segunda-feira, dia
	 * 01/01/2000, e quisermos obter a pr??xima quarta-feira, ent??o o retorno ??
	 * "03012000" (03/01/2000). Se quisermos a pr??xima segunda-feira, o retorno ??
	 * "08012000" (08/01/2000). Pode-se obter a data com  a diferen??a de alguns
	 * dias atr??s ou adiante, por exemplo, a pr??xima ter??a-feira daqui a 20 dias.
	 * 
	 * @param diaDaSemanaPorExtenso
	 *            segunda-feira, ter??a-feira, quarta-feira, quinta-feira,
	 *            sexta-feira, s??bado, domingo
	 * @param diferencaEmDias
	 *            quantos dias a partir da data de hoje. Positivo para adicionar
	 *            dias. Negativo para subtrair dias. Zero para obter a data de hoje.
	 * @param formato
	 *            padr??o de formata????o de data. Se nulo, considera como default
	 *            'ddMMuuuu'.
	 * @return A data formatada do pr??ximo dia da semana conforme definido.
	 */
	public String obterProximoDiaDaSemana(final DayOfWeek diaDaSemana, long diferencaEmDias, final String formato) {
		return converterLocalDateParaString(obterProximoLocalDateDaSemana(diaDaSemana, diferencaEmDias), formato);
	}
	
	public String obterProximoDiaDaSemanaIngles(final DayOfWeek diaDaSemana, long diferencaEmDias, final String formato) {
		return converterLocalDateParaStringIngles(obterProximoLocalDateDaSemana(diaDaSemana, diferencaEmDias), formato);
	}

	/**
	 * Obt??m o pr??ximo dia da semana. Por exemplo, se hoje for segunda-feira, dia
	 * 01/01/2000, e quisermos obter a pr??xima quarta-feira (diaDaSemana=3), ent??o o
	 * retorno ?? "03012000" (03/01/2000). Se quisermos a pr??xima segunda-feira
	 * (diaDaSemana=1), o retorno ?? "08012000" (08/01/2000).
	 * 
	 * @param diaDaSemana
	 *            de 1 (Segunda-feira) at?? 7 (Domingo)
	 * @param diferencaEmDias
	 *            quantos dias a partir da data de hoje. Positivo para adicionar
	 *            dias. Negativo para subtrair dias. Zero para obter a data de hoje.
	 * @return O pr??ximo dia da semana no formato ddMMyyyy
	 */
	public LocalDate obterProximoLocalDateDaSemana(final DayOfWeek diaDaSemana, long diferencaEmDias) {
		if (diferencaEmDias == 0) {
			diferencaEmDias = 1;
		}
		LocalDate data = obterLocalDateDeHoje(diferencaEmDias);
		
		if (diferencaEmDias > 0) {
			while (!data.getDayOfWeek().equals(diaDaSemana)) {
				data = data.plusDays(1);
			}
		} else {
			while (!data.getDayOfWeek().equals(diaDaSemana)) {
				data = data.minusDays(1);
			}
		}
		
		return data;
	}
	
	/**
	 * Converte uma java.time.LocalDate para uma String no formato dd/MM/uuuu.
	 * 
	 * @param localdate uma java.time.LocalDate
	 * @return uma data formatada em dd/MM/uuuu
	 */
	public String converterLocalDateParaString( LocalDate localdate )
	{
		return converterLocalDateParaString( localdate, "dd/MM/uuuu" );
	}

	/**
	 * Converte uma java.time.LocalDate para uma String em um
	 * formato qualquer.
	 * 
	 * @param localdate uma java.time.LocalDate
	 * @param formato padr??o de formata????o de data
	 * @return uma data formatada
	 */
	public String converterLocalDateParaString( LocalDate localdate, String formato )
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( formato );
		return localdate.format( formatter );
	}
	
	public String converterLocalDateParaStringIngles( LocalDate localdate )
	{
		return converterLocalDateParaStringIngles( localdate, "dd/MM/uuuu" );
	}
	
	public String converterLocalDateParaStringIngles( LocalDate localdate, String formato )
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( formato, Locale.ENGLISH );
		return localdate.format( formatter );
	}
}
