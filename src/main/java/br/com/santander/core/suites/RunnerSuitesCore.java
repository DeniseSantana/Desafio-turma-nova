package br.com.santander.core.suites;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class RunnerSuitesCore {

	/**
	 * Executa a(s) suite(s) de testes em xml usando testNG
	 * 
	 * @param nomeSuites
	 *            Lista com os nomes dos arquivos .xml
	 * @param path
	 *            Caminho até a pasta contendo os arquivos .xml
	 */
	public static void run(List<String> nomeSuites, String path) {
		TestNG testNG = new TestNG();
		testNG.setTestSuites(getSuites(nomeSuites, path));
		testNG.run();
	}

	private static List<String> getSuites(List<String> nomeSuites, String path) {
		try {
			if (path.charAt(path.length() - 1) != '\\') {
				path += "\\";
			}
			List<String> suites = new ArrayList<String>();
			for (String suite : nomeSuites) {
				suite = suite.replaceAll(".xml", "");
				suites.add(path + suite + ".xml");
			}
			System.out.println("Rodando " + suites.size() + " Suite(s): " + nomeSuites);
			return suites;
		} catch (StringIndexOutOfBoundsException e) {
			throw new RuntimeException("caminho não especificado");
		}
	}
}