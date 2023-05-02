@web @login

Feature:Login

  @CT=100
  Scenario: Realizar login
    Given que estou na tela principal do site
    When  insiro email e senha  válidos
    Then acesso o sistema com sucesso

  @CT=105
  Scenario: Criar conta Salesforce
    Given que estou na tela principal do site
    When   insiro email e senha  válidos
    Then  clico em contas e depois clico em criar conta e preencho o formulario e crio minha conta com sucesso.




