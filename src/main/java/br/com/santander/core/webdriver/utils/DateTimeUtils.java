package br.com.santander.core.webdriver.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeUtils {

	public String obterDataHora(String formato) {
		return String.valueOf(new SimpleDateFormat(formato).format(new Date()));
	}

	public String obterDataDeHoje(Long diferencaEmDias) {
		LocalDate hoje = LocalDate.now();
		hoje = hoje.plusDays(diferencaEmDias);
		return converterLocalDateParaString(hoje);
	}

	private LocalDate obterLocalDateDeHoje(Long diferencaEmDias) {
		LocalDate hoje = LocalDate.now();
		hoje = hoje.plusDays(diferencaEmDias);
		return hoje;
	}

	public String obterProximoDiaDaSemana(String diaDaSemanaPorExtenso, Long diferencaEmDias) {
		LocalDate data = obterLocalDateDeHoje(diferencaEmDias);
		Integer diaDaSemana = obterNumeroDoDiaDaSemana(diaDaSemanaPorExtenso);
		while (data.getDayOfWeek().getValue() != diaDaSemana) {
			data = data.plusDays(1);
		}
		return converterLocalDateParaString(data);
	}

	public String obterDataProximoXDias(Long diferencaEmDias) {
		LocalDate data = obterLocalDateDeHoje(diferencaEmDias);
		while (data.getDayOfWeek().getValue() >= 6) { // ignorando sabado e domingo
			data = data.minusDays(1);
		}
		return converterLocalDateParaString(data);
	}

	public String obterProximoDiaDaSemana(String diaDaSemanaPorExtenso) {
		return converterLocalDateParaString(obterProximoDiaDaSemana(obterNumeroDoDiaDaSemana(diaDaSemanaPorExtenso)));
	}

	private LocalDate obterProximoDiaDaSemana(Integer diaDaSemana) {
		LocalDate data = LocalDate.now();
		DayOfWeek dayOfWeek = DayOfWeek.of(diaDaSemana);

		if (data.getDayOfWeek() == dayOfWeek) {
			data = data.plusWeeks(1);
		} else {
			do {
				data = data.plusDays(1);
			} while (data.getDayOfWeek() != dayOfWeek);
		}

		return data;
	}

	private Integer obterNumeroDoDiaDaSemana(String diaDaSemanaPorExtenso) {
		Integer numeroFeira = 0;
		String qualFeiraPorExtenso = null;
		Pattern pattern = Pattern.compile("(?<qualFeira>[^-]+)(.*)");
		Matcher matcher = pattern.matcher(diaDaSemanaPorExtenso);
		if (matcher.matches()) {
			qualFeiraPorExtenso = matcher.group("qualFeira").toUpperCase();
		}

		if (qualFeiraPorExtenso == null) {
			log.error("Erro no método, dia não encontrado");
			return -1;
		}

		switch (qualFeiraPorExtenso) {
		case "SEGUNDA":
			numeroFeira = 1;
			break;
		case "TERÇA":
			numeroFeira = 2;
			break;
		case "QUARTA":
			numeroFeira = 3;
			break;
		case "QUINTA":
			numeroFeira = 4;
			break;
		case "SEXTA":
			numeroFeira = 5;
			break;
		case "SÁBADO":
			numeroFeira = 6;
			break;
		case "DOMINGO":
			numeroFeira = 7;
			break;
		default:
			log.error("Dia não encontrado");
		}
		return numeroFeira;
	}

	public String setPrimeiroDiaMesSubsequente() throws Exception {
		LocalDate data = LocalDate.now();
		LocalDate setDia = data.withDayOfMonth(01);
		LocalDate setMes = setDia.plusMonths(1);
		return converterLocalDateParaString(setMes);
	}

	public String setPrimeiroDiaMesVigente() throws Exception {
		LocalDate data = LocalDate.now();
		LocalDate inicio = data.withDayOfMonth(01);
		return converterLocalDateParaString(inicio);
	}

	public String setUltimoDiaMesSubsequente() throws Exception {
		LocalDate data = LocalDate.now();
		LocalDate setDia = data.withDayOfMonth(data.lengthOfMonth());
		LocalDate setMes = setDia.plusMonths(1);

		return converterLocalDateParaString(setMes);
	}

	public String setUltimoDiaMesVigente() throws Exception {
		LocalDate data = LocalDate.now();
		LocalDate setDia = data.withDayOfMonth(data.lengthOfMonth());
		return converterLocalDateParaString(setDia);
	}

	public String setPrimeiroDiaMesAnterior() throws Exception {
		LocalDate data = LocalDate.now();
		LocalDate setDia = data.withDayOfMonth(01);
		LocalDate setMes = setDia.minusMonths(1);
		return converterLocalDateParaString(setMes);
	}

	public String converterLocalDateParaString(LocalDate localdate) {
		String dataISO = localdate.toString();
		String data = null;
		Pattern pattern = Pattern.compile("(?<ano>\\d{4})\\-(?<mes>\\d{2})\\-(?<dia>\\d{2})");
		Matcher matcher = pattern.matcher(dataISO);
		if (matcher.matches()) {
			data = matcher.group("dia") + matcher.group("mes") + matcher.group("ano");
		}
		return data;
	}

	public String converterLocalDateParaStringComBarra(LocalDate localdate) {
		String dataISO = localdate.toString();
		String data = null;
		Pattern pattern = Pattern.compile("(?<ano>\\d{4})\\-(?<mes>\\d{2})\\-(?<dia>\\d{2})");
		Matcher matcher = pattern.matcher(dataISO);
		if (matcher.matches()) {
			data = matcher.group("dia") + "/" + matcher.group("mes") + "/" + matcher.group("ano");
		}
		return data;
	}

	public String setPrimeiroDiaMesSubsequenteSeparadoPorBarra() {
		LocalDate data = LocalDate.now();
		LocalDate setDia = data.withDayOfMonth(01);
		LocalDate setMes = setDia.plusMonths(1);
		return converterLocalDateParaStringComBarra(setMes);
	}

}
