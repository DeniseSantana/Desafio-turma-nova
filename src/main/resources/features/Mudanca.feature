@web @busca
Feature: mudanca

  @CT=10
  Scenario: Icone mudanca
    Given que estou na tela inicial
    When clico no icone alert
    Then valido que estou na tela com sucesso

  @CT=11
  Scenario: Icone mudanca
    Given que estou na tela inicial
    When clico no icone Iframe
    Then valido que estou pagina com sucesso

  @CT=12
  Scenario: Icone mudanca
    Given que estou na tela inicial
    When clico no icone janela
    Then valido que estou na tela 
    
	@CT=13
  Scenario: Icone mudanca
    Given que estou na tela inicial
    When clico no icone modal
    Then valido a pagina com sucesso
	