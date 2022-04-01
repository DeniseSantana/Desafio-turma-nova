@mobile @mpj
Feature: mobile tests
  Feature to test automation structure

  @MPJ_00001_01
  Scenario: Realizar login
    Given eu seleciono o ambiente
    And preencho 'Agencia'    
    And preencho 'Conta'    
    And preencho 'Usuario'
    And preencho 'Senha'
    And clico em 'ENTRAR'
    Then valido o login