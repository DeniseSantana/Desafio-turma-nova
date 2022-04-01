@web @formulario

Feature: formulario

@CT=01
Scenario: Criar Usuário
Given que estou na tela principal
When seleciono o formulario e coloco os dados necessários e confirmo
Then valido a criação do usuário

@CT=02
Scenario: Excluir Usuário
Given que estou na tela principal
When seleciono o usuário para excluir
Then valido a exclussão do usuário