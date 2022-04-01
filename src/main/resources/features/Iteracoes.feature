@web @busca

Feature:Iteracoes

@CT=17
  Scenario: acessando  Drag and Drop
    Given que estou na tela de automacao com batista
    When seleciono no icone iteracoes e acesso o  Drag and Drop
    Then arrasto o elemento selecionado para seu destinno com sucesso
    
@CT=18
  Scenario: acessando Mousehover
    Given que estou na tela de automacao com batista
    When seleciono no icone iteracoes e acesso o  Mousehover
    Then direciono o mause para dentro da caixa que exibira uma menssagem validando a interacao    
    