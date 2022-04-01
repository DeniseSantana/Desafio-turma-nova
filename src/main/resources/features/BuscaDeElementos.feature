@web @busca

Feature: Busca de Elementos


@CT=03
Scenario: buscando elementos
Given que estou na tela inicial e clico em comecar automacao web e busca de elementos
When clico em links e clico nos quatro links
Then valido que algum link foi clicado

@CT=04
Scenario: buscando elementos
Given que estou na tela inicial e clico em comecar automacao web e busca de elementos
When clico em inputs e textfield e preencho as informacoes
Then valido que as informacoes do inputs e textfield foram preenchidas

@CT=05
Scenario: buscando elementos
Given que estou na tela inicial e clico em comecar automacao web e busca de elementos
When clico em botoes e clico em raised e floating e flat e submit
Then valido mensagem ao clicar em algum desses botoes

@CT=06
Scenario: buscando elementos
Given que estou na tela inicial e clico em comecar automacao web e busca de elementos
When clico em radio e checkbox e seleciono cada tipo
Then valido que os tipos foram selecionados

@CT=07
Scenario: buscando elementos
Given que estou na tela inicial e clico em comecar automacao web e busca de elementos
When clico em dropdown e seleciono as opcoes escolhidas
Then valido que a tela com as opcoes escolhidas

@CT=08
Scenario: buscando elementos
Given que estou na tela inicial e clico em comecar automacao web e busca de elementos
When clico em textos e clico em um texto
Then valido que abriu e estou na tela do texto

@CT=09
Scenario: buscando elementos
Given que estou na tela inicial e clico em comecar automacao web e busca de elementos
When clico em tabela e clico em voltar 
Then valido que voltei corretamente